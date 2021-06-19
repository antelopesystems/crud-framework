package com.antelopesystem.crudframework.jpa.ro

import java.io.Serializable
import java.util.*

abstract class AbstractJpaUpdatableCrudRO : Serializable {
    val id: Long? = null
    val creationTime: Date? = null
    val lastUpdateTime: Date? = null
}