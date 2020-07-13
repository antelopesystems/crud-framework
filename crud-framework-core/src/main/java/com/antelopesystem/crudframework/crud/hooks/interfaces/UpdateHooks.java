package com.antelopesystem.crudframework.crud.hooks.interfaces;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public interface UpdateHooks<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends CRUDHooks<ID, Entity> {

	default void preUpdate(@NotNull Entity entity) {
	}

	default void onUpdate(@NotNull Entity entity) {
	}

	default void postUpdate(@NotNull Entity entity) {
	}
}
