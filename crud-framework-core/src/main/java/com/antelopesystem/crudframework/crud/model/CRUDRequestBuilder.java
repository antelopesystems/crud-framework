package com.antelopesystem.crudframework.crud.model;

import com.antelopesystem.crudframework.crud.dataaccess.model.DataAccessorDTO;
import com.antelopesystem.crudframework.crud.hooks.base.CRUDHook;
import com.antelopesystem.crudframework.model.BaseCrudEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract CRUD request builder
 *
 * @param <PreHook> The {@link CRUDHook} that is run pre CRUD action
 * @param <OnHook> The {@link CRUDHook} that is run on(inside a transaction) CRUD action
 * @param <PostHook> The {@link CRUDHook} that is run post CRUD action
 * @param <EntityType> the {@link BaseCrudEntity} return type
 */
public abstract class CRUDRequestBuilder<PreHook, OnHook, PostHook, EntityType> {

	protected List<PreHook> preHooks = new ArrayList<>();

	protected List<OnHook> onHooks = new ArrayList<>();

	protected List<PostHook> postHooks = new ArrayList<>();

	protected DataAccessorDTO accessorDTO;

	/**
	 * Runs the CRUD action
	 *
	 * @return the entity type
	 */
	public abstract EntityType execute();

	/**
	 * Adds a preHook to the request
	 *
	 * @param preHook the pre hook
	 */
	public final CRUDRequestBuilder<PreHook, OnHook, PostHook, EntityType> withPreHook(PreHook preHook) {
		preHooks.add(preHook);
		return this;
	}

	/**
	 * Adds an onHook to the request
	 *
	 * @param onHook the on hook
	 */
	public final CRUDRequestBuilder<PreHook, OnHook, PostHook, EntityType> withOnHook(OnHook onHook) {
		onHooks.add(onHook);
		return this;
	}

	public CRUDRequestBuilder<PreHook, OnHook, PostHook, EntityType> enforceAccess(BaseCrudEntity requester) {
		accessorDTO = new DataAccessorDTO(requester.getClass(), requester.getId());
		return this;
	}

	public CRUDRequestBuilder<PreHook, OnHook, PostHook, EntityType> enforceAccess(Class<?> requesterType, Long requesterId) {
		accessorDTO = new DataAccessorDTO(requesterType, requesterId);
		return this;
	}

	/**
	 * Adds a postHook to the request
	 *
	 * @param postHook the post hook
	 */
	public final CRUDRequestBuilder<PreHook, OnHook, PostHook, EntityType> withPostHook(PostHook postHook) {
		postHooks.add(postHook);
		return this;
	}

}
