package com.antelopesystem.crudframework.crud.annotation

/**
 * Used to define a given entity as deleteable
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class Deleteable(
        /**
         * Denotes whether the entity is soft deleteable or hard deleteable
         */
        val softDelete: Boolean
)