package com.antelopesystem.crudframework.components.componentmap;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentMap {

	Class<?> key() default void.class;

	Class<?> value() default void.class;
}
