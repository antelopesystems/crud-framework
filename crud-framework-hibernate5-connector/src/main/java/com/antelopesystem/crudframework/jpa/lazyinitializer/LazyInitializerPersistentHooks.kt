package com.antelopesystem.crudframework.jpa.lazyinitializer

import com.antelopesystem.crudframework.crud.hooks.interfaces.*
import com.antelopesystem.crudframework.jpa.lazyinitializer.annotation.InitializeLazyOn
import com.antelopesystem.crudframework.jpa.model.BaseJpaEntity
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter
import com.antelopesystem.crudframework.ro.PagingDTO
import com.antelopesystem.crudframework.utils.utils.ReflectionUtils
import org.hibernate.Hibernate

class LazyInitializerPersistentHooks : ShowHooks<Long, BaseJpaEntity>,
        ShowByHooks<Long, BaseJpaEntity>,
        IndexHooks<Long, BaseJpaEntity>,
        UpdateHooks<Long, BaseJpaEntity>,
        UpdateFromHooks<Long, BaseJpaEntity>,
        CreateHooks<Long, BaseJpaEntity>,
        CreateFromHooks<Long, BaseJpaEntity> {

    override fun onShow(entity: BaseJpaEntity?) {
        entity ?: return
        initializeLazyFields(entity) { it.show }
    }

    override fun onCreateFrom(entity: BaseJpaEntity, ro: Any) {
        initializeLazyFields(entity) { it.createFrom }
    }

    override fun onCreate(entity: BaseJpaEntity) {
        initializeLazyFields(entity) { it.create }
    }

    override fun onIndex(filter: DynamicModelFilter, result: PagingDTO<BaseJpaEntity>) {
        result.data ?: return
        for (entity in result.data) {
            initializeLazyFields(entity) { it.index }
        }
    }

    override fun onShowBy(entity: BaseJpaEntity?) {
        entity ?: return
        initializeLazyFields(entity) { it.showBy }
    }

    override fun onUpdateFrom(entity: BaseJpaEntity, ro: Any) {
        initializeLazyFields(entity) { it.updateFrom }
    }

    override fun onUpdate(entity: BaseJpaEntity) {
        initializeLazyFields(entity) { it.update }
    }

    private fun initializeLazyFields(entity: BaseJpaEntity, condition: (annotation: InitializeLazyOn) -> Boolean) {
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