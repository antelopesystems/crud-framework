package com.antelopesystem.crudframework.test

import com.antelopesystem.crudframework.crud.handler.*
import com.antelopesystem.crudframework.model.BaseCrudEntity
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter
import com.antelopesystem.crudframework.ro.PagingDTO
import com.nhaarman.mockitokotlin2.*
import org.mockito.stubbing.OngoingStubbing
import org.mockito.verification.VerificationMode
import java.io.Serializable

class TestCrudHandler(
    val mockCrudReadHandler: CrudReadHandler = mock(),
    val mockCrudUpdateHandler: CrudUpdateHandler = mock(),
    val mockCrudDeleteHandler: CrudDeleteHandler = mock(),
    val mockCrudCreateHandler: CrudCreateHandler = mock(),
    val testCrudHelper: CrudHelper = TestCrudHelper()
) : CrudHandlerImpl() {

    init {
        this.setCrudReadHandler(mockCrudReadHandler)
        this.setCrudUpdateHandler(mockCrudUpdateHandler)
        this.setCrudDeleteHandler(mockCrudDeleteHandler)
        this.setCrudCreateHandler(mockCrudCreateHandler)
        this.setCrudHelper(testCrudHelper)
    }

    /**
     * Operation Hooks
     */

    /**
     * Returns [OngoingStubbing] for the show operation
     * @param id ID argument
     */
    inline fun <reified EntityType : BaseCrudEntity<ID>, reified ID: Serializable> onShow(id: ID? = null): OngoingStubbing<EntityType?> {
        return whenever(mockCrudReadHandler.showInternal(eqOrAny(id), eq(EntityType::class.java), anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull()))
    }

    /**
     * Returns [OngoingStubbing] for the show by operation
     * @param filter Filter argument
     */
    inline fun <reified EntityType : BaseCrudEntity<ID>, reified ID: Serializable> onShowBy(filter: DynamicModelFilter? = null): OngoingStubbing<EntityType?> {
        return whenever(mockCrudReadHandler.showByInternal(eqOrAny(filter), eq(EntityType::class.java), anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull()))
    }

    /**
     * Returns [OngoingStubbing] for the index operation
     * @param filter Filter argument
     */
    inline fun <reified EntityType : BaseCrudEntity<ID>, reified ID: Serializable> onIndex(filter: DynamicModelFilter? = null): OngoingStubbing<PagingDTO<EntityType>> {
        return whenever(mockCrudReadHandler.indexInternal(eqOrAny(filter), eq(EntityType::class.java), anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull()))
    }

    /**
     * Returns [OngoingStubbing] for the create operation
     * @param entity Entity argument
     */
    inline fun <reified EntityType : BaseCrudEntity<ID>, reified ID: Serializable> onCreate(entity: EntityType? = null): OngoingStubbing<EntityType> {
        verify(mockCrudCreateHandler)
        return whenever(mockCrudCreateHandler.createInternal(eqOrAny(entity), anyOrNull(), anyOrNull()))
    }

    /**
     * Returns [OngoingStubbing] for the create from operation
     * @param fromObject From Object argument
     */
    inline fun <reified EntityType : BaseCrudEntity<ID>, reified ID: Serializable> onCreateFrom(fromObject: Any? = null): OngoingStubbing<EntityType> {
        return whenever(mockCrudCreateHandler.createFromInternal(eqOrAny(fromObject), eq(EntityType::class.java), anyOrNull(), anyOrNull()))
    }

    /**
     * Returns [OngoingStubbing] for the update operation
     * @param entity Entity argument
     */
    inline fun <reified EntityType : BaseCrudEntity<ID>, reified ID: Serializable> onUpdate(entity: EntityType? = null): OngoingStubbing<EntityType> {
        return whenever(mockCrudUpdateHandler.updateInternal(eqOrAny(entity), anyOrNull(), anyOrNull()))
    }

    /**
     * Returns [OngoingStubbing] for the update from operation
     * @param id ID argument
     * @param fromObject From Object argument
     */
    inline fun <reified EntityType : BaseCrudEntity<ID>, reified ID: Serializable> onUpdateFrom(id: ID? = anyOrNull(), fromObject: Any? = null): OngoingStubbing<EntityType> {
        return whenever(mockCrudUpdateHandler.updateFromInternal(eqOrAny(id), eqOrAny(fromObject), eq(EntityType::class.java), anyOrNull(), anyOrNull()))
    }

    /**
     * Returns [OngoingStubbing] for the delete operation
     * @param id ID argument
     */
    inline fun <reified EntityType : BaseCrudEntity<ID>, reified ID: Serializable> onDelete(id: ID? = null): OngoingStubbing<Unit> {
        return whenever(mockCrudDeleteHandler.deleteInternal(eqOrAny(id), eq(EntityType::class.java), anyOrNull(), anyOrNull()))
    }

    /**
     * Verification methods
     */

    /**
     * Runs verify on the show operation
     * @param id ID argument
     * @param verificationMode The verification mode to use
     */
    inline fun <reified EntityType : BaseCrudEntity<ID>, reified ID: Serializable> verifyShow(id: ID? = null, verificationMode: VerificationMode = times(1)): ID {
        val idCaptor = argumentCaptor<ID>()
        verify(mockCrudReadHandler, verificationMode).showInternal(idCaptor.capture(), eq(EntityType::class.java), anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull())
        return idCaptor.lastValue
    }

    /**
     * Runs verify on the show by operation
     * @param verificationMode The verification mode to use
     * @return The captured filter
     */
    inline fun <reified EntityType : BaseCrudEntity<ID>, reified ID: Serializable> verifyShowBy(verificationMode: VerificationMode = times(1)): DynamicModelFilter? {
        val filterCaptor = argumentCaptor<DynamicModelFilter>()
        verify(mockCrudReadHandler, verificationMode).showByInternal(filterCaptor.capture(), eq(EntityType::class.java), anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull())
        return filterCaptor.allValues.lastOrNull()
    }

    /**
     * Runs verify for the index operation
     * @return The captured filter
     */
    inline fun <reified EntityType : BaseCrudEntity<ID>, reified ID: Serializable> verifyIndex(verificationMode: VerificationMode = times(1)): DynamicModelFilter? {
        val filterCaptor = argumentCaptor<DynamicModelFilter>()
        verify(mockCrudReadHandler, verificationMode).indexInternal(filterCaptor.capture(), eq(EntityType::class.java), anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull())
        return filterCaptor.allValues.lastOrNull()
    }

    /**
     * Runs verify for the create operation
     * @return The captured entity
     */
    inline fun <reified EntityType : BaseCrudEntity<ID>, reified ID: Serializable> verifyCreate(verificationMode: VerificationMode = times(1)): EntityType {
        val entityCaptor = argumentCaptor<EntityType>()
        verify(mockCrudCreateHandler, verificationMode).createInternal(entityCaptor.capture(), anyOrNull(), anyOrNull())
        return entityCaptor.lastValue
    }

    /**
     * Runs verify for the create from operation
     * @param fromObject From Object argument
     * @return The captured fromObject
     */
    inline fun <reified EntityType : BaseCrudEntity<ID>, reified ID: Serializable> verifyCreateFrom(verificationMode: VerificationMode = times(1)): Any {
        val fromObjectCaptor = argumentCaptor<Any>()
        verify(mockCrudCreateHandler, verificationMode).createFromInternal(fromObjectCaptor.capture(), eq(EntityType::class.java), anyOrNull(), anyOrNull())
        return fromObjectCaptor.lastValue
    }

    /**
     * Runs verify for the update operation
     * @return The captured entity
     */
    inline fun <reified EntityType : BaseCrudEntity<ID>, reified ID: Serializable> verifyUpdate(verificationMode: VerificationMode = times(1)): EntityType {
        val entityCaptor = argumentCaptor<EntityType>()
        verify(mockCrudUpdateHandler, verificationMode).updateInternal(entityCaptor.capture(), anyOrNull(), anyOrNull())
        return entityCaptor.lastValue
    }

    /**
     * Runs verify for the update from operation
     * @param id ID argument
     * @param fromObject From Object argument
     * @return The captured id and fromObject
     */
    inline fun <reified EntityType : BaseCrudEntity<ID>, reified ID: Serializable> verifyUpdateFrom(verificationMode: VerificationMode = times(1)): Pair<ID, Any> {
        val idCaptor = argumentCaptor<ID>()
        val fromObjectCaptor = argumentCaptor<Any>()
        verify(mockCrudUpdateHandler, verificationMode).updateFromInternal(idCaptor.capture(), fromObjectCaptor.capture(), eq(EntityType::class.java), anyOrNull(), anyOrNull())
        return idCaptor.lastValue to fromObjectCaptor.lastValue
    }

    /**
     * Runs verify for the delete operation
     * @param id ID argument
     * @return The captured id
     */
    inline fun <reified EntityType : BaseCrudEntity<ID>, reified ID: Serializable> verifyDelete(verificationMode: VerificationMode = times(1)): ID {
        val idCaptor = argumentCaptor<ID>()
        verify(mockCrudDeleteHandler, verificationMode).deleteInternal(idCaptor.capture(), eq(EntityType::class.java), anyOrNull(), anyOrNull())
        return idCaptor.lastValue
    }

    inline fun <reified T : Any> eqOrAny(value: T?): T {
        return if(value == null) {
            anyOrNull()
        } else {
            eq(value)
        }
    }
}