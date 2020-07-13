package com.antelopesystem.crudframework.jpa.config

import com.antelopesystem.crudframework.crud.handler.CrudDao
import com.antelopesystem.crudframework.jpa.dao.CrudDaoImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CrudHibernate5ConnectorConfiguration {
    @Bean
    fun jpaCrudDao(): CrudDao = CrudDaoImpl()
}