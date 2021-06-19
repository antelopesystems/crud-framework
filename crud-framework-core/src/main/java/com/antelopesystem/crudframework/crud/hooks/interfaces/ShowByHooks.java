package com.antelopesystem.crudframework.crud.hooks.interfaces;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * This class contains all hook callbacks for the ShowBy operation. It can be implemented
 * as many times as needed per entity, or even for abstract entities for use with {@link com.antelopesystem.crudframework.crud.annotation.WithHooks}. Implementations of this interface should be declared as Spring beans.
 * @param <Entity> the entity to listen to
 * @param <ID> the ID type of the entity
 */
public interface ShowByHooks<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends CRUDHooks<ID, Entity> {

	/**
	 * Called prior to an ShowBy operation.
	 * Runs after the filter has been validated for the given entity
	 * @param filter the filter used in the operation
	 */
	default void preShowBy(@NotNull DynamicModelFilter filter) {
	}

	/**
	 * Called during a ShowBy operation.
	 * If {@link com.antelopesystem.crudframework.crud.model.ReadCRUDRequestBuilder#fromCache} is used, this method will be skipped. It will be called after data access checks and run inside of a read-only transaction.
	 * @param entity represents the entity that was found, or null if not found
	 */
	default void onShowBy(@Nullable Entity entity) {
	}

	/**
	 * Called after a ShowBy operation.
	 * @param entity represents the entity that was found, or null if not found
	 */
	default void postShowBy(@Nullable Entity entity) {
	}
}
