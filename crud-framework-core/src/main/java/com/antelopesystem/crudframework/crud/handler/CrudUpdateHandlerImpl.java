package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.dataaccess.model.DataAccessorDTO;
import com.antelopesystem.crudframework.crud.exception.CRUDException;
import com.antelopesystem.crudframework.crud.hooks.HooksDTO;
import com.antelopesystem.crudframework.crud.hooks.interfaces.CRUDHooks;
import com.antelopesystem.crudframework.crud.hooks.interfaces.UpdateFromHooks;
import com.antelopesystem.crudframework.crud.hooks.interfaces.UpdateHooks;
import com.antelopesystem.crudframework.crud.hooks.update.CRUDOnUpdateHook;
import com.antelopesystem.crudframework.crud.hooks.update.CRUDPostUpdateHook;
import com.antelopesystem.crudframework.crud.hooks.update.CRUDPreUpdateHook;
import com.antelopesystem.crudframework.crud.hooks.update.from.CRUDOnUpdateFromHook;
import com.antelopesystem.crudframework.crud.hooks.update.from.CRUDPostUpdateFromHook;
import com.antelopesystem.crudframework.crud.hooks.update.from.CRUDPreUpdateFromHook;
import com.antelopesystem.crudframework.exception.tree.core.ErrorCode;
import com.antelopesystem.crudframework.exception.tree.core.ExceptionOverride;
import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CrudUpdateHandlerImpl extends CrudHookHandlerBase implements CrudUpdateHandler {

	@Autowired
	private CrudHelper crudHelper;

	@Resource(name = "crudUpdateHandler")
	private CrudUpdateHandler crudUpdateHandlerProxy;

	@Override
	protected List<Class<? extends CRUDHooks>> getHookTargetClasses() {
		return Arrays.asList(UpdateFromHooks.class, UpdateHooks.class);
	}

	@Override
	@Transactional(readOnly = false)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> List<Entity> updateManyTransactional(List<Entity> entities,
			HooksDTO<CRUDPreUpdateHook<ID, Entity>, CRUDOnUpdateHook<ID, Entity>, CRUDPostUpdateHook<ID, Entity>> hooks, Boolean persistCopy, DataAccessorDTO accessorDTO) {
		List<Entity> finalEntityList = new ArrayList<>();
		for(Entity entity : entities) {
			if(persistCopy != null && persistCopy) {
				entity.saveOrGetCopy();
			}

			finalEntityList.add(crudUpdateHandlerProxy.updateInternal(entity, hooks, accessorDTO));
		}

		return finalEntityList;
	}

	@Override
	@Transactional(readOnly = false)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> List<Entity> updateByFilterTransactional(DynamicModelFilter filter, Class<Entity> entityClazz,
			HooksDTO<CRUDPreUpdateHook<ID, Entity>, CRUDOnUpdateHook<ID, Entity>, CRUDPostUpdateHook<ID, Entity>> hooks, Boolean persistCopy, DataAccessorDTO accessorDTO) {
		List<Entity> entities = crudHelper.getEntities(filter, entityClazz, accessorDTO, false, true);
		return crudUpdateHandlerProxy.updateManyTransactional(entities, hooks, persistCopy, accessorDTO);
	}

	@Override
	@ExceptionOverride(value = CRUDException.class, errorCode = ErrorCode.UpdateError)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity updateInternal(Entity entity, HooksDTO<CRUDPreUpdateHook<ID, Entity>, CRUDOnUpdateHook<ID, Entity>, CRUDPostUpdateHook<ID, Entity>> hooks,
			DataAccessorDTO accessorDTO) {
		Objects.requireNonNull(entity, "Entity cannot be null");
		crudHelper.checkEntityImmutability(entity.getClass());

		List<UpdateHooks> updateHooksList = getHooks(UpdateHooks.class, entity.getClass());

		if(updateHooksList != null && !updateHooksList.isEmpty()) {
			for(UpdateHooks<ID, Entity> updateHooks : updateHooksList) {
				hooks.getPreHooks().add(0, updateHooks::preUpdate);
				hooks.getOnHooks().add(0, updateHooks::onUpdate);
				hooks.getPostHooks().add(0, updateHooks::postUpdate);
			}
		}

		for(CRUDPreUpdateHook<ID, Entity> preHook : hooks.getPreHooks()) {
			preHook.run(entity);
		}

		entity = crudUpdateHandlerProxy.updateTransactional(entity, hooks.getOnHooks(), accessorDTO);

		crudHelper.evictEntityFromCache(entity);

		for(CRUDPostUpdateHook<ID, Entity> postHook : hooks.getPostHooks()) {
			postHook.run(entity);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = false)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity updateTransactional(Entity entity, List<CRUDOnUpdateHook<ID, Entity>> onHooks, DataAccessorDTO accessorDTO) {
		// check id exists and has access to entity
		if(!entity.exists() || crudHelper.getEntityCountById(entity.getId(), entity.getClass(), accessorDTO, true) == 0) {
			throw new CRUDException()
					.withErrorCode(ErrorCode.UpdateError)
					.withDisplayMessage("Entity of type [ " + entity.getClass().getSimpleName() + " ] does not exist or cannot be updated");
		}

		for(CRUDOnUpdateHook<ID, Entity> onHook : onHooks) {
			onHook.run(entity);
		}

		crudHelper.validate(entity);

		return crudHelper.getCrudDaoForEntity(entity.getClass()).saveOrUpdate(entity);
	}

	@Override
	@ExceptionOverride(value = CRUDException.class, errorCode = ErrorCode.UpdateError)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity updateFromInternal(ID id, Object object, Class<Entity> clazz,
			HooksDTO<CRUDPreUpdateFromHook<ID, Entity>, CRUDOnUpdateFromHook<ID, Entity>, CRUDPostUpdateFromHook<ID, Entity>> hooks,
			DataAccessorDTO accessorDTO) {
		crudHelper.checkEntityImmutability(clazz);

		List<UpdateFromHooks> updateFromHooksList = getHooks(UpdateFromHooks.class, clazz);

		if(updateFromHooksList != null && !updateFromHooksList.isEmpty()) {
			for(UpdateFromHooks<ID, Entity> updateFromHooks : updateFromHooksList) {
				hooks.getPreHooks().add(0, updateFromHooks::preUpdateFrom);
				hooks.getOnHooks().add(0, updateFromHooks::onUpdateFrom);
				hooks.getPostHooks().add(0, updateFromHooks::postUpdateFrom);
			}
		}

		Objects.requireNonNull(object, "Object cannot be null");
		for(CRUDPreUpdateFromHook<ID, Entity> preHook : hooks.getPreHooks()) {
			preHook.run(id, object);
		}

		crudHelper.validate(object);

		Entity entity = crudUpdateHandlerProxy.updateFromTransactional(id, object, clazz, hooks.getOnHooks(), accessorDTO);

		crudHelper.evictEntityFromCache(entity);

		for(CRUDPostUpdateFromHook<ID, Entity> postHook : hooks.getPostHooks()) {
			postHook.run(entity);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = false)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity updateFromTransactional(ID id, Object object, Class<Entity> clazz, List<CRUDOnUpdateFromHook<ID, Entity>> onHooks,
			DataAccessorDTO accessorDTO) {
		Entity entity = crudHelper.getEntityById(id, clazz, null, accessorDTO, true);

		if(entity == null) {
			throw new CRUDException()
					.withErrorCode(ErrorCode.UpdateError)
					.withDisplayMessage("Entity of type [ " + clazz.getSimpleName() + " ] does not exist or cannot be updated");
		}

		crudHelper.fill(object, entity);

		for(CRUDOnUpdateFromHook<ID, Entity> onHook : onHooks) {
			onHook.run(entity, object);
		}

		crudHelper.validate(entity);

		return crudHelper.getCrudDaoForEntity(clazz).saveOrUpdate(entity);
	}


}
