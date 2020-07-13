package com.antelopesystem.crudframework.jpa.annotation

import com.antelopesystem.crudframework.crud.annotation.CrudEntity
import com.antelopesystem.crudframework.jpa.dao.CrudDaoImpl

@CrudEntity(dao = CrudDaoImpl::class)
annotation class JpaCrudEntity