package com.antelopesystem.crudframework.crud.cache

/**
 * Options available when creating a cache
 * Not all options are supported by all vendors
 */
data class CrudCacheOptions(
        val timeToLiveSeconds: Long? = null,
        val timeToIdleSeconds: Long? = null,
        val maxEntries: Long? = null
)