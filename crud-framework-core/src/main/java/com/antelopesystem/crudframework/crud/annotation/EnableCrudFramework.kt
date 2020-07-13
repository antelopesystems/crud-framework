package com.antelopesystem.crudframework.crud.annotation

import com.antelopesystem.crudframework.crud.configuration.CrudFrameworkConfiguration
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Import(CrudFrameworkConfiguration::class)
annotation class EnableCrudFramework