package com.antelopesystem.crudframework.crud.hooks.interfaces;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import com.antelopesystem.crudframework.ro.PagingDTO;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * This class contains all hook callbacks for the Index operation. It can be implemented
 * as many times as needed per entity, or even for abstract entities for use with {@link com.antelopesystem.crudframework.crud.annotation.WithHooks}. Implementations of this interface should be declared as Spring beans.
 * @param <Entity> the entity to listen to
 * @param <ID> the ID type of the entity
 */
public interface IndexHooks<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends CRUDHooks<ID, Entity> {

	/**
	 * Called prior to an Index operation.
	 * Runs after the filter has been validated for the given entity
	 * @param filter the filter used in the operation
	 */
	default void preIndex(@NotNull DynamicModelFilter filter) {
	}

	/**
	 * Called during an Index operation.
	 * If {@link com.antelopesystem.crudframework.crud.model.ReadCRUDRequestBuilder#fromCache} is used, this method will be skipped. It will be called  data access checks and run inside of a read-only transaction.
	 * @param filter the filter used in the operation
	 * @param result represents the paginated list of results from the given filter for the give entity
	 */
	default void onIndex(@NotNull DynamicModelFilter filter, @NotNull PagingDTO<Entity> result) {
	}

	/**
	 * Called after a Index operation
	 * @param filter the filter used in the operation
	 * @param result represents the paginated list of results from the given filter for the give entity
	 */
	default void postIndex(@NotNull DynamicModelFilter filter, @NotNull PagingDTO<Entity> result) {
	}
}
