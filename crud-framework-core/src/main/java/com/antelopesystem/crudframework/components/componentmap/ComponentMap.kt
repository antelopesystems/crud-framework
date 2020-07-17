package com.antelopesystem.crudframework.components.componentmap

import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
annotation class ComponentMap(val key: KClass<*> = Unit::class, val value: KClass<*> = Unit::class) 