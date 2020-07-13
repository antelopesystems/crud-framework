package com.antelopesystem.crudframework.crud.hooks.base;

import com.antelopesystem.crudframework.model.BaseCrudEntity;

import java.io.Serializable;

public interface EntityHook<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends CRUDHook {

	void run(Entity entity);

}
