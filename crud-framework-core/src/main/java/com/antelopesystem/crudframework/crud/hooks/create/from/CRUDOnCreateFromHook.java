package com.antelopesystem.crudframework.crud.hooks.create.from;

import com.antelopesystem.crudframework.crud.hooks.base.ObjectEntityHook;
import com.antelopesystem.crudframework.model.BaseCrudEntity;

import java.io.Serializable;

@FunctionalInterface
public interface CRUDOnCreateFromHook<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends ObjectEntityHook<ID, Entity> {

}
