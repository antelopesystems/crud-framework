package com.antelopesystem.crudframework.dsl.function;

public interface DslFunction<T> {
	T execute(Object... args);
}
