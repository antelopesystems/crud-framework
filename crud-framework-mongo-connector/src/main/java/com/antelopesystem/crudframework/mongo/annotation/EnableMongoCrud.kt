package com.antelopesystem.crudframework.mongo.annotation

import com.antelopesystem.crudframework.crud.annotation.EnableCrudFramework
import com.antelopesystem.crudframework.mongo.config.CrudMongoConnectorConfiguration
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Import(CrudMongoConnectorConfiguration::class)
@EnableCrudFramework
annotation class EnableMongoCrud