package com.antelopesystem.crudframework.mongo.model

import com.antelopesystem.crudframework.mongo.annotation.MongoCrudEntity
import com.antelopesystem.crudframework.fieldmapper.annotation.MappedField
import com.antelopesystem.crudframework.fieldmapper.transformer.DateToLongTransformer
import com.antelopesystem.crudframework.model.BaseCrudEntity
import com.antelopesystem.crudframework.mongo.ro.BaseMongoRO
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.util.*


@MongoCrudEntity
abstract class BaseMongoEntity : BaseCrudEntity<String>() {
    @MappedField(target = BaseMongoRO::class)
    @Id
    override lateinit var id: String

    @MappedField(target = BaseMongoRO::class, transformer = DateToLongTransformer::class)
    var creationTime: Date = Date()

    override fun exists(): Boolean = this::id.isInitialized
}

