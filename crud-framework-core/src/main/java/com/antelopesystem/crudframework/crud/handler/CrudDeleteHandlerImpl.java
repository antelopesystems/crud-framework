package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.dataaccess.model.DataAccessorDTO;
import com.antelopesystem.crudframework.crud.exception.CRUDException;
import com.antelopesystem.crudframework.crud.hooks.HooksDTO;
import com.antelopesystem.crudframework.crud.hooks.delete.CRUDOnDeleteHook;
import com.antelopesystem.crudframework.crud.hooks.delete.CRUDPostDeleteHook;
import com.antelopesystem.crudframework.crud.hooks.delete.CRUDPreDeleteHook;
import com.antelopesystem.crudframework.crud.hooks.interfaces.CRUDHooks;
import com.antelopesystem.crudframework.crud.hooks.interfaces.DeleteHooks;
import com.antelopesystem.crudframework.crud.model.EntityMetadataDTO;
import com.antelopesystem.crudframework.exception.tree.core.ErrorCode;
import com.antelopesystem.crudframework.exception.tree.core.ExceptionOverride;
import com.antelopesystem.crudframework.model.BaseCrudEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class CrudDeleteHandlerImpl extends CrudHookHandlerBase implements CrudDeleteHandler {

	@Autowired
	private CrudHelper crudHelper;

	@Resource(name = "crudDeleteHandler")
	private CrudDeleteHandler crudDeleteHandlerProxy;

	@Override
	protected List<Class<? extends CRUDHooks>> getHookTargetClasses() {
		return Arrays.asList(DeleteHooks.class);
	}

	@Override
	@ExceptionOverride(value = CRUDException.class, errorCode = ErrorCode.DeleteError)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> Object deleteInternal(ID id, Class<Entity> clazz,
			HooksDTO<CRUDPreDeleteHook<ID, Entity>, CRUDOnDeleteHook<ID, Entity>, CRUDPostDeleteHook<ID, Entity>> hooks,
			DataAccessorDTO accessorDTO) {

		crudHelper.checkEntityImmutability(clazz);
		crudHelper.checkEntityDeletability(clazz);

		List<DeleteHooks> deleteHooksList = getHooks(DeleteHooks.class, clazz);

		if(deleteHooksList != null && !deleteHooksList.isEmpty()) {
			for(DeleteHooks<ID, Entity> deleteHooks : deleteHooksList) {
				hooks.getPreHooks().add(0, deleteHooks::preDelete);
				hooks.getOnHooks().add(0, deleteHooks::onDelete);
				hooks.getPostHooks().add(0, deleteHooks::postDelete);
			}
		}

		for(CRUDPreDeleteHook<ID, Entity> preHook : hooks.getPreHooks()) {
			preHook.run(id);
		}

		EntityMetadataDTO metadataDTO = crudHelper.getEntityMetadata(clazz);

		Entity entity;

		if(metadataDTO.getDeleteableType() == EntityMetadataDTO.DeleteableType.Hard) {
			entity = crudDeleteHandlerProxy.deleteHardTransactional(id, clazz, hooks.getOnHooks(), accessorDTO);
		} else {
			if(metadataDTO.getDeleteField() == null) {
				throw new CRUDException()
						.withDisplayMessage("No deleteColumn specified")
						.withErrorCode(ErrorCode.OperationNotSupported);
			}

			if(!ClassUtils.isAssignable(boolean.class, metadataDTO.getDeleteField().getType())) {
				throw new CRUDException()
						.withDisplayMessage("deleteColumn must be a boolean")
						.withErrorCode(ErrorCode.OperationNotSupported);
			}

			entity = crudDeleteHandlerProxy.deleteSoftTransactional(id, metadataDTO.getDeleteField().getName(), clazz, hooks.getOnHooks(), accessorDTO);
		}

		crudHelper.evictEntityFromCache(entity);

		for(CRUDPostDeleteHook<ID, Entity> postHook : hooks.getPostHooks()) {
			postHook.run(entity);
		}

		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity deleteHardTransactional(ID id, Class<Entity> clazz, List<CRUDOnDeleteHook<ID, Entity>> onHooks, DataAccessorDTO accessorDTO) {
		Entity entity = crudHelper.getEntityById(id, clazz, null, accessorDTO, true);
		if(crudHelper.isEntityDeleted(entity)) {
			throw new CRUDException()
					.withErrorCode(ErrorCode.DeleteError)
					.withDisplayMessage("Entity of type [ " + clazz.getSimpleName() + " ] does not exist or cannot be deleted");
		}

		for(CRUDOnDeleteHook<ID, Entity> onHook : onHooks) {
			onHook.run(entity);
		}

		crudHelper.getCrudDaoForEntity(clazz).hardDeleteById(id, clazz);
		return entity;
	}

	@Override
	@Transactional(readOnly = false)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity deleteSoftTransactional(ID id, String deleteField, Class<Entity> clazz, List<CRUDOnDeleteHook<ID, Entity>> onHooks,
			DataAccessorDTO accessorDTO) {
		Entity entity = crudHelper.getEntityById(id, clazz, null, accessorDTO, true);

		if(crudHelper.isEntityDeleted(entity)) {
			throw new CRUDException()
					.withErrorCode(ErrorCode.DeleteError)
					.withDisplayMessage("Entity of type [ " + clazz.getSimpleName() + " ] does not exist or cannot be deleted");
		}


		for(CRUDOnDeleteHook<ID, Entity> onHook : onHooks) {
			onHook.run(entity);
		}
		crudHelper.getCrudDaoForEntity(clazz).softDeleteById(id, deleteField, clazz);

		return entity;
	}

}
