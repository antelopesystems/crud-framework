package com.antelopesystem.crudframework.utils.component.startup

import com.antelopesystem.crudframework.utils.component.componentmap.configuration.ComponentMapConfiguration
import com.antelopesystem.crudframework.utils.component.startup.configuration.PostStartupConfiguration
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.junit.Assert.*

@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = [PostStartupConfiguration::class, PostStartUpTestConfig::class])
class PostStartupTest {

    @Autowired
    private lateinit var postStartUpUser: PostStartUpUser

    @Test
    fun `context loads`() {
    }

    @Test
    fun `test PostStartUp happy flow`() {
        assertEquals(true, postStartUpUser.initCalled)
    }
}