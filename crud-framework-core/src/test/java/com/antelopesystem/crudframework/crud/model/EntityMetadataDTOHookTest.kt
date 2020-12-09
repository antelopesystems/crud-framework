package com.antelopesystem.crudframework.crud.model

import com.antelopesystem.crudframework.crud.annotation.WithHooks
import com.antelopesystem.crudframework.crud.hooks.interfaces.CRUDHooks
import com.antelopesystem.crudframework.crud.test.AbstractTestEntity
import com.antelopesystem.crudframework.model.BaseCrudEntity
import org.junit.Assert.assertArrayEquals
import org.junit.Test
import java.io.Serializable

class EntityMetadataDTOHookTest {
    private class GenericPersistentHooks<ID : Serializable> : CRUDHooks<ID, BaseCrudEntity<ID>>
    private class GenericPersistentHooks2<ID : Serializable> : CRUDHooks<ID, BaseCrudEntity<ID>>

    @WithHooks(hooks = [GenericPersistentHooks::class])
    private annotation class WithGenericPersistentHooks

    @WithHooks(hooks = [GenericPersistentHooks2::class])
    private annotation class WithGenericPersistentHooks2

    @Test
    fun `single nested withHooks annotation should register`() {
        @WithGenericPersistentHooks
        class TestEntity : AbstractTestEntity()

        val metadata = EntityMetadataDTO(TestEntity::class.java)

        assertArrayEquals(arrayOf(GenericPersistentHooks::class.java), metadata.hookTypesFromAnnotations.toTypedArray())
    }

    @Test
    fun `two nested withHooks annotation should register`() {
        @WithGenericPersistentHooks
        @WithGenericPersistentHooks2
        class TestEntity : AbstractTestEntity()

        val metadata = EntityMetadataDTO(TestEntity::class.java)

        assertArrayEquals(arrayOf(GenericPersistentHooks::class.java, GenericPersistentHooks2::class.java), metadata.hookTypesFromAnnotations.toTypedArray())
    }

    @Test
    fun `two nested withHooks annotation should register with one on parent`() {
        @WithGenericPersistentHooks2 abstract class AbstractAnnotatedTestEntity : AbstractTestEntity()
        @WithGenericPersistentHooks class TestEntity : AbstractAnnotatedTestEntity()

        val metadata = EntityMetadataDTO(TestEntity::class.java)

        assertArrayEquals(arrayOf(GenericPersistentHooks::class.java, GenericPersistentHooks2::class.java), metadata.hookTypesFromAnnotations.toTypedArray())
    }

    @Test
    fun `one withHooks annotation with multiple hooks should register`() {
        @WithHooks(hooks = [GenericPersistentHooks::class, GenericPersistentHooks2::class])
        class TestEntity : AbstractTestEntity()

        val metadata = EntityMetadataDTO(TestEntity::class.java)

        assertArrayEquals(arrayOf(GenericPersistentHooks::class.java, GenericPersistentHooks2::class.java), metadata.hookTypesFromAnnotations.toTypedArray())
    }

    @Test
    fun `nested witHooks annotation with multiple hooks should register`() {
        @WithHooks(hooks = [GenericPersistentHooks::class, GenericPersistentHooks2::class])
        abstract class AbstractAnnotatedTestEntity : AbstractTestEntity()

        class TestEntity : AbstractAnnotatedTestEntity()

        val metadata = EntityMetadataDTO(TestEntity::class.java)

        assertArrayEquals(arrayOf(GenericPersistentHooks::class.java, GenericPersistentHooks2::class.java), metadata.hookTypesFromAnnotations.toTypedArray())
    }
}