package com.antelopesystem.crudframework.crud.hooks.interfaces;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import org.springframework.core.GenericTypeResolver;
import java.io.Serializable;

public interface CRUDHooks<ID extends Serializable, Entity extends BaseCrudEntity<ID>> {

	default Class<Entity> getType() {
		Class[] generics = GenericTypeResolver.resolveTypeArguments(getClass(), CRUDHooks.class);
		return generics[1];
	}
}
