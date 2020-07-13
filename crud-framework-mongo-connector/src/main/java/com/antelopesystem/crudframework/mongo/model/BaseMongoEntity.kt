package com.antelopesystem.crudframework.mongo.model

import com.antelopesystem.crudframework.mongo.annotation.MongoCrudEntity
import com.antelopesystem.crudframework.fieldmapper.annotation.MappedField
import com.antelopesystem.crudframework.fieldmapper.transformer.DateToLongTransformer
import com.antelopesystem.crudframework.model.BaseCrudEntity
import com.antelopesystem.crudframework.mongo.ro.BaseMongoRO
import java.util.*


@MongoCrudEntity
abstract class BaseMongoEntity : BaseCrudEntity<String>() {
    @MappedField(target = BaseMongoRO::class)
    override var id: String = ""

    @MappedField(target = BaseMongoRO::class, transformer = DateToLongTransformer::class)
    override var creationTime: Date = Date()

    override fun exists(): Boolean = id.isNotBlank()
}

