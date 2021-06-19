package com.antelopesystem.crudframework.crud.cache

/**
 * Used to retrieve and create caches
 */
interface CacheManagerAdapter {
    /**
     * Get a cache by its name
     */
    fun getCache(name: String): CrudCache?

    /**
     * Create a cache with the given name and options
     * Not all options are supported by all vendors
     * Whether or not the cache exists or not and how to handle that is passed on to the vendor
     */
    fun createCache(name: String, options: CrudCacheOptions): CrudCache
}