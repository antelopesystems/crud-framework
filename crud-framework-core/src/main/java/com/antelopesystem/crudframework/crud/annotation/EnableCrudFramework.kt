package com.antelopesystem.crudframework.crud.annotation

import com.antelopesystem.crudframework.crud.configuration.CrudCacheConfiguration
import com.antelopesystem.crudframework.crud.configuration.CrudFrameworkConfiguration
import com.antelopesystem.crudframework.utils.component.componentmap.annotation.EnableComponentMap
import com.antelopesystem.crudframework.utils.component.startup.annotation.EnablePostStartup
import org.springframework.context.annotation.Import


/**
 * Used to enable the framework
 * Enables the component map and post startup features
 * Additionally triggers [CrudFrameworkConfiguration]
 */
@Target(AnnotationTarget.CLASS)
@Import(CrudFrameworkConfiguration::class, CrudCacheConfiguration::class)
@EnableComponentMap
@EnablePostStartup
annotation class EnableCrudFramework