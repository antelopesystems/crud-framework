package com.antelopesystem.crudframework.utils.component.componentmap.annotation

import com.antelopesystem.crudframework.utils.component.componentmap.configuration.ComponentMapConfiguration
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Import(ComponentMapConfiguration::class)
annotation class EnableComponentMap