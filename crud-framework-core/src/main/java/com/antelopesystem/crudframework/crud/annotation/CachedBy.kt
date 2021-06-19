package com.antelopesystem.crudframework.crud.annotation

/**
 * Entity annotation, specifies which cache should be used by the framework for the given entity
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class CachedBy(
        /**
         * The name of the cache
         */
        val value: String,
        /**
         * Whether or not to create a cache with this name if the cache does not exist
         */
        val createIfMissing: Boolean = false,

        /**
         * Specifies time to live in seconds when creating the cache if missing, may not be supported on all providers.
         * -1 is interpret as null
         */
        val timeToLiveSeconds: Long = -1L,

        /**
         * Specifies time to idle in seconds when creating the cache if missing, may not be supported on all providers
         * -1 is interpret as null
         */
        val timeToIdleSeconds: Long = -1L,

        /**
         * Specifies max cache entries when creating the cache if missing, may not be supported on all providers
         * -1 is interpret as null
         */
        val maxEntries: Long = -1
)