package com.antelopesystem.crudframework.fieldmapper.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;

import java.lang.reflect.Field;
import java.util.Locale;

public class CountryIsoToNameTransformer extends FieldTransformerBase<String, String> {

	@Override
	protected String innerTransform(Field fromField, Field toField, String originalValue) {
		if(originalValue == null) {
			return null;
		}

		return new Locale("", originalValue).getDisplayCountry();
	}
}
