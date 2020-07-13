package com.antelopesystem.crudframework.fieldmapper.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface DefaultMappingTarget {

	Class<?> value();
}
