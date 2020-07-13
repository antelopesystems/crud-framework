package com.antelopesystem.crudframework.crud.annotation

import com.antelopesystem.crudframework.crud.handler.CrudDao
import kotlin.reflect.KClass

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
annotation class CrudEntity(val dao: KClass<out CrudDao>)

