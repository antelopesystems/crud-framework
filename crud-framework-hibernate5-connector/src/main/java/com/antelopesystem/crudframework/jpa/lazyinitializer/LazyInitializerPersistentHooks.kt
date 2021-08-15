package com.antelopesystem.crudframework.jpa.lazyinitializer

import com.antelopesystem.crudframework.crud.hooks.interfaces.*
import com.antelopesystem.crudframework.jpa.lazyinitializer.annotation.InitializeLazyOn
import com.antelopesystem.crudframework.model.BaseCrudEntity
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter
import com.antelopesystem.crudframework.ro.PagingDTO
import com.antelopesystem.crudframework.utils.utils.ReflectionUtils
import org.hibernate.Hibernate

class LazyInitializerPersistentHooks : ShowHooks<Long, BaseCrudEntity<Long>>,
        ShowByHooks<Long, BaseCrudEntity<Long>>,
        IndexHooks<Long, BaseCrudEntity<Long>>,
        UpdateHooks<Long, BaseCrudEntity<Long>>,
        UpdateFromHooks<Long, BaseCrudEntity<Long>>,
        CreateHooks<Long, BaseCrudEntity<Long>>,
        CreateFromHooks<Long, BaseCrudEntity<Long>> {

    override fun onShow(entity: BaseCrudEntity<Long>?) {
        entity ?: return
        initializeLazyFields(entity) { it.show }
    }

    override fun onCreateFrom(entity: BaseCrudEntity<Long>, ro: Any) {
        initializeLazyFields(entity) { it.createFrom }
    }

    override fun onCreate(entity: BaseCrudEntity<Long>) {
        initializeLazyFields(entity) { it.create }
    }

    override fun onIndex(filter: DynamicModelFilter, result: PagingDTO<BaseCrudEntity<Long>>) {
        result.data ?: return
        for (entity in result.data) {
            initializeLazyFields(entity) { it.index }
        }
    }

    override fun onShowBy(entity: BaseCrudEntity<Long>?) {
        entity ?: return
        initializeLazyFields(entity) { it.showBy }
    }

    override fun onUpdateFrom(entity: BaseCrudEntity<Long>, ro: Any) {
        initializeLazyFields(entity) { it.updateFrom }
    }

    override fun onUpdate(entity: BaseCrudEntity<Long>) {
        initializeLazyFields(entity) { it.update }
    }

    private fun initializeLazyFields(entity: BaseCrudEntity<Long>, condition: (annotation: InitializeLazyOn) -> Boolean) {
        ReflectionUtils.doWithFields(entity::class.java) {
            val annotation = it.getDeclaredAnnotation(ANNOTATION_TYPE) ?: return@doWithFields
            if(condition(annotation)) {
                ReflectionUtils.makeAccessible(it)
                Hibernate.initialize(it.get(entity))
            }
        }
    }

    companion object {
        private val ANNOTATION_TYPE = InitializeLazyOn::class.java
    }
}