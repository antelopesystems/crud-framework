package com.antelopesystem.crudframework.crud.hooks.base;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import com.antelopesystem.crudframework.ro.PagingDTO;

import java.io.Serializable;

public interface FilterPagingDTOHook<ID extends Serializable, Entity extends BaseCrudEntity<ID>, Filter extends DynamicModelFilter> extends CRUDHook {

	void run(Filter filter, PagingDTO<Entity> result);
}
