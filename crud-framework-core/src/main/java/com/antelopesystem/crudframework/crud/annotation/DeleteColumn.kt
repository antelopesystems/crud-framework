package com.antelopesystem.crudframework.crud.annotation

/**
 * Used to define the boolean column when using [Deleteable.softDelete]
 * ```
 * Example usage:
 *
 * ```
 * @Deleteable(softDelete = true)
 * class Entity {
 *  ...
 *  @DeleteColumn
 *  var isDeleted: Boolean = false
 * }
 * ```
 */
@Target(AnnotationTarget.FIELD)
annotation class DeleteColumn 