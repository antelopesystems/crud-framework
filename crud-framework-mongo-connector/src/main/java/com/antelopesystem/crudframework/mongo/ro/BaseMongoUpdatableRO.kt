package com.antelopesystem.crudframework.mongo.ro

abstract class BaseMongoUpdatableRO: BaseMongoRO() {
    private var lastUpdateTime: Long = 0
}