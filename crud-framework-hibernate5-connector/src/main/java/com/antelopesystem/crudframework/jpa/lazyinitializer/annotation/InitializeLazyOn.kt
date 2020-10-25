package com.antelopesystem.crudframework.jpa.lazyinitializer.annotation

@Target(AnnotationTarget.FIELD)
annotation class InitializeLazyOn(
        val show : Boolean = false,
        val showBy : Boolean = false,
        val index : Boolean = false,
        val create : Boolean = false,
        val createFrom : Boolean = false,
        val update : Boolean = false,
        val updateFrom : Boolean = false
)