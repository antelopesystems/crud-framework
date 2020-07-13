package com.antelopesystem.crudframework.mongo.dao

import com.antelopesystem.crudframework.crud.handler.CrudDao
import com.antelopesystem.crudframework.model.BaseCrudEntity
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter
import com.antelopesystem.crudframework.modelfilter.dsl.where
import org.springframework.data.mongodb.core.query.Update
import java.io.Serializable

class MongoCrudDaoImpl : CrudDao, AbstractMongoBaseDao() {
    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?, Filter : DynamicModelFilter> index(filter: Filter, clazz: Class<Entity>?): MutableList<Entity> {
        val query = buildQuery(filter)
        setOrder(query, filter.orderBy, filter.orderDesc)
        setBoundaries(query, filter.start, filter.limit)
        return mongoTemplate.find(query, clazz)
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?, Filter : DynamicModelFilter?> indexCount(filter: Filter, clazz: Class<Entity>?): Long {
        return mongoTemplate.count(buildQuery(filter), clazz)
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> softDeleteById(id: ID, deleteColumn: String?, clazz: Class<Entity>?) {
        mongoTemplate.updateFirst(buildQuery(
                where {
                    "id" Equal id as String
                }),
                Update.update(deleteColumn, true),
                clazz
        )
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> hardDeleteById(id: ID, clazz: Class<Entity>?) {
        deleteObject(clazz, id)
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> saveOrUpdate(entity: Entity): Entity {
        mongoTemplate.save(entity)
        return entity;
    }
}