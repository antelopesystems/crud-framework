package com.antelopesystem.crudframework.model

import org.springframework.beans.BeanUtils
import org.springframework.cglib.beans.ImmutableBean
import java.io.Serializable
import java.util.*

abstract class BaseCrudEntity<ID : Serializable> : PersistentEntity, Serializable {

    abstract var id: ID

    @Transient
    private var copy: BaseCrudEntity<ID>? = null

    @Transient
    private var isCopy: Boolean = false

    fun saveOrGetCopy(): BaseCrudEntity<ID>? {
        if (copy == null) {
            try {
                if (!isCopy) {
                    val internalCopy = javaClass.newInstance()
                    internalCopy.isCopy = true
                    BeanUtils.copyProperties(this, internalCopy)
                    copy = internalCopy //ImmutableBean.create(internalCopy) as BaseCrudEntity<ID>
                } else {
                    return null
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return copy
    }

    open fun getCacheKey(): String? {
        return getCacheKey(javaClass, id)
    }

    abstract fun exists(): Boolean

    companion object {
        fun getCacheKey(clazz: Class<*>, id: Serializable?): String? {
            return "CacheKey_" + clazz.simpleName + "_" + id
        }
    }

}