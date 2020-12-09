package com.antelopesystem.crudframework.crud.test

import com.antelopesystem.crudframework.crud.handler.CrudDao
import com.antelopesystem.crudframework.model.BaseCrudEntity
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter
import java.io.Serializable

class TestCrudDao : CrudDao {
    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?, E : DynamicModelFilter?> index(filter: E, clazz: Class<Entity>?): MutableList<Entity> {
        TODO("Not yet implemented")
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?, E : DynamicModelFilter?> indexCount(filter: E, clazz: Class<Entity>?): Long {
        TODO("Not yet implemented")
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> softDeleteById(id: ID, deleteColumn: String?, clazz: Class<Entity>?) {
        TODO("Not yet implemented")
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> hardDeleteById(id: ID, clazz: Class<Entity>?) {
        TODO("Not yet implemented")
    }

    override fun <ID : Serializable?, Entity : BaseCrudEntity<ID>?> saveOrUpdate(entity: Entity): Entity {
        TODO("Not yet implemented")
    }
}