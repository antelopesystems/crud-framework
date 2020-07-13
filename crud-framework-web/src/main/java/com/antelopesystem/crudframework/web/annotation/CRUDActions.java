package com.antelopesystem.crudframework.web.annotation;

import com.antelopesystem.crudframework.web.controller.BaseCRUDController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to define the available CRUD actions in the {@link BaseCRUDController}
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CRUDActions {

	/**
	 * Index action toggle, false to block
	 */
	boolean index() default true;

	/**
	 * Show action toggle, false to block
	 */
	boolean show() default true;

	/**
	 * Update action toggle, false to block
	 */
	boolean update() default true;

	/**
	 * Create action toggle, false to block
	 */
	boolean create() default true;

	/**
	 * Delete action toggle, false to block
	 */
	boolean delete() default true;
}
