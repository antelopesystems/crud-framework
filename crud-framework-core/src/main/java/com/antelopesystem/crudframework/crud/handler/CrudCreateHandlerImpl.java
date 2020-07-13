package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.dataaccess.DataAccessManager;
import com.antelopesystem.crudframework.crud.dataaccess.model.DataAccessorDTO;
import com.antelopesystem.crudframework.crud.exception.CRUDException;
import com.antelopesystem.crudframework.crud.hooks.HooksDTO;
import com.antelopesystem.crudframework.crud.hooks.create.CRUDOnCreateHook;
import com.antelopesystem.crudframework.crud.hooks.create.CRUDPostCreateHook;
import com.antelopesystem.crudframework.crud.hooks.create.CRUDPreCreateHook;
import com.antelopesystem.crudframework.crud.hooks.create.from.CRUDOnCreateFromHook;
import com.antelopesystem.crudframework.crud.hooks.create.from.CRUDPostCreateFromHook;
import com.antelopesystem.crudframework.crud.hooks.create.from.CRUDPreCreateFromHook;
import com.antelopesystem.crudframework.crud.hooks.interfaces.CRUDHooks;
import com.antelopesystem.crudframework.crud.hooks.interfaces.CreateFromHooks;
import com.antelopesystem.crudframework.crud.hooks.interfaces.CreateHooks;
import com.antelopesystem.crudframework.exception.tree.core.ErrorCode;
import com.antelopesystem.crudframework.exception.tree.core.ExceptionOverride;
import com.antelopesystem.crudframework.model.BaseCrudEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component("crudCreateHandler")
class CrudCreateHandlerImpl extends CrudHookHandlerBase implements CrudCreateHandler {

	@Autowired
	private CrudHelper crudHelper;

	@Resource(name = "crudCreateHandler")
	private CrudCreateHandler crudCreateHandlerProxy;

	@Override
	protected List<Class<? extends CRUDHooks>> getHookTargetClasses() {
		return Arrays.asList(CreateHooks.class, CreateFromHooks.class);
	}

	@Override
	@ExceptionOverride(value = CRUDException.class, errorCode = ErrorCode.CreateError)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity createInternal(Entity entity, HooksDTO<CRUDPreCreateHook<ID, Entity>, CRUDOnCreateHook<ID, Entity>, CRUDPostCreateHook<ID, Entity>> hooks,
			DataAccessorDTO accessorDTO) {
		Objects.requireNonNull(entity, "Entity cannot be null");

		List<CreateHooks> createHooksList = getHooks(CreateHooks.class, entity.getClass());

		if(createHooksList != null && !createHooksList.isEmpty()) {
			for(CreateHooks<ID, Entity> createHooks : createHooksList) {
				hooks.getPreHooks().add(0, createHooks::preCreate);
				hooks.getOnHooks().add(0, createHooks::onCreate);
				hooks.getPostHooks().add(0, createHooks::postCreate);
			}
		}

		for(CRUDPreCreateHook<ID, Entity> preHook : hooks.getPreHooks()) {
			preHook.run(entity);
		}

		entity = crudCreateHandlerProxy.createTransactional(entity, hooks.getOnHooks(), accessorDTO);
		for(CRUDPostCreateHook<ID, Entity> postHook : hooks.getPostHooks()) {
			postHook.run(entity);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = false)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity createTransactional(Entity entity, List<CRUDOnCreateHook<ID, Entity>> onHooks, DataAccessorDTO accessorDTO) {

		if(accessorDTO != null) {
			DataAccessManager dataAccessManager = crudHelper.getAccessorManager(accessorDTO.getAccessorClazz(), entity.getClass());
			if(dataAccessManager != null) {
				dataAccessManager.decorateCreateOperation(entity, accessorDTO.getAccessorId(), accessorDTO.getAccessorClazz());
			}
		}

		for(CRUDOnCreateHook<ID, Entity> onHook : onHooks) {
			onHook.run(entity);
		}

		crudHelper.validate(entity);

		return crudHelper.getCrudDaoForEntity(entity.getClass()).saveOrUpdate(entity);
	}

	@Override
	@ExceptionOverride(value = CRUDException.class, errorCode = ErrorCode.CreateError)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity createFromInternal(Object object, Class<Entity> clazz,
			HooksDTO<CRUDPreCreateFromHook<ID, Entity>, CRUDOnCreateFromHook<ID, Entity>, CRUDPostCreateFromHook<ID, Entity>> hooks,
			DataAccessorDTO accessorDTO) {
		Objects.requireNonNull(object, "Object cannot be null");

		List<CreateFromHooks> createFromHooksList = getHooks(CreateFromHooks.class, clazz);

		if(createFromHooksList != null && !createFromHooksList.isEmpty()) {
			for(CreateFromHooks<ID, Entity> createFromHooks : createFromHooksList) {
				hooks.getPreHooks().add(0, createFromHooks::preCreateFrom);
				hooks.getOnHooks().add(0, createFromHooks::onCreateFrom);
				hooks.getPostHooks().add(0, createFromHooks::postCreateFrom);
			}
		}

		for(CRUDPreCreateFromHook preHook : hooks.getPreHooks()) {
			preHook.run(object);
		}

		crudHelper.validate(object);

		Entity entity = crudCreateHandlerProxy.createFromTransactional(object, clazz, hooks.getOnHooks(), accessorDTO);
		for(CRUDPostCreateFromHook<ID, Entity> postHook : hooks.getPostHooks()) {
			postHook.run(entity);
		}
		return entity;
	}

	@Override
	@Transactional(readOnly = false)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity createFromTransactional(Object object, Class<Entity> clazz, List<CRUDOnCreateFromHook<ID, Entity>> onHooks, DataAccessorDTO accessorDTO) {
		Entity entity = crudHelper.fill(object, clazz);

		if(entity.exists()) {
			throw new CRUDException()
					.withErrorCode(ErrorCode.CreateError)
					.withDisplayMessage("Entity of type [ " + clazz.getSimpleName() + " ] with ID [ " + entity.getId() + " ] already exists and cannot be created");
		}

		if(accessorDTO != null) {
			DataAccessManager dataAccessManager = crudHelper.getAccessorManager(accessorDTO.getAccessorClazz(), clazz);
			if(dataAccessManager != null) {
				dataAccessManager.decorateCreateOperation(entity, accessorDTO.getAccessorId(), accessorDTO.getAccessorClazz());
			}
		}

		for(CRUDOnCreateFromHook<ID, Entity> onHook : onHooks) {
			onHook.run(entity, object);
		}

		crudHelper.validate(entity);

		return crudHelper.getCrudDaoForEntity(clazz).saveOrUpdate(entity);
	}

}
