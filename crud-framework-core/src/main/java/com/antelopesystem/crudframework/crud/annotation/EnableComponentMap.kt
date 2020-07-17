package com.antelopesystem.crudframework.crud.annotation

import com.antelopesystem.crudframework.crud.configuration.ComponentMapConfiguration
import com.antelopesystem.crudframework.crud.configuration.CrudFrameworkConfiguration
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Import(ComponentMapConfiguration::class)
annotation class EnableComponentMap