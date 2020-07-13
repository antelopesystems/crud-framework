package com.antelopesystem.crudframework.crud.hooks.interfaces;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;


public interface DeleteHooks<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends CRUDHooks<ID, Entity> {

	default void preDelete(ID id) {
	}

	default void onDelete(@NotNull Entity entity) {
	}

	default void postDelete(@NotNull Entity entity) {
	}
}
