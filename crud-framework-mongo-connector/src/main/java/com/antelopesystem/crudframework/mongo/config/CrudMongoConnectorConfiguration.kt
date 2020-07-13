package com.antelopesystem.crudframework.mongo.config

import com.antelopesystem.crudframework.crud.handler.CrudDao
import com.antelopesystem.crudframework.mongo.dao.MongoCrudDaoImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CrudMongoConnectorConfiguration {
    @Bean
    fun mongoCrudDao(): CrudDao = MongoCrudDaoImpl()
}