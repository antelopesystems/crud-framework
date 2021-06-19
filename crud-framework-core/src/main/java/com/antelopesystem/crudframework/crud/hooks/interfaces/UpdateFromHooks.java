package com.antelopesystem.crudframework.crud.hooks.interfaces;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * This class contains all hook callbacks for the UpdateFrom operation. It can be implemented
 * as many times as needed per entity, or even for abstract entities for use with {@link com.antelopesystem.crudframework.crud.annotation.WithHooks}. Implementations of this interface should be declared as Spring beans.
 * @param <Entity> the entity to listen to
 * @param <ID> the ID type of the entity
 */
public interface UpdateFromHooks<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends CRUDHooks<ID, Entity> {

	/**
	 * Called prior to an UpdateFrom operation.
	 * Runs after the entity is verified to be updatable. (Not immutable)
	 * @param id represents the ID of the entity to be updated
	 * @param ro represents the object that will be transformed to the entity
	 */
	default void preUpdateFrom(ID id, @NotNull Object ro) {
	}

	/**
	 * Called during an UpdateFrom operation.
	 * This method will be called after field mapping / object decoration between the
	 * RO and the entity and data access checks, and before validation.
	 * This method will run inside of a read/write transaction.
	 * @param entity represents the entity being updated
	 * @param ro represents the object that will be transformed to the entity
	 */
	default void onUpdateFrom(@NotNull Entity entity, @NotNull Object ro) {
	}

	/**
	 * Called after a UpdateFrom operation
	 * @param entity represents the entity that has been created
	 */
	default void postUpdateFrom(@NotNull Entity entity) {
	}
}
