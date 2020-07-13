package com.antelopesystem.crudframework.crud.hooks.interfaces;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public interface CreateHooks<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends CRUDHooks<ID, Entity> {

	default void preCreate(@NotNull Entity entity) {
	}

	default void onCreate(@NotNull Entity entity) {
	}

	default void postCreate(@NotNull Entity entity) {
	}
}
