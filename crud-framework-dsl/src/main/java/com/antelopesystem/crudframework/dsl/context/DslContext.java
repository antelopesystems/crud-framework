package com.antelopesystem.crudframework.dsl.context;

import com.antelopesystem.crudframework.dsl.function.DslFunction;

import java.util.Map;

public interface DslContext {
	Map<String, DslFunction> getFunctions();
}
