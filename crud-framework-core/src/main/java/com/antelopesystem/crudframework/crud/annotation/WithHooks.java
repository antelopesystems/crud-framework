package com.antelopesystem.crudframework.crud.annotation;

import com.antelopesystem.crudframework.crud.hooks.interfaces.CRUDHooks;

import java.lang.annotation.*;

/**
 * Entity annotation, used to define generic persistent hooks which will run on the given entity
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(WithHooks.List.class)
public @interface WithHooks {

	/**
	 * The hook classes, each class provided must be an active bean
	 */
	Class<? extends CRUDHooks<?, ?>>[] hooks() default {};

	@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@interface List {

		WithHooks[] value() default {};
	}
}
