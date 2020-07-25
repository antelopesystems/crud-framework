package com.antelopesystem.crudframework.utils.component.componentmap.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
annotation class ComponentMap(val key: KClass<*> = Unit::class, val value: KClass<*> = Unit::class) 