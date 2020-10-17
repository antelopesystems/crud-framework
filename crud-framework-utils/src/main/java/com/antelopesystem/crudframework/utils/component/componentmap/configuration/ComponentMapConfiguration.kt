package com.antelopesystem.crudframework.utils.component.componentmap.configuration

import com.antelopesystem.crudframework.utils.component.componentmap.ComponentMapPostProcessor
import com.antelopesystem.crudframework.utils.component.componentmap.LegacyComponentMapHandler
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

@Configuration
class ComponentMapConfiguration {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ConditionalOnProperty("crudframework.component-map.legacy-mode", havingValue = "false", matchIfMissing = true)
    fun componentMapPostProcessor(): ComponentMapPostProcessor {
        return ComponentMapPostProcessor()
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ConditionalOnProperty("crudframework.component-map.legacy-mode", havingValue = "true")
    fun legacyComponentMapHandler(): LegacyComponentMapHandler {
        return LegacyComponentMapHandler()
    }
}