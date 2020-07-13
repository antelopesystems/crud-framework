package com.antelopesystem.crudframework.fieldmapper.annotation;

import com.antelopesystem.crudframework.fieldmapper.transformer.DefaultTransformer;
import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformer;

import java.lang.annotation.*;

/**
 * Indicates that an annotated field should be mapped to a field on a different model by the {@link com.antelopesystem.crudframework.fieldmapper.FieldMapper}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Repeatable(MappedFields.class)
public @interface MappedField {

	/**
	 * The target class to map the field to
	 */
	Class<?> target() default Void.class;


	/**
	 * (Optional) The field name to map the value from.
	 * When used  at the field level, allows for mapping of nested values
	 * If left empty at the type level, an exception will be thrown. Otherwise, the name of the field will be used.
	 */
	String mapFrom() default "";

	/**
	 * (Optional) The field name to map the value to.
	 * If left empty, the name of the field will be used.
	 */
	String mapTo() default "";

	/**
	 * The {@link FieldTransformer} to use on the value
	 */
	Class<? extends FieldTransformer> transformer() default DefaultTransformer.class;

	/**
	 * Bean name for a defined {@link FieldTransformer} bean to use on the value
	 * Supersedes {@link MappedField#transformer()} if specified
	 */
	String transformerRef() default "";
}

