package com.antelopesystem.crudframework.fieldmapper.transformer
import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase
import com.antelopesystem.crudframework.fieldmapper.annotation.StringTrimLength
import org.apache.commons.lang3.StringUtils
import java.lang.IllegalStateException
import java.lang.reflect.Field

class StringTrimTransformer : FieldTransformerBase<String?, String?>() {
    override fun innerTransform(fromField: Field, toField: Field, originalValue: String?, fromObject: Any, toObject: Any): String? {
        if (originalValue == null) {
            return null
        }
        val annotation = fromField.getAnnotation(StringTrimLength::class.java) ?: throw IllegalStateException("StringTrimLength annotation missing on field - $toField")
        val trimLength = annotation.value
        return StringUtils.left(originalValue, trimLength)
    }
}