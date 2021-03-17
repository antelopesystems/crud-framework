package com.antelopesystem.crudframework.jpa.annotation

import org.hibernate.sql.JoinType

/**
 * Determine join type for relation
 */
@Target(AnnotationTarget.FIELD)
annotation class CrudJoinType(
        val joinType: JoinType
)
