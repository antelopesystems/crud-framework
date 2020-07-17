package com.antelopesystem.crudframework.crud.annotation

import com.antelopesystem.crudframework.crud.configuration.PostStartupConfiguration
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Import(PostStartupConfiguration::class)
annotation class EnablePostStartup