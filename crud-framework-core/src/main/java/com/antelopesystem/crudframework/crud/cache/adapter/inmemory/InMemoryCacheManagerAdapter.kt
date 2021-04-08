package com.antelopesystem.crudframework.crud.cache.adapter.inmemory

import com.antelopesystem.crudframework.crud.cache.CacheManagerAdapter
import com.antelopesystem.crudframework.crud.cache.CrudCache
import com.antelopesystem.crudframework.crud.cache.CrudCacheOptions
import org.slf4j.LoggerFactory

class InMemoryCacheManagerAdapter : CacheManagerAdapter {
    private val caches = mutableMapOf<String, CrudCache>()
    override fun getCache(name: String) : CrudCache? {
        log.debug("Attempting to find cache with name [ $name ]")
        val cache = caches[name]
        if(cache != null) {
            log.debug("Found cache with $name")
        } else {
            log.debug("Did not find cache with $name")
        }

        return cache
    }

    override fun createCache(name: String, options: CrudCacheOptions): CrudCache {
        log.debug("Attempting to create or return cache with name [ $name ]")
        return caches.computeIfAbsent(name) {
            log.debug("Cache with name [ $name ] did not exist, creating")
            InMemoryCrudCache()
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(InMemoryCacheManagerAdapter::class.java)
    }
}



