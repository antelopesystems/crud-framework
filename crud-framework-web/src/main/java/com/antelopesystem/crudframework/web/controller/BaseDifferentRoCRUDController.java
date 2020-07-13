package com.antelopesystem.crudframework.web.controller;

import com.antelopesystem.crudframework.crud.handler.CrudHandler;
import com.antelopesystem.crudframework.crud.model.CRUDRequestBuilder;
import com.antelopesystem.crudframework.crud.model.ReadCRUDRequestBuilder;
import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import com.antelopesystem.crudframework.web.annotation.CRUDActions;
import com.antelopesystem.crudframework.web.ro.MassUpdateResult;
import com.antelopesystem.crudframework.web.ro.ResultRO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public abstract class BaseDifferentRoCRUDController<ID extends Serializable, Entity extends BaseCrudEntity<ID>, CreateRO, UpdateRO, ReturnRO> extends BaseController {

	@Autowired
	private CrudHandler crudHandler;

	protected Class<Entity> entityClazz;

	protected Class<ReturnRO> roClazz;

	protected Class<?> getAccessorType() {
		return null;
	}

	protected Long getAccessorId() {
		return null;
	}

	private boolean shouldEnforce() {
		return getAccessorType() != null && getAccessorId() != null;
	}

	@PostConstruct
	private void init() {
		Type[] actualTypes = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
		entityClazz = (Class<Entity>) actualTypes[1];
		roClazz = (Class<ReturnRO>) actualTypes[actualTypes.length - 1];
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResultRO show(@PathVariable ID id) {
		verifyOperation(CRUDActionType.Show);
		return wrapResult(() -> {
			CRUDRequestBuilder builder = crudHandler.show(id, entityClazz, roClazz);
			if(shouldEnforce()) {
				builder.enforceAccess(getAccessorType(), getAccessorId());
			}

			return builder.execute();
		});


	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResultRO index(DynamicModelFilter filter) {
		verifyOperation(CRUDActionType.Index);
		return wrapResult(() -> {
			ReadCRUDRequestBuilder builder = crudHandler.index(filter, entityClazz, roClazz);
			if(shouldEnforce()) {
				builder.enforceAccess(getAccessorType(), getAccessorId());
			}

			return builder.execute();
		});
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	@ResponseBody
	public ResultRO indexCount(DynamicModelFilter filter) {
		verifyOperation(CRUDActionType.Index);
		return wrapResult(() -> {
			ReadCRUDRequestBuilder builder = crudHandler.index(filter, entityClazz, roClazz);
			if(shouldEnforce()) {
				builder.enforceAccess(getAccessorType(), getAccessorId());
			}

			return builder.fromCache().count();
		});
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	public ResultRO search(@RequestBody DynamicModelFilter filter) {
		verifyOperation(CRUDActionType.Index);
		return wrapResult(() -> {
			ReadCRUDRequestBuilder builder = crudHandler.index(filter, entityClazz, roClazz);
			if(shouldEnforce()) {
				builder.enforceAccess(getAccessorType(), getAccessorId());
			}

			return builder.execute();
		});
	}

	@RequestMapping(value = "/search/count", method = RequestMethod.POST)
	@ResponseBody
	public ResultRO searchCount(@RequestBody DynamicModelFilter filter) {
		verifyOperation(CRUDActionType.Index);
		return wrapResult(() -> {
			ReadCRUDRequestBuilder builder = crudHandler.index(filter, entityClazz, roClazz);
			if(shouldEnforce()) {
				builder.enforceAccess(getAccessorType(), getAccessorId());
			}

			return builder.fromCache().count();
		});
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResultRO create(@RequestBody CreateRO ro) {
		verifyOperation(CRUDActionType.Create);
		return wrapResult(() -> {
			CRUDRequestBuilder builder = crudHandler.createFrom(ro, entityClazz, roClazz);
			if(shouldEnforce()) {
				builder.enforceAccess(getAccessorType(), getAccessorId());
			}

			return builder.execute();
		});
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResultRO update(@PathVariable ID id, @RequestBody UpdateRO ro) {
		verifyOperation(CRUDActionType.Update);
		return wrapResult(() -> {
			CRUDRequestBuilder builder = crudHandler.updateFrom(id, ro, entityClazz, roClazz);
			if(shouldEnforce()) {
				builder.enforceAccess(getAccessorType(), getAccessorId());
			}

			return builder.execute();
		});
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResultRO delete(@PathVariable ID id) {
		verifyOperation(CRUDActionType.Delete);
		return wrapResult(() -> {
			CRUDRequestBuilder builder = crudHandler.delete(id, entityClazz);
			if(shouldEnforce()) {
				builder.enforceAccess(getAccessorType(), getAccessorId());
			}

			return builder.execute();
		});
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public ResultRO deleteMany(@RequestBody List<ID> ids) {
		verifyOperation(CRUDActionType.Delete);
		return wrapResult(() -> {
			Set<ID> successfulIds = new HashSet<>();
			Map<ID, String> failedIds = new HashMap<>();
			for(ID id : ids) {
				CRUDRequestBuilder builder = crudHandler.delete(id, entityClazz);
				if(shouldEnforce()) {
					builder.enforceAccess(getAccessorType(), getAccessorId());
				}

				try {
					builder.execute();
					successfulIds.add(id);
				} catch(Exception e) {
					failedIds.put(id, e.getMessage());
				}
			}

			return new MassUpdateResult<ID>(successfulIds, failedIds);
		});
	}

	private void verifyOperation(CRUDActionType crudActionType) {
		CRUDActions crudActions = getClass().getDeclaredAnnotation(CRUDActions.class);
		if(crudActions == null) {
			return;
		}

		switch(crudActionType) {
			case Show:
				if(!crudActions.show()) {
					throw new UnsupportedOperationException(" [ " + crudActionType.name() + " ] operation for entity [ " + entityClazz.getSimpleName() + " ] not supported");
				}

				break;
			case Index:
				if(!crudActions.index()) {
					throw new UnsupportedOperationException(" [ " + crudActionType.name() + " ] operation for entity [ " + entityClazz.getSimpleName() + " ] not supported");
				}

				break;
			case Create:
				if(!crudActions.create()) {
					throw new UnsupportedOperationException(" [ " + crudActionType.name() + " ] operation for entity [ " + entityClazz.getSimpleName() + " ] not supported");
				}

				break;
			case Update:
				if(!crudActions.update()) {
					throw new UnsupportedOperationException(" [ " + crudActionType.name() + " ] operation for entity [ " + entityClazz.getSimpleName() + " ] not supported");
				}

				break;
			case Delete:
				if(!crudActions.delete()) {
					throw new UnsupportedOperationException(" [ " + crudActionType.name() + " ] operation for entity [ " + entityClazz.getSimpleName() + " ] not supported");
				}

				break;
		}
	}

	private enum CRUDActionType {
		Show, Index, Create, Update, Delete
	}

}
