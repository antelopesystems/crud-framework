package com.antelopesystem.crudframework.crud.hooks.show;

import com.antelopesystem.crudframework.crud.hooks.base.IDHook;
import com.antelopesystem.crudframework.model.BaseCrudEntity;

import java.io.Serializable;

@FunctionalInterface
public interface CRUDPreShowHook<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends IDHook<ID> {

}
