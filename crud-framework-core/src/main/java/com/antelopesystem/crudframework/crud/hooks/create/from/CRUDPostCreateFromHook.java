package com.antelopesystem.crudframework.crud.hooks.create.from;

import com.antelopesystem.crudframework.crud.hooks.base.EntityHook;
import com.antelopesystem.crudframework.model.BaseCrudEntity;

import java.io.Serializable;

@FunctionalInterface
public interface CRUDPostCreateFromHook<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends EntityHook<ID, Entity> {

}
