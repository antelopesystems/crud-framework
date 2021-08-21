package com.antelopesystem.crudframework.crud.hooks.index;

import com.antelopesystem.crudframework.crud.hooks.base.FilterPagingDTOHook;
import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;

import java.io.Serializable;

@FunctionalInterface
public interface CRUDOnIndexHook<ID extends Serializable, Entity extends BaseCrudEntity<ID>> extends FilterPagingDTOHook<ID, Entity> {

}
