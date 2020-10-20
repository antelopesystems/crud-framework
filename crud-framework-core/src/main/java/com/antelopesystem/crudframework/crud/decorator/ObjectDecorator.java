package com.antelopesystem.crudframework.crud.decorator;

import com.antelopesystem.crudframework.utils.component.componentmap.annotation.ComponentMapKey;
import org.jetbrains.annotations.NotNull;

/**
 * Class that handles decorations when converting {@code fromObject} to {@code toObject}. Must be defined as a bean
 *
 * @param <From> the fromObject type
 * @param <To> the toObject type
 */
public interface ObjectDecorator<From, To> {

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
	void decorate(@NotNull From fromObject, @NotNull To toObject);

}
