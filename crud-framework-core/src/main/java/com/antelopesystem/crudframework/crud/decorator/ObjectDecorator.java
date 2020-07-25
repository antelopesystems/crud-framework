package com.antelopesystem.crudframework.crud.decorator;

import com.antelopesystem.crudframework.utils.component.componentmap.annotation.ComponentMapKey;

/**
 * Class that handles decorations when converting {@code fromObject} to {@code toObject}. Must be defined as a bean
 *
 * @param <T> the fromObject type
 * @param <E> the toObject type
 */
public interface ObjectDecorator<T, E> {

	/**
	 * Gets Object Decorator type.
	 *
	 * @return the type
	 */
	@ComponentMapKey
	String getType();

	/**
	 * Decorates the target object with access to the source object
	 *
	 * @param fromObject the source object
	 * @param toObject the target object
	 */
	void decorate(T fromObject, E toObject);

}
