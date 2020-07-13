package com.antelopesystem.crudframework.crud.annotation;

import com.antelopesystem.crudframework.crud.hooks.interfaces.CRUDHooks;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(WithHooks.List.class)
public @interface WithHooks {

	Class<? extends CRUDHooks<?, ?>>[] hooks() default {};

	@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface List {

		WithHooks[] value() default {};
	}
}
