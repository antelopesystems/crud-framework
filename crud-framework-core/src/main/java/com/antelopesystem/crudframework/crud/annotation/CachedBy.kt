package com.antelopesystem.crudframework.crud.annotation

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class CachedBy(val value: String)