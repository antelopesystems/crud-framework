package com.antelopesystem.crudframework.exception.tree.core;

import com.antelopesystem.crudframework.exception.tree.exception.ServerException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionOverride {

	Class<? extends ServerException> value();

	ErrorCode errorCode() default ErrorCode.GeneralError;

	LogLevel logLevel() default LogLevel.Error;

	String loggerOverride() default "";

	boolean logArguments() default true;

	boolean logStackTrace() default false;

	boolean rethrow() default true;

}
