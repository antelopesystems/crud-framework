package com.antelopesystem.crudframework.crud.cache.adapter.inmemory

import com.antelopesystem.crudframework.crud.cache.CrudCache

class InMemoryCrudCache : CrudCache {
    private val internalMap = mutableMapOf<Any, Any?>()

    override fun get(key: Any): Any? = internalMap[key]

    override fun put(key: Any, value: Any?) {
        internalMap[key] = value
    }

    override fun remove(key: Any) {
        internalMap.remove(key)
    }

    override fun removeAll() {
        internalMap.clear()
    }

    override fun unwrap(): Any {
        return internalMap
    }
}