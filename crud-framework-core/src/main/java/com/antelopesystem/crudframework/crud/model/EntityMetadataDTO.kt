package com.antelopesystem.crudframework.crud.model

import com.antelopesystem.crudframework.crud.annotation.*
import com.antelopesystem.crudframework.crud.handler.CrudDao
import com.antelopesystem.crudframework.crud.hooks.interfaces.CRUDHooks
import com.antelopesystem.crudframework.model.BaseCrudEntity
import com.antelopesystem.crudframework.model.PersistentEntity
import com.antelopesystem.crudframework.utils.utils.ReflectionUtils
import org.springframework.core.annotation.AnnotationUtils
import java.lang.reflect.Field
import kotlin.reflect.KClass

class EntityMetadataDTO {

    val simpleName: String;

    val deleteField: Field?

    val deleteableType: DeleteableType

    val cacheName: String?

    val immutable: Boolean

    val alwaysPersistCopy: Boolean

    val hookTypesFromAnnotations: MutableSet<Class<CRUDHooks<*, *>>> = mutableSetOf()

    val hooksFromAnnotations: MutableSet<CRUDHooks<*, *>> = mutableSetOf()

    val fields: MutableMap<String, Class<*>> = mutableMapOf()

    val daoClazz: Class<out CrudDao>

    constructor(entityClazz: KClass<out BaseCrudEntity<*>>) : this(entityClazz.java)

    constructor(entityClazz: Class<out BaseCrudEntity<*>>) {
        deleteField = getEntityDeleteField(entityClazz)
        deleteableType = getEntityDeleteableType(entityClazz)
        cacheName = getEntityCacheName(entityClazz)
        immutable = isEntityImmutable(entityClazz)
        alwaysPersistCopy = shouldAlwaysPersistCopy(entityClazz)
        collectHookAnnotations(entityClazz)
        daoClazz = getEntityDao(entityClazz)
        getFields(entityClazz)
        simpleName = entityClazz.simpleName
    }

    private fun getFields(entityClazz: Class<out PersistentEntity>, prefix: String? = null, currentDepth: Int = 0) {
        val effectivePrefix: String
        if(prefix.isNullOrBlank()) {
            effectivePrefix = ""
        } else {
            effectivePrefix = prefix.replace(".", "/") + "."
        }

        ReflectionUtils.getFields(entityClazz).forEach {
            if(it.name == "copy" && it.type == BaseCrudEntity::class.java) {
                return
            }

            if(PersistentEntity::class.java.isAssignableFrom(it.type) && currentDepth < MAX_FILTERFIELD_DEPTH) {
                getFields(it.type as Class<out PersistentEntity>, effectivePrefix + it.name, currentDepth + 1)
            } else {
                fields[effectivePrefix + it.name] = it.type
            }
        }
    }

    private fun collectHookAnnotations(entityClazz: Class<out BaseCrudEntity<*>>) {
        val hookAnnotations = mutableSetOf<WithHooks>()

        // The first search targets the WithHooks.List annotation, which is the repeatable container for WithHooks
        val hookAnnotationsRepeatable = AnnotationUtils.findAnnotation(entityClazz, WithHooks.List::class.java)
        if (hookAnnotationsRepeatable != null) {
            if (hookAnnotationsRepeatable.value.isNotEmpty()) {
                hookAnnotations.addAll(hookAnnotations)
            }
        } else {
            // We run this second search because a nested, single WithHooks annotation in a Kotlin file does not register as WithHooks.List
            val hookAnnotation = AnnotationUtils.findAnnotation(entityClazz, WithHooks::class.java)
            if (hookAnnotation != null) {
                hookAnnotations.add(hookAnnotation)
            }
        }

        if (hookAnnotations.isNotEmpty()) {
            for (hookAnnotation in hookAnnotations) {
                val hooksArray = AnnotationUtils.getAnnotationAttributes(hookAnnotation)["hooks"] as Array<Class<CRUDHooks<*, *>>>
                if (hooksArray.isNotEmpty()) {
                    hookTypesFromAnnotations.addAll(hooksArray.toList())
                }
            }
        }
    }

    private fun getEntityDao(clazz: Class<out BaseCrudEntity<*>>): Class<out CrudDao> {
        return AnnotationUtils.findAnnotation(clazz, CrudEntity::class.java).dao.java
    }

    private fun getEntityCacheName(clazz: Class<out BaseCrudEntity<*>>): String? {
        val cachedBy = clazz.getDeclaredAnnotation(CachedBy::class.java)
        return cachedBy?.value
    }

    private fun getEntityDeleteableType(clazz: Class<out BaseCrudEntity<*>>): DeleteableType {
        val deleteable = clazz.getDeclaredAnnotation(Deleteable::class.java)
        return when {
            deleteable == null -> DeleteableType.None
            deleteable.softDelete -> DeleteableType.Soft
            else -> DeleteableType.Hard
        }
    }

    private fun getEntityDeleteField(clazz: Class<out BaseCrudEntity<*>>): Field? {
        val fields = ReflectionUtils.getFields(clazz)

        var deleteField: Field? = null
        for (field in fields) {
            if (field.getDeclaredAnnotation(DeleteColumn::class.java) != null) {
                deleteField = field
            }
        }

        return deleteField
    }

    private fun isEntityImmutable(clazz: Class<out BaseCrudEntity<*>>): Boolean {
        return clazz.getDeclaredAnnotation(Immutable::class.java) != null
    }

    private fun shouldAlwaysPersistCopy(clazz: Class<out BaseCrudEntity<*>>): Boolean {
        return clazz.getDeclaredAnnotation(PersistCopyOnFetch::class.java) != null
    }

    enum class DeleteableType {
        None, Soft, Hard
    }

    companion object {
        private const val MAX_FILTERFIELD_DEPTH = 4
    }
}