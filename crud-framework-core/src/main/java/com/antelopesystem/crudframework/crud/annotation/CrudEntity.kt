package com.antelopesystem.crudframework.crud.annotation

import com.antelopesystem.crudframework.crud.handler.CrudDao
import kotlin.reflect.KClass

/**
 * Entity annotation, denotes the entity as an entity which can be used in the framework
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
annotation class CrudEntity(
        /**
         * Which [CrudDao] should be used when interacting with the given entity
         */
        val dao: KClass<out CrudDao>
)

