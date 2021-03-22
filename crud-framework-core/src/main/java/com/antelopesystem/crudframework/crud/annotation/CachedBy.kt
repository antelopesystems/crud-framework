package com.antelopesystem.crudframework.crud.annotation

/**
 * Entity annotation, specifies which cache should be used by the framework for the given entity
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class CachedBy(
        /**
         * Cache name in the underlying cache provider
         */
        val value: String
)