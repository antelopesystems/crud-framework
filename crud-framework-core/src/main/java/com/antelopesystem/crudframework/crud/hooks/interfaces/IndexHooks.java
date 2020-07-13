package com.antelopesystem.crudframework.crud.hooks.interfaces;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import com.antelopesystem.crudframework.ro.PagingDTO;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public interface IndexHooks<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends CRUDHooks<ID, Entity> {

	default void preIndex(@NotNull DynamicModelFilter filter) {
	}

	default void onIndex(@NotNull DynamicModelFilter filter, @NotNull PagingDTO<Entity> result) {
	}

	default void postIndex(@NotNull DynamicModelFilter filter, @NotNull PagingDTO<Entity> result) {
	}
}
