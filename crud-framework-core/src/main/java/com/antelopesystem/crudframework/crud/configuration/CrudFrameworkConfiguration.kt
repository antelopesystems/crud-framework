package com.antelopesystem.crudframework.crud.configuration

import com.antelopesystem.crudframework.crud.handler.*
import com.antelopesystem.crudframework.exception.WrapExceptionAspect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
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
}