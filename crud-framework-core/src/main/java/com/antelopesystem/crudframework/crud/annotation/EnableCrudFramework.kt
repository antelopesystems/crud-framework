package com.antelopesystem.crudframework.crud.annotation

import com.antelopesystem.crudframework.crud.configuration.CacheConfiguration
import com.antelopesystem.crudframework.crud.configuration.CrudFrameworkConfiguration
import com.antelopesystem.crudframework.utils.component.componentmap.annotation.EnableComponentMap
import com.antelopesystem.crudframework.utils.component.startup.annotation.EnablePostStartup
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Import(CrudFrameworkConfiguration::class, CacheConfiguration::class)
@EnableComponentMap
@EnablePostStartup
annotation class EnableCrudFramework