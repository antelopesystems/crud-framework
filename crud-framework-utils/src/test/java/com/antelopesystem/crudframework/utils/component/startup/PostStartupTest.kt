package com.antelopesystem.crudframework.utils.component.startup

import com.antelopesystem.crudframework.utils.component.startup.configuration.PostStartupConfiguration
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import strikt.api.expectThat
import strikt.assertions.isTrue

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [PostStartupConfiguration::class, PostStartUpTestConfig::class])
class PostStartupTest {

    @Autowired
    private lateinit var postStartUpUser: PostStartUpUser

    @Test
    fun `context loads`() {
    }

    @Test
    fun `test PostStartUp happy flow`() {
        expectThat(postStartUpUser.initCalled)
            .isTrue()
    }
}