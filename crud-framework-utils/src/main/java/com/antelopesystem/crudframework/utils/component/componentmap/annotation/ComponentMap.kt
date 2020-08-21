package com.antelopesystem.crudframework.utils.component.componentmap.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
annotation class ComponentMap(@Deprecated("no longer used") val key: KClass<*> = Unit::class, @Deprecated("no longer used") val value: KClass<*> = Unit::class)