package com.antelopesystem.crudframework.dsl.context;

import com.antelopesystem.crudframework.dsl.function.DateFunction;
import com.antelopesystem.crudframework.dsl.function.DslFunction;

import java.util.HashMap;
import java.util.Map;

public class DefaultDslContext implements DslContext {
	private static final Map<String, DslFunction> DEFAULT_FUNCTIONS = new HashMap<>();
	private Map<String, DslFunction> functions = new HashMap<>();

	static {
		DEFAULT_FUNCTIONS.put("date", new DateFunction());
	}

	public DefaultDslContext() {
		this.getFunctions().putAll(DEFAULT_FUNCTIONS);
	}

	public DefaultDslContext(Map<String, DslFunction> functions) {
		this.getFunctions().putAll(DEFAULT_FUNCTIONS);
		this.getFunctions().putAll(functions);
	}

	@Override
	public final Map<String, DslFunction> getFunctions() {
		return functions;
	}
}
