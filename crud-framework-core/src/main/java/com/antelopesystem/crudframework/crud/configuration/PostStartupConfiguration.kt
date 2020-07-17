package com.antelopesystem.crudframework.crud.configuration

import com.antelopesystem.crudframework.components.startup.PostStartupHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PostStartupConfiguration {

    @Bean
    fun postStartupHandler(): PostStartupHandler = PostStartupHandler()
}