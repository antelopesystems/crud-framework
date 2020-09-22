package com.antelopesystem.crudframework.transformer

import com.antelopesystem.crudframework.crud.handler.CrudHandler
import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase
import com.antelopesystem.crudframework.model.BaseCrudEntity
import com.antelopesystem.crudframework.ro.BaseRO
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
import java.lang.reflect.Field

class EntityListToRoListTransformer(private val crudHandler: CrudHandler) : FieldTransformerBase<List<BaseCrudEntity<*>>?, List<BaseRO<*>?>>() {
    override fun innerTransform(fromField: Field, toField: Field, originalValue: List<BaseCrudEntity<*>>?): List<BaseRO<*>>? {
        if (originalValue == null) {
            return null
        }
        val roList = mutableListOf<BaseRO<*>>()
        for (entity in originalValue) {
            val type = (toField.genericType as ParameterizedTypeImpl).actualTypeArguments[0]
            roList += crudHandler.getRO(entity, type as Class<*>) as BaseRO<*>
        }
        return roList.toList()
    }
}

