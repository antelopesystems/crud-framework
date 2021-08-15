package com.antelopesystem.crudframework.crud.model

import com.antelopesystem.crudframework.crud.annotation.WithHooks
import com.antelopesystem.crudframework.crud.hooks.interfaces.CRUDHooks
import com.antelopesystem.crudframework.jpa.model.AbstractJpaCrudEntity
import com.antelopesystem.crudframework.model.BaseCrudEntity
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Table

class GenericPersistentHooks<ID : Serializable> : CRUDHooks<ID, BaseCrudEntity<ID>>

@WithHooks(hooks = [GenericPersistentHooks::class])
annotation class NestedWithHooks

@Entity
@Table(name = "test_kotlin_entity")
@NestedWithHooks
class TestKotlinEntity : AbstractJpaCrudEntity()