package com.antelopesystem.crudframework.crud.hooks.interfaces;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public interface UpdateFromHooks<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends CRUDHooks<ID, Entity> {

	default void preUpdateFrom(ID id, @NotNull Object ro) {
	}

	default void onUpdateFrom(@NotNull Entity entity, @NotNull Object ro) {
	}

	default void postUpdateFrom(@NotNull Entity entity) {
	}
}
