package com.antelopesystem.crudframework.crud.cache

/**
 * Internal cache abstraction
 */
interface CrudCache {
    fun get(key: Any): Any?
    fun put(key: Any, value: Any?)
    fun remove(key: Any)
    fun removeAll()
    fun unwrap() : Any
}