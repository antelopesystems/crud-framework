package com.antelopesystem.crudframework.crud.hooks.update.from;

import com.antelopesystem.crudframework.crud.hooks.base.CRUDHook;
import com.antelopesystem.crudframework.model.BaseCrudEntity;

import java.io.Serializable;

@FunctionalInterface
public interface CRUDPreUpdateFromHook<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends CRUDHook {

	void run(ID id, Object object);
}
