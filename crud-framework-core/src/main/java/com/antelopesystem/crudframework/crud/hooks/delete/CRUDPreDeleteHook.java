package com.antelopesystem.crudframework.crud.hooks.delete;

import com.antelopesystem.crudframework.crud.hooks.base.IDHook;
import com.antelopesystem.crudframework.model.BaseCrudEntity;

import java.io.Serializable;

@FunctionalInterface
public interface CRUDPreDeleteHook<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends IDHook<ID> {

}
