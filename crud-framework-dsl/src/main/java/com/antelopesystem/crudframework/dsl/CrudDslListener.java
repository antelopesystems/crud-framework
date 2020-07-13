package com.antelopesystem.crudframework.dsl;

import com.antelopesystem.crudframework.dsl.CrudDslParserBaseListener;
import com.antelopesystem.crudframework.dsl.context.DslContext;
import com.antelopesystem.crudframework.dsl.function.DslFunction;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import com.antelopesystem.crudframework.modelfilter.FilterField;
import com.antelopesystem.crudframework.modelfilter.enums.FilterFieldOperation;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.*;
import java.util.stream.Collectors;

import static com.antelopesystem.crudframework.dsl.CrudDslParser.*;

public class CrudDslListener extends CrudDslParserBaseListener {

	private final DslContext dslContext;
	private final DynamicModelFilter filter = new DynamicModelFilter();

	public CrudDslListener(DslContext dslContext) {
		this.dslContext = dslContext;
	}

	private final ArrayDeque<FilterField> junctionStack = new ArrayDeque<>();
	@Override
	public void enterJunction(JunctionContext ctx) {
		FilterField filterField = new FilterField();
		filterField.setOperation(getFilterFieldOperation(ctx.type.getText(), false));
		junctionStack.add(filterField);
	}

	@Override
	public void exitJunction(JunctionContext ctx) {
		FilterField filterField = junctionStack.pop();
		addFilterField(filterField);
	}

	@Override
	public void enterStandardComparison(StandardComparisonContext ctx) {
		String id = ctx.left.getText();
		String op = ctx.op.getText();
		Object value = parseValueContext(ctx.right);

		FilterField filterField = new FilterField();

		filterField.setFieldName(id);
		filterField.setValues(new Object[]{value});
		filterField.setOperation(getFilterFieldOperation(op, value == null));
		addFilterField(filterField);
	}

	@Override
	public void enterStringComparison(StringComparisonContext ctx) {
		String id = ctx.left.getText();
		String op = ctx.op.getText();
		String value = (String) parseValueContext(ctx.right);
		FilterField filterField = new FilterField();
		filterField.setFieldName(id);
		filterField.setValues(new String[] { value });
		filterField.setOperation(getFilterFieldOperation(op, false));
		addFilterField(filterField);
	}

	@Override
	public void enterArrayComparison(ArrayComparisonContext ctx) {
		String id = ctx.left.getText();
		String op = ctx.op.getText();

		List<Object> values = ctx.right.children.stream().filter(ch -> ch instanceof AnyValueContext).map(ct -> parseValueContext((ParserRuleContext) ct)).collect(Collectors.toList());
		FilterField filterField = new FilterField();
		filterField.setFieldName(id);
		filterField.setValues(values.toArray(new Object[] {}));
		filterField.setOperation(getFilterFieldOperation(op, false));
		addFilterField(filterField);
	}

	@Override
	public void enterNumericalOrDateComparison(NumericalOrDateComparisonContext ctx) {
		String id = ctx.left.getText();
		String op = ctx.op.getText();
		Object value = parseValueContext(ctx.right);
		FilterField filterField = new FilterField();
		filterField.setFieldName(id);
		filterField.setValues(new Object[] { value });
		filterField.setOperation(getFilterFieldOperation(op, false));
		addFilterField(filterField);
	}

	@Override
	public void enterBetweenComparison(BetweenComparisonContext ctx) {
		String id = ctx.left.getText();

		Object firstValue = parseValueContext(ctx.first);
		Object secondValue = parseValueContext(ctx.second);

		FilterField filterField = new FilterField();
		filterField.setFieldName(id);
		filterField.setValues(new Object[] { firstValue, secondValue });
		filterField.setOperation(FilterFieldOperation.Between);
		addFilterField(filterField);
	}

	public DynamicModelFilter getFilter() {
		return filter;
	}

	private FilterFieldOperation getFilterFieldOperation(String op, boolean isValueNull) {
		if(isValueNull) {
			if("==".equals(op)) {
				return FilterFieldOperation.IsNull;
			} else if("!=".equals(op)) {
				return FilterFieldOperation.IsNotNull;
			} else {
				throw new UnsupportedOperationException("Unsupported operation for null value");
			}
		}

		if("~".equals(op)) {
			return FilterFieldOperation.Contains;
		} else if("==".equals(op)) {
			return FilterFieldOperation.Equal;
		} else if("!=".equals(op)) {
			return FilterFieldOperation.NotEqual;
		} else if("in".equals(op)) {
			return FilterFieldOperation.In;
		} else if("not in".equals(op)) {
			return FilterFieldOperation.NotIn;
		} else if(">".equals(op)) {
			return FilterFieldOperation.GreaterThan;
		} else if(">=".equals(op)) {
			return FilterFieldOperation.GreaterEqual;
		} else if("<".equals(op)) {
			return FilterFieldOperation.LowerThan;
		} else if("<=".equals(op)) {
			return FilterFieldOperation.LowerEqual;
		} else if("^".equals(op)) {
			return FilterFieldOperation.StartsWith;
		} else if("$".equals(op)) {
			return FilterFieldOperation.EndsWith;
		} else if("and".equals(op)) {
			return FilterFieldOperation.And;
		} else if("or".equals(op)) {
			return FilterFieldOperation.Or;
		} else if("not".equals(op)) {
			return FilterFieldOperation.Not;
		}
		throw new UnsupportedOperationException(op + " is not a valid operation");
	}

	private Object parseValueContext(ParserRuleContext ctx) {
		Object value;
		if(ctx.getToken(STRING_VALUE, 0) != null) {
			value = ctx.getToken(STRING_VALUE, 0).getText().substring(1, ctx.getText().length()-1);
		} else if (ctx.getToken(BOOLEAN_VALUE, 0) != null) {
			value = Boolean.valueOf(ctx.getToken(BOOLEAN_VALUE, 0).getText());
		} else if (ctx.getToken(INT_VALUE, 0) != null) {
			value = Integer.valueOf(ctx.getToken(INT_VALUE, 0).getText());
		} else if (ctx.getRuleContext(FunctionCallContext.class,0) != null) {
			FunctionCallContext functionCallCtx = ctx.getRuleContext(FunctionCallContext.class,0);
			value = callFunction(functionCallCtx);
		} else if (ctx.getToken(NULL_VALUE, 0) != null) {
			value = null; // Left for brevity
		} else {
			throw new IllegalStateException("Could not parse value for token '" + ctx.getText() + "'");
		}

		return value;
	}

	private Object callFunction(FunctionCallContext functionCallCtx) {
		Object value;
		String functionName = functionCallCtx.name.getText();
		if(!dslContext.getFunctions().containsKey(functionName)) {
			throw new IllegalArgumentException("No such function: " + functionName);
		}
		DslFunction function = dslContext.getFunctions().get(functionName);
		List<Object> values = functionCallCtx.children.stream().filter(ch -> ch instanceof AnyValueContext).map(childCtx -> parseValueContext((ParserRuleContext) childCtx)).collect(Collectors.toList());
		value = function.execute(values.toArray(new Object[]{}));
		return value;
	}

	private void addFilterField(FilterField filterField) {
		FilterField last = junctionStack.peek();
		if(last != null) {
			if(last.getChildren() == null) {
				last.setChildren(new ArrayList<>());
			}

			last.getChildren().add(filterField);
		} else {
			filter.add(filterField);
		}
	}

}
