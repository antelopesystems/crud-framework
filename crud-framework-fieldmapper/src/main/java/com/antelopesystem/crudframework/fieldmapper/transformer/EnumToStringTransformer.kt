package com.antelopesystem.crudframework.fieldmapper.transformer

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase
import java.lang.reflect.Field

class EnumToStringTransformer : FieldTransformerBase<Enum<*>?, String>() {

    override fun innerTransform(fromField: Field, toField: Field, originalValue: Enum<*>?, fromObject: Any, toObject: Any): String? {
        return originalValue?.name
    }
}
