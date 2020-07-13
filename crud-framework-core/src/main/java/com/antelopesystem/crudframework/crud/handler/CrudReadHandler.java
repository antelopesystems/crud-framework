package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.dataaccess.model.DataAccessorDTO;
import com.antelopesystem.crudframework.crud.enums.ShowByMode;
import com.antelopesystem.crudframework.crud.hooks.HooksDTO;
import com.antelopesystem.crudframework.crud.hooks.index.CRUDOnIndexHook;
import com.antelopesystem.crudframework.crud.hooks.index.CRUDPostIndexHook;
import com.antelopesystem.crudframework.crud.hooks.index.CRUDPreIndexHook;
import com.antelopesystem.crudframework.crud.hooks.show.CRUDOnShowHook;
import com.antelopesystem.crudframework.crud.hooks.show.CRUDPostShowHook;
import com.antelopesystem.crudframework.crud.hooks.show.CRUDPreShowHook;
import com.antelopesystem.crudframework.crud.hooks.show.by.CRUDOnShowByHook;
import com.antelopesystem.crudframework.crud.hooks.show.by.CRUDPostShowByHook;
import com.antelopesystem.crudframework.crud.hooks.show.by.CRUDPreShowByHook;
import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import com.antelopesystem.crudframework.ro.PagingDTO;

import java.io.Serializable;
import java.util.List;

interface CrudReadHandler {

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>, Filter extends DynamicModelFilter> PagingDTO<Entity> indexInternal(Filter filter, Class<Entity> clazz,
			HooksDTO<CRUDPreIndexHook<ID, Entity, Filter>, CRUDOnIndexHook<ID, Entity, Filter>, CRUDPostIndexHook<ID, Entity, Filter>> hooks,
			boolean fromCache, Boolean persistCopy, DataAccessorDTO accessorDTO, boolean count);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>, Filter extends DynamicModelFilter> PagingDTO<Entity> indexTransactional(Filter filter, Class<Entity> clazz,
			List<CRUDOnIndexHook<ID, Entity, Filter>> onHooks,
			Boolean persistCopy, DataAccessorDTO accessorDTO, boolean count);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>, Filter extends DynamicModelFilter> Entity showByInternal(Filter filter, Class<Entity> clazz,
			HooksDTO<CRUDPreShowByHook<ID, Entity, Filter>, CRUDOnShowByHook<ID, Entity>, CRUDPostShowByHook<ID, Entity>> hooks, boolean fromCache, Boolean persistCopy, ShowByMode mode, DataAccessorDTO accessorDTO);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>, Filter extends DynamicModelFilter> Entity showByTransactional(Filter filter, Class<Entity> clazz, List<CRUDOnShowByHook<ID, Entity>> onHooks,
			Boolean persistCopy, ShowByMode mode,
			DataAccessorDTO accessorDTO);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity showInternal(ID id, Class<Entity> clazz,
			HooksDTO<CRUDPreShowHook<ID, Entity>, CRUDOnShowHook<ID, Entity>, CRUDPostShowHook<ID, Entity>> hooks, boolean fromCache, Boolean persistCopy, DataAccessorDTO accessorDTO);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity showTransactional(ID id, Class<Entity> clazz, List<CRUDOnShowHook<ID, Entity>> onHooks, Boolean persistCopy, DataAccessorDTO accessorDTO);
}
