package com.antelopesystem.crudframework.modelfilter;

import com.antelopesystem.crudframework.modelfilter.enums.FilterFieldDataType;
import com.antelopesystem.crudframework.modelfilter.enums.FilterFieldOperation;

import java.util.*;

public final class FilterFields {

	private FilterFields() {
	}

	public static FilterField eq(String fieldName, FilterFieldDataType dataType, Object value) {
		return createFilterField(fieldName, FilterFieldOperation.Equal, dataType, null, value);
	}

	public static FilterField eq(String fieldName, FilterFieldDataType dataType, String enumType, Object value) {
		return createFilterField(fieldName, FilterFieldOperation.Equal, dataType, enumType, value);
	}

	public static FilterField eq(String fieldName, String value) {
		return createFilterField(fieldName, FilterFieldOperation.Equal, FilterFieldDataType.String, null, value);
	}

	public static FilterField eq(String fieldName, Integer value) {
		return createFilterField(fieldName, FilterFieldOperation.Equal, FilterFieldDataType.Integer, null, value);
	}

	public static FilterField eq(String fieldName, Long value) {
		return createFilterField(fieldName, FilterFieldOperation.Equal, FilterFieldDataType.Long, null, value);
	}

	public static FilterField eq(String fieldName, Double value) {
		return createFilterField(fieldName, FilterFieldOperation.Equal, FilterFieldDataType.Double, null, value);
	}

	public static FilterField eq(String fieldName, Boolean value) {
		return createFilterField(fieldName, FilterFieldOperation.Equal, FilterFieldDataType.Boolean, null, value);
	}

	public static FilterField eq(String fieldName, Date value) {
		return createFilterField(fieldName, FilterFieldOperation.Equal, FilterFieldDataType.Date, null, value);
	}

	public static FilterField eq(String fieldName, Enum<?> value) {
		return createFilterField(fieldName, FilterFieldOperation.Equal, FilterFieldDataType.Enum, value.getClass().getName(), value);
	}

	public static FilterField ne(String fieldName, FilterFieldDataType dataType, Object value) {
		return createFilterField(fieldName, FilterFieldOperation.NotEqual, dataType, null, value);
	}

	public static FilterField ne(String fieldName, FilterFieldDataType dataType, String enumType, Object value) {
		return createFilterField(fieldName, FilterFieldOperation.NotEqual, dataType, enumType, value);
	}

	public static FilterField ne(String fieldName, String value) {
		return createFilterField(fieldName, FilterFieldOperation.NotEqual, FilterFieldDataType.String, null, value);
	}

	public static FilterField ne(String fieldName, Integer value) {
		return createFilterField(fieldName, FilterFieldOperation.NotEqual, FilterFieldDataType.Integer, null, value);
	}

	public static FilterField ne(String fieldName, Long value) {
		return createFilterField(fieldName, FilterFieldOperation.NotEqual, FilterFieldDataType.Long, null, value);
	}

	public static FilterField ne(String fieldName, Double value) {
		return createFilterField(fieldName, FilterFieldOperation.NotEqual, FilterFieldDataType.Double, null, value);
	}

	public static FilterField ne(String fieldName, Boolean value) {
		return createFilterField(fieldName, FilterFieldOperation.NotEqual, FilterFieldDataType.Boolean, null, value);
	}

	public static FilterField ne(String fieldName, Date value) {
		return createFilterField(fieldName, FilterFieldOperation.NotEqual, FilterFieldDataType.Date, null, value);
	}

	public static FilterField ne(String fieldName, Enum<?> value) {
		return createFilterField(fieldName, FilterFieldOperation.NotEqual, FilterFieldDataType.Enum, value.getClass().getSimpleName(), value);
	}

	public static FilterField in(String fieldName, FilterFieldDataType dataType, Object... values) {
		return createFilterField(fieldName, FilterFieldOperation.In, dataType, null, values);
	}

	public static FilterField in(String fieldName, FilterFieldDataType dataType, String enumType, Object... values) {
		return createFilterField(fieldName, FilterFieldOperation.In, dataType, enumType, values);
	}

	public static FilterField in(String fieldName, String... values) {
		if(values != null && values.length > 0) {
			return createFilterField(fieldName, FilterFieldOperation.In, FilterFieldDataType.String, null, values);
		}
		return null;
	}

	public static FilterField in(String fieldName, Integer... values) {
		if(values != null && values.length > 0) {
			return createFilterField(fieldName, FilterFieldOperation.In, FilterFieldDataType.Integer, null, values);
		}
		return null;
	}

	public static FilterField in(String fieldName, Long... values) {
		if(values != null && values.length > 0) {
			return createFilterField(fieldName, FilterFieldOperation.In, FilterFieldDataType.Long, null, values);
		}
		return null;
	}

	public static FilterField in(String fieldName, Double... values) {
		if(values != null && values.length > 0) {
			return createFilterField(fieldName, FilterFieldOperation.In, FilterFieldDataType.Double, null, values);
		}
		return null;
	}

	public static FilterField in(String fieldName, Date... values) {
		if(values != null && values.length > 0) {
			return createFilterField(fieldName, FilterFieldOperation.In, FilterFieldDataType.Date, null, values);
		}
		return null;
	}

	public static FilterField in(String fieldName, Enum<?>... values) {
		if(values != null && values.length > 0) {
			return createFilterField(fieldName, FilterFieldOperation.In, FilterFieldDataType.Enum, values[0].getClass().getSimpleName(), values);
		}
		return null;
	}

	public static FilterField inString(String fieldName, List<String> values) {
		if(values != null && !values.isEmpty()) {
			return createFilterField(fieldName, FilterFieldOperation.In, FilterFieldDataType.String, null, values.toArray());
		}
		return null;
	}

	public static FilterField inInteger(String fieldName, List<Integer> values) {
		if(values != null && !values.isEmpty()) {
			return createFilterField(fieldName, FilterFieldOperation.In, FilterFieldDataType.Integer, null, values.toArray());
		}
		return null;
	}

	public static FilterField inLong(String fieldName, List<Long> values) {
		if(values != null && !values.isEmpty()) {
			return createFilterField(fieldName, FilterFieldOperation.In, FilterFieldDataType.Long, null, values.toArray());
		}
		return null;
	}

	public static FilterField inDouble(String fieldName, List<Double> values) {
		if(values != null && !values.isEmpty()) {
			return createFilterField(fieldName, FilterFieldOperation.In, FilterFieldDataType.Double, null, values.toArray());
		}
		return null;
	}

	public static FilterField inDate(String fieldName, List<Date> values) {
		if(values != null && !values.isEmpty()) {
			return createFilterField(fieldName, FilterFieldOperation.In, FilterFieldDataType.Date, null, values.toArray());
		}
		return null;
	}

	public static FilterField inEnum(String fieldName, List<? extends Enum> values) {
		if(values != null && !values.isEmpty()) {
			return createFilterField(fieldName, FilterFieldOperation.In, FilterFieldDataType.Enum, values.get(0).getClass().getSimpleName(), values.toArray());
		}
		return null;
	}

	public static FilterField notIn(String fieldName, FilterFieldDataType dataType, Object... values) {
		return createFilterField(fieldName, FilterFieldOperation.NotIn, dataType, null, values);
	}

	public static FilterField notIn(String fieldName, FilterFieldDataType dataType, String enumType, Object... values) {
		return createFilterField(fieldName, FilterFieldOperation.NotIn, dataType, enumType, values);
	}

	public static FilterField notIn(String fieldName, String... values) {
		if(values != null && values.length > 0) {
			return createFilterField(fieldName, FilterFieldOperation.NotIn, FilterFieldDataType.String, null, values);
		}
		return null;
	}

	public static FilterField notIn(String fieldName, Integer... values) {
		if(values != null && values.length > 0) {
			return createFilterField(fieldName, FilterFieldOperation.NotIn, FilterFieldDataType.Integer, null, values);
		}
		return null;
	}

	public static FilterField notIn(String fieldName, Long... values) {
		if(values != null && values.length > 0) {
			return createFilterField(fieldName, FilterFieldOperation.NotIn, FilterFieldDataType.Long, null, values);
		}
		return null;
	}

	public static FilterField notIn(String fieldName, Double... values) {
		if(values != null && values.length > 0) {
			return createFilterField(fieldName, FilterFieldOperation.NotIn, FilterFieldDataType.Double, null, values);
		}
		return null;
	}

	public static FilterField notIn(String fieldName, Date... values) {
		if(values != null && values.length > 0) {
			return createFilterField(fieldName, FilterFieldOperation.NotIn, FilterFieldDataType.Date, null, values);
		}
		return null;
	}

	public static FilterField notIn(String fieldName, Enum<?>... values) {
		if(values != null && values.length > 0) {
			return createFilterField(fieldName, FilterFieldOperation.NotIn, FilterFieldDataType.Enum, values[0].getClass().getSimpleName(), values);
		}
		return null;
	}

	public static FilterField notInString(String fieldName, List<String> values) {
		if(values != null && !values.isEmpty()) {
			return createFilterField(fieldName, FilterFieldOperation.NotIn, FilterFieldDataType.String, null, values.toArray());
		}
		return null;
	}

	public static FilterField notInInteger(String fieldName, List<Integer> values) {
		if(values != null && !values.isEmpty()) {
			return createFilterField(fieldName, FilterFieldOperation.NotIn, FilterFieldDataType.Integer, null, values.toArray());
		}
		return null;
	}

	public static FilterField notInLong(String fieldName, List<Long> values) {
		if(values != null && !values.isEmpty()) {
			return createFilterField(fieldName, FilterFieldOperation.NotIn, FilterFieldDataType.Long, null, values.toArray());
		}
		return null;
	}

	public static FilterField notInDouble(String fieldName, List<Double> values) {
		if(values != null && !values.isEmpty()) {
			return createFilterField(fieldName, FilterFieldOperation.NotIn, FilterFieldDataType.Double, null, values.toArray());
		}
		return null;
	}

	public static FilterField notInDate(String fieldName, List<Date> values) {
		if(values != null && !values.isEmpty()) {
			return createFilterField(fieldName, FilterFieldOperation.NotIn, FilterFieldDataType.Date, null, values.toArray());
		}
		return null;
	}

	public static FilterField notInEnum(String fieldName, List<Enum<?>> values) {
		if(values != null && !values.isEmpty()) {
			return createFilterField(fieldName, FilterFieldOperation.NotIn, FilterFieldDataType.Enum, values.get(0).getClass().getSimpleName(), values.toArray());
		}
		return null;
	}

	public static FilterField gt(String fieldName, FilterFieldDataType dataType, Object value) {
		return createFilterField(fieldName, FilterFieldOperation.GreaterThan, dataType, null, value);
	}

	public static FilterField gt(String fieldName, Integer value) {
		return createFilterField(fieldName, FilterFieldOperation.GreaterThan, FilterFieldDataType.Integer, null, value);
	}

	public static FilterField gt(String fieldName, Long value) {
		return createFilterField(fieldName, FilterFieldOperation.GreaterThan, FilterFieldDataType.Long, null, value);
	}

	public static FilterField gt(String fieldName, Double value) {
		return createFilterField(fieldName, FilterFieldOperation.GreaterThan, FilterFieldDataType.Double, null, value);
	}

	public static FilterField gt(String fieldName, Date value) {
		return createFilterField(fieldName, FilterFieldOperation.GreaterThan, FilterFieldDataType.Date, null, value);
	}

	public static FilterField ge(String fieldName, FilterFieldDataType dataType, Object value) {
		return createFilterField(fieldName, FilterFieldOperation.GreaterEqual, dataType, null, value);
	}

	public static FilterField ge(String fieldName, Integer value) {
		return createFilterField(fieldName, FilterFieldOperation.GreaterEqual, FilterFieldDataType.Integer, null, value);
	}

	public static FilterField ge(String fieldName, Long value) {
		return createFilterField(fieldName, FilterFieldOperation.GreaterEqual, FilterFieldDataType.Long, null, value);
	}

	public static FilterField ge(String fieldName, Double value) {
		return createFilterField(fieldName, FilterFieldOperation.GreaterEqual, FilterFieldDataType.Double, null, value);
	}

	public static FilterField ge(String fieldName, Date value) {
		return createFilterField(fieldName, FilterFieldOperation.GreaterEqual, FilterFieldDataType.Date, null, value);
	}

	public static FilterField lt(String fieldName, FilterFieldDataType dataType, Object value) {
		return createFilterField(fieldName, FilterFieldOperation.LowerThan, dataType, null, value);
	}

	public static FilterField lt(String fieldName, Integer value) {
		return createFilterField(fieldName, FilterFieldOperation.LowerThan, FilterFieldDataType.Integer, null, value);
	}

	public static FilterField lt(String fieldName, Long value) {
		return createFilterField(fieldName, FilterFieldOperation.LowerThan, FilterFieldDataType.Long, null, value);
	}

	public static FilterField lt(String fieldName, Double value) {
		return createFilterField(fieldName, FilterFieldOperation.LowerThan, FilterFieldDataType.Double, null, value);
	}

	public static FilterField lt(String fieldName, Date value) {
		return createFilterField(fieldName, FilterFieldOperation.LowerThan, FilterFieldDataType.Date, null, value);
	}

	public static FilterField le(String fieldName, FilterFieldDataType dataType, Object value) {
		return createFilterField(fieldName, FilterFieldOperation.LowerEqual, dataType, null, value);
	}

	public static FilterField le(String fieldName, Integer value) {
		return createFilterField(fieldName, FilterFieldOperation.LowerEqual, FilterFieldDataType.Integer, null, value);
	}

	public static FilterField le(String fieldName, Long value) {
		return createFilterField(fieldName, FilterFieldOperation.LowerEqual, FilterFieldDataType.Long, null, value);
	}

	public static FilterField le(String fieldName, Double value) {
		return createFilterField(fieldName, FilterFieldOperation.LowerEqual, FilterFieldDataType.Double, null, value);
	}

	public static FilterField le(String fieldName, Date value) {
		return createFilterField(fieldName, FilterFieldOperation.LowerEqual, FilterFieldDataType.Date, null, value);
	}

	public static FilterField between(String fieldName, FilterFieldDataType dataType, Object low, Object high) {
		return createFilterField(fieldName, FilterFieldOperation.Between, dataType, null, low, high);
	}

	public static FilterField between(String fieldName, Integer low, Integer high) {
		return createFilterField(fieldName, FilterFieldOperation.Between, FilterFieldDataType.Integer, null, low, high);
	}

	public static FilterField between(String fieldName, Long low, Long high) {
		return createFilterField(fieldName, FilterFieldOperation.Between, FilterFieldDataType.Long, null, low, high);
	}

	public static FilterField between(String fieldName, Double low, Double high) {
		return createFilterField(fieldName, FilterFieldOperation.Between, FilterFieldDataType.Double, null, low, high);
	}

	public static FilterField between(String fieldName, Date low, Date high) {
		return createFilterField(fieldName, FilterFieldOperation.Between, FilterFieldDataType.Date, null, low, high);
	}

	public static FilterField contains(String fieldName, String value) {
		return createFilterField(fieldName, FilterFieldOperation.Contains, FilterFieldDataType.String, null, value);
	}

	public static FilterField startsWith(String fieldName, String value) {
		return createFilterField(fieldName, FilterFieldOperation.StartsWith, FilterFieldDataType.String, null, value);
	}

	public static FilterField endsWith(String fieldName, String value) {
		return createFilterField(fieldName, FilterFieldOperation.EndsWith, FilterFieldDataType.String, null, value);
	}

	public static FilterField containsIn(String fieldName, String... values) {
		if(values != null && values.length > 0) {
			return createFilterField(fieldName, FilterFieldOperation.ContainsIn, FilterFieldDataType.String, null, values);
		}
		return null;
	}

	public static FilterField rawJunction(BaseRawJunction rawJunctionDTO) {
		if(rawJunctionDTO == null || rawJunctionDTO.getJunction() == null) {
			return null;
		}

		return createFilterField("", FilterFieldOperation.RawJunction, FilterFieldDataType.None, null, rawJunctionDTO);
	}

	public static FilterField notContainsIn(String fieldName, String... values) {
		if(values != null && values.length > 0) {
			return createFilterField(fieldName, FilterFieldOperation.NotContainsIn, FilterFieldDataType.String, null, values);
		}
		return null;
	}

	public static FilterField isNull(String fieldName) {
		return createFilterField(fieldName, FilterFieldOperation.IsNull, null, null, null);
	}

	public static FilterField isNotNull(String fieldName) {
		return createFilterField(fieldName, FilterFieldOperation.IsNotNull, null, null, null);
	}

	public static FilterField or(FilterField... filterFields) {
		FilterField filterField = new FilterField();
		filterField.setOperation(FilterFieldOperation.Or);
		filterField.setChildren(Arrays.asList(filterFields));
		return filterField;
	}

	private static FilterField createFilterField(String fieldName, FilterFieldOperation operation, FilterFieldDataType dataType, String enumType, Object... values) {
		if(enumType == null) {
			return new FilterField(fieldName, operation, dataType, values);
		}

		return new FilterField(fieldName, operation, enumType, values);
	}

}