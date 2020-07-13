package com.antelopesystem.crudframework.crud.annotation;

import java.lang.annotation.*;

/**
 * Used to define a given entity as deleteable
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Deleteable {

	/**
	 * Denotes whether the entity is soft deleteable or hard deleteable
	 */
	boolean softDelete();
}
