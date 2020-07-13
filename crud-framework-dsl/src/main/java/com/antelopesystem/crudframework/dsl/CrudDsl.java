package com.antelopesystem.crudframework.dsl;

import com.antelopesystem.crudframework.dsl.context.DefaultDslContext;
import com.antelopesystem.crudframework.dsl.context.DslContext;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.io.InputStream;

public class CrudDsl {

	private final DslContext dslContext;

	public CrudDsl() {
		dslContext = new DefaultDslContext();
	}

	public CrudDsl(DslContext dslContext) {
		this.dslContext = dslContext;
	}

	public DynamicModelFilter parseInput(InputStream input) throws IOException {
		return parseInput(CharStreams.fromStream(input));
	}

	public DynamicModelFilter parseInput(String input) {
		return parseInput(CharStreams.fromString(input));
	}

	public DynamicModelFilter parseInput(CharStream input) {
		CrudDslLexer lexer = new CrudDslLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CrudDslParser parser = new CrudDslParser(tokens);
		CrudDslListener listener = new CrudDslListener(dslContext);
		ParseTreeWalker.DEFAULT.walk(listener, parser.query());
		return listener.getFilter();
	}

}
