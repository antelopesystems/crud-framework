package com.antelopesystem.crudframework.crud.hooks.base;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import com.antelopesystem.crudframework.ro.PagingDTO;

import java.io.Serializable;

public interface FilterPagingDTOHook<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends CRUDHook {

	void run(DynamicModelFilter filter, PagingDTO<Entity> result);
}
