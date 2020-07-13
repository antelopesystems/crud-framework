package com.antelopesystem.crudframework.crud.hooks.index;

import com.antelopesystem.crudframework.crud.hooks.base.CRUDHook;
import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;

import java.io.Serializable;

@FunctionalInterface
public interface CRUDPreIndexHook<ID extends Serializable, Entity extends BaseCrudEntity<ID>, Filter extends DynamicModelFilter> extends CRUDHook {

	void run(Filter filter);
}
