package com.antelopesystem.crudframework.crud.configuration

import com.antelopesystem.crudframework.crud.configuration.properties.CrudFrameworkProperties
import com.antelopesystem.crudframework.crud.handler.*
import com.antelopesystem.crudframework.exception.WrapExceptionAspect
import com.antelopesystem.crudframework.transformer.EntityListToRoListTransformer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(CrudFrameworkProperties::class)
class CrudFrameworkConfiguration {

    @Bean
    fun crudHandler(): CrudHandler = CrudHandlerImpl()

    @Bean
    fun crudHelper(): CrudHelper = CrudHelperImpl()

    @Bean
    fun crudCreateHandler(): CrudCreateHandler = CrudCreateHandlerImpl()

    @Bean
    fun crudDeleteHandler(): CrudDeleteHandler = CrudDeleteHandlerImpl()

    @Bean
    fun crudUpdateHandler(): CrudUpdateHandler = CrudUpdateHandlerImpl()

    @Bean
    fun crudReadHandler(): CrudReadHandler = CrudReadHandlerImpl()

    @Bean
    fun wrapExceptionAspect(): WrapExceptionAspect = WrapExceptionAspect()

    @Bean
    fun entityListToRoListTransformer() = EntityListToRoListTransformer(crudHandler())
}