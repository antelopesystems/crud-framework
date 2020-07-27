package com.mycompany.crudframework.crud.ro

data class ManyFailedReason<FailedObject>(
        val `object`: FailedObject,
        val reason: String
)
