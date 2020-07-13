package com.antelopesystem.crudframework.crud.hooks.interfaces;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public interface ShowByHooks<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends CRUDHooks<ID, Entity> {

	default void preShowBy(@NotNull DynamicModelFilter filter) {
	}

	default void onShowBy(@Nullable Entity entity) {
	}

	default void postShowBy(@Nullable Entity entity) {
	}
}
