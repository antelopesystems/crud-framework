package com.antelopesystem.crudframework.jpa.lazyinitializer

import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter
import com.antelopesystem.crudframework.ro.PagingDTO
import com.antelopesystem.crudframework.ro.PagingRO
import org.junit.Assert.*
import org.junit.Test

class LazyInitializerPersistentHooksTest {

    @Test
    fun `test index hook doesn't fail on null result`() {
        val subject = LazyInitializerPersistentHooks()
        subject.onIndex(DynamicModelFilter(), PagingDTO(PagingRO(0, 20, 100), null))
    }
}