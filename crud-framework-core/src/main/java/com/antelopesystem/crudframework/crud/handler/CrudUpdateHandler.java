package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.dataaccess.model.DataAccessorDTO;
import com.antelopesystem.crudframework.crud.hooks.HooksDTO;
import com.antelopesystem.crudframework.crud.hooks.update.CRUDOnUpdateHook;
import com.antelopesystem.crudframework.crud.hooks.update.CRUDPostUpdateHook;
import com.antelopesystem.crudframework.crud.hooks.update.CRUDPreUpdateHook;
import com.antelopesystem.crudframework.crud.hooks.update.from.CRUDOnUpdateFromHook;
import com.antelopesystem.crudframework.crud.hooks.update.from.CRUDPostUpdateFromHook;
import com.antelopesystem.crudframework.crud.hooks.update.from.CRUDPreUpdateFromHook;
import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;

import java.io.Serializable;
import java.util.List;

interface CrudUpdateHandler {

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity updateInternal(Entity entity, HooksDTO<CRUDPreUpdateHook<ID, Entity>, CRUDOnUpdateHook<ID, Entity>, CRUDPostUpdateHook<ID, Entity>> hooks,
			DataAccessorDTO accessorDTO);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity updateTransactional(Entity entity, List<CRUDOnUpdateHook<ID, Entity>> onHooks, DataAccessorDTO accessorDTO);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity updateFromInternal(ID id, Object object, Class<Entity> clazz,
			HooksDTO<CRUDPreUpdateFromHook<ID, Entity>, CRUDOnUpdateFromHook<ID, Entity>, CRUDPostUpdateFromHook<ID, Entity>> hooks,
			DataAccessorDTO accessorDTO);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity updateFromTransactional(ID id, Object object, Class<Entity> clazz, List<CRUDOnUpdateFromHook<ID, Entity>> onHooks, DataAccessorDTO accessorDTO);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> List<Entity> updateManyTransactional(List<Entity> entities,
			HooksDTO<CRUDPreUpdateHook<ID, Entity>, CRUDOnUpdateHook<ID, Entity>, CRUDPostUpdateHook<ID, Entity>> hooks, Boolean persistCopy, DataAccessorDTO accessorDTO);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> List<Entity> updateByFilterTransactional(DynamicModelFilter filter, Class<Entity> entityClazz,
			HooksDTO<CRUDPreUpdateHook<ID, Entity>, CRUDOnUpdateHook<ID, Entity>, CRUDPostUpdateHook<ID, Entity>> hooks, Boolean persistCopy, DataAccessorDTO accessorDTO);
}
