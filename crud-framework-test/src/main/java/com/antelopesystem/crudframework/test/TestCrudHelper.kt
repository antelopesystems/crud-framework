package com.antelopesystem.crudframework.test

import com.antelopesystem.crudframework.crud.handler.AbstractCrudHelper

/**
 * Bare minimum test implementation for CrudHelper
 */
class TestCrudHelper: AbstractCrudHelper() {
    override fun <From, To> fill(fromObject: From, toClazz: Class<To>): To {
        return toClazz.newInstance()
    }

    override fun <From, To> fill(fromObject: From, toObject: To) {
    }

    override fun <From, To> fillMany(fromObjects: MutableList<From>, toClazz: Class<To>): List<To> {
        return fromObjects.map { fill(it, toClazz) }
    }
}