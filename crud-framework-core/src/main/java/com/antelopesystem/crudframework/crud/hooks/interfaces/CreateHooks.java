package com.antelopesystem.crudframework.crud.hooks.interfaces;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * This class contains all hook callbacks for the Create operation. It can be implemented
 * as many times as needed per entity, or even for abstract entities for use with {@link com.antelopesystem.crudframework.crud.annotation.WithHooks}. Implementations of this interface should be declared as Spring beans.
 * @param <Entity> the entity to listen to
 * @param <ID> the ID type of the entity
 */
public interface CreateHooks<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends CRUDHooks<ID, Entity> {

	/**
	 * Called prior to a Create operation
	 * @param entity represents the entity being created
	 */
	default void preCreate(@NotNull Entity entity) {
	}

	/**
	 * Called during a Create operation.
	 * This method will be called after data access checks, and before validation.
	 * This method will run inside of a read/write transaction.
	 * @param entity represents the entity being created
	 */
	default void onCreate(@NotNull Entity entity) {
	}

	/**
	 * Called after a Create operation
	 * @param entity represents the entity that has been created
	 */
	default void postCreate(@NotNull Entity entity) {
	}
}
