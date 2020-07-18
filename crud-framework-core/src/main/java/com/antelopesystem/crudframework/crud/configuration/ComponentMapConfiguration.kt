package com.antelopesystem.crudframework.crud.configuration

import com.antelopesystem.crudframework.components.componentmap.ComponentMapPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

@Configuration
class ComponentMapConfiguration {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun componentMapPostProcessor(): ComponentMapPostProcessor {
        return ComponentMapPostProcessor()
    }
}