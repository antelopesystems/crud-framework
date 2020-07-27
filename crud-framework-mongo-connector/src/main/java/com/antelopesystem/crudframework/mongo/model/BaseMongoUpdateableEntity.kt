package com.antelopesystem.crudframework.mongo.model

import com.antelopesystem.crudframework.fieldmapper.annotation.MappedField
import com.antelopesystem.crudframework.fieldmapper.transformer.DateToLongTransformer
import com.antelopesystem.crudframework.mongo.ro.BaseMongoUpdatableRO
import java.util.*

abstract class BaseMongoUpdateableEntity : BaseMongoEntity() {
    @MappedField(target = BaseMongoUpdatableRO::class, transformer = DateToLongTransformer::class)
    var lastUpdateTime: Date = Date()
}