package com.antelopesystem.crudframework.exception

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class WrapException(val value: KClass<out Exception>)