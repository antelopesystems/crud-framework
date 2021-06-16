package com.antelopesystem.crudframework.crud.dataaccess;

import com.antelopesystem.crudframework.utils.component.componentmap.annotation.ComponentMapKey;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public interface DataAccessManager<Accessor, AccessorId, Entity> {

	@ComponentMapKey
	String getKey();

	default void decorateViewOperation(@NotNull DynamicModelFilter filter, @NotNull AccessorId accessorId, Class<Accessor> accessorClazz) {
	}

	default void decorateUpdateOperation(@NotNull DynamicModelFilter filter, @NotNull AccessorId accessorId, @NotNull Class<Accessor> accessorClazz) {
	}

	default void decorateCreateOperation(@NotNull Entity entity, @NotNull AccessorId accessorId, @NotNull Class<Accessor> accessorClazz) {
	}

}
