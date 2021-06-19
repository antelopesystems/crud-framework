package com.antelopesystem.crudframework.crud.test

import com.antelopesystem.crudframework.crud.annotation.CrudEntity
import com.antelopesystem.crudframework.crud.test.TestCrudDao
import com.antelopesystem.crudframework.model.BaseCrudEntity
import java.util.*

@CrudEntity(TestCrudDao::class)
abstract class AbstractTestEntity : BaseCrudEntity<Long>() {
    override var id: Long
        get() = 0L
        set(value) {}

    override fun exists(): Boolean {
        return false
    }
}