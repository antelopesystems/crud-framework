package com.antelopesystem.crudframework.crud.hooks.interfaces;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;


/**
 * This class contains all hook callbacks for the Delete operation. It can be implemented
 * as many times as needed per entity, or even for abstract entities for use with {@link com.antelopesystem.crudframework.crud.annotation.WithHooks}. Implementations of this interface should be declared as Spring beans.
 * @param <Entity> The entity to listen to
 */
public interface DeleteHooks<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends CRUDHooks<ID, Entity> {

	/**
	 * Called prior to a Delete operation.
	 * Runs after the entity is verified to be deletable (Not immutable and deletable. -- whether hard or soft)
	 * The validity of the ID at this point is still unknown.
	 * @param id represents the ID of the entity to be deleted
	 */
	default void preDelete(ID id) {
	}

	/**
	 * Called during a Delete operation.
	 * This method will be called after validating the entity with the provided ID exists.
	 * This method will run inside of a read/write transaction.
	 * @param entity represents the entity being deleted
	 */
	default void onDelete(@NotNull Entity entity) {
	}

	/**
	 * Called after a DeleteFrom operation
	 * @param entity represents the entity that has been deleted
	 */
	default void postDelete(@NotNull Entity entity) {
	}
}
