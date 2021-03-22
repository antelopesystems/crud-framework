package com.antelopesystem.crudframework.crud.annotation

/**
 * Entity annotation, whether or not to persist a copy of the original state of the entity on fetch
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PersistCopyOnFetch 