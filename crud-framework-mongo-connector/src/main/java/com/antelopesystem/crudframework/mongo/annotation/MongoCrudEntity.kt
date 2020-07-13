package com.antelopesystem.crudframework.mongo.annotation

import com.antelopesystem.crudframework.crud.annotation.CrudEntity
import com.antelopesystem.crudframework.mongo.dao.MongoCrudDaoImpl

@CrudEntity(dao = MongoCrudDaoImpl::class)
annotation class MongoCrudEntity