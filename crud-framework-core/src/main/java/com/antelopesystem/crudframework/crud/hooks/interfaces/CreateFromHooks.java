package com.antelopesystem.crudframework.crud.hooks.interfaces;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public interface CreateFromHooks<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends CRUDHooks<ID, Entity> {

	default void preCreateFrom(@NotNull Object ro) {
	}

	default void onCreateFrom(@NotNull Entity entity, @NotNull Object ro) {
	}

	default void postCreateFrom(@NotNull Entity entity) {
	}
}
