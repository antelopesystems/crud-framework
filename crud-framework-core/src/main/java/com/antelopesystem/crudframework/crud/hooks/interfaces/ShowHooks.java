package com.antelopesystem.crudframework.crud.hooks.interfaces;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public interface ShowHooks<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends CRUDHooks<ID, Entity> {

	default void preShow(ID id) {
	}

	default void onShow(@Nullable Entity entity) {
	}

	default void postShow(@Nullable Entity entity) {
	}
}
