package com.antelopesystem.crudframework.crud.configuration

import com.antelopesystem.crudframework.crud.cache.CacheManagerAdapter
import com.antelopesystem.crudframework.crud.cache.adapter.inmemory.InMemoryCacheManagerAdapter
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(CrudEhCacheConfiguration::class)
class CrudCacheConfiguration {
    @Bean
    @ConditionalOnMissingBean(CacheManagerAdapter::class)
    fun inMemoryCacheManagerAdapter() : CacheManagerAdapter {
        log.info("Using In Memory Cache for caching operations")
        return InMemoryCacheManagerAdapter()
    }

    companion object {
        private val log = LoggerFactory.getLogger(CrudCacheConfiguration::class.java)
    }
}

