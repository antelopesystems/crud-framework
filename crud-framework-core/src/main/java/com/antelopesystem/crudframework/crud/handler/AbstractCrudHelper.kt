package com.antelopesystem.crudframework.crud.handler

import com.antelopesystem.crudframework.crud.cache.CrudCache
import com.antelopesystem.crudframework.crud.dataaccess.DataAccessManager
import com.antelopesystem.crudframework.crud.dataaccess.model.DataAccessorDTO
import com.antelopesystem.crudframework.crud.decorator.ObjectDecorator
import com.antelopesystem.crudframework.crud.hooks.interfaces.CRUDHooks
import com.antelopesystem.crudframework.crud.model.EntityMetadataDTO
import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformer
import com.antelopesystem.crudframework.model.BaseCrudEntity
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter
import com.antelopesystem.crudframework.modelfilter.FilterField
import java.io.Serializable

abstract class AbstractCrudHelper : CrudHelper {
    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?, HooksType : CRUDHooks<*, out BaseCrudEntity<*>>?> getHooks(crudHooksClazz: Class<HooksType>?, entityClazz: Class<Entity>?): MutableList<HooksType> {
        throw UnsupportedOperationException()
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> isEntityDeleted(entity: Entity): Boolean {
        throw UnsupportedOperationException()
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> decorateFilter(filter: DynamicModelFilter?, entityClazz: Class<Entity>?, accessorDTO: DataAccessorDTO?, forUpdate: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun validateAndFillFilterFieldMetadata(filterFields: MutableList<FilterField>?, metadataDTO: EntityMetadataDTO?) {
        throw UnsupportedOperationException()
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> getEntities(
        filter: DynamicModelFilter?,
        entityClazz: Class<Entity>?,
        accessorDTO: DataAccessorDTO?,
        persistCopy: Boolean?,
        forUpdate: Boolean
    ): MutableList<Entity> {
        throw UnsupportedOperationException()
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> getEntitiesCount(filter: DynamicModelFilter?, entityClazz: Class<Entity>?, accessorDTO: DataAccessorDTO?, forUpdate: Boolean): Long {
        throw UnsupportedOperationException()
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> getEntityById(entityId: ID, entityClazz: Class<Entity>?, persistCopy: Boolean?, accessorDTO: DataAccessorDTO?, forUpdate: Boolean): Entity {
        throw UnsupportedOperationException()
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> getEntityCountById(entityId: ID, entityClazz: Class<Entity>?, accessorDTO: DataAccessorDTO?, forUpdate: Boolean): Long {
        throw UnsupportedOperationException()
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> getAccessorManager(accessorClazz: Class<*>?, entityClazz: Class<Entity>?): DataAccessManager<*, *, *> {
        throw UnsupportedOperationException()
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> checkEntityImmutability(clazz: Class<Entity>?) {
        throw UnsupportedOperationException()
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> checkEntityDeletability(clazz: Class<Entity>?) {
        throw UnsupportedOperationException()
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> getEntityMetadata(entityClazz: Class<Entity>?): EntityMetadataDTO {
        throw UnsupportedOperationException()
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> evictEntityFromCache(entity: Entity) {
        throw UnsupportedOperationException()
    }

    override fun <Entity : Any?, E : Any?> getObjectDecorator(fromClass: Class<Entity>?, toClass: Class<E>?): ObjectDecorator<Entity, E> {
        throw UnsupportedOperationException()
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> getEntityCache(clazz: Class<Entity>?): CrudCache {
        throw UnsupportedOperationException()
    }

    override fun validate(target: Any?) {
        throw UnsupportedOperationException()
    }

    override fun <From : Any?, To : Any?> fill(fromObject: From, toClazz: Class<To>): To {
        throw UnsupportedOperationException()
    }

    override fun <From : Any?, To : Any?> fill(fromObject: From, toObject: To) {
        throw UnsupportedOperationException()
    }

    override fun <From : Any?, To : Any?> fillMany(fromObjects: MutableList<From>, toClazz: Class<To>): List<To> {
        throw UnsupportedOperationException()
    }

    override fun <Entity : Any?> setTotalToPagingCache(entityClazz: Class<Entity>?, filter: DynamicModelFilter?, total: Long) {
        throw UnsupportedOperationException()
    }

    override fun <Entity : Any?> getTotalFromPagingCache(entityClazz: Class<Entity>?, filter: DynamicModelFilter?): Long {
        throw UnsupportedOperationException()
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> getCrudDaoForEntity(entityClazz: Class<Entity>?): CrudDao {
        throw UnsupportedOperationException()
    }

    override fun registerDefaultTransformer(transformer: FieldTransformer<*, *>?) {
        throw UnsupportedOperationException()
    }

    override fun registerDefaultTransformer(transformer: FieldTransformer<*, *>?, fromType: Class<*>?, toType: Class<*>?) {
        throw UnsupportedOperationException()
    }
}