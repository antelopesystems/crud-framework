package com.antelopesystem.crudframework.web.controller;

import com.antelopesystem.crudframework.crud.handler.CrudHandler;
import com.antelopesystem.crudframework.crud.model.CRUDRequestBuilder;
import com.antelopesystem.crudframework.crud.model.ReadCRUDRequestBuilder;
import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import com.antelopesystem.crudframework.ro.BaseRO;
import com.antelopesystem.crudframework.web.annotation.CRUDActions;
import com.antelopesystem.crudframework.web.ro.ResultRO;
import com.antelopesystem.crudframework.web.ro.ManyCrudResult;
import com.antelopesystem.crudframework.web.ro.ManyFailedReason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseDifferentRoCRUDController<ID extends Serializable, Entity extends BaseCrudEntity<ID>, CreateRO extends BaseRO<ID>, UpdateRO extends BaseRO<ID>, ShowReturnRO extends BaseRO<ID>, IndexReturnRO extends BaseRO<ID>> extends BaseController {

	@Autowired
	private CrudHandler crudHandler;

	protected Class<Entity> entityClazz;

	protected Class<ShowReturnRO> showRoClazz;

	protected Class<IndexReturnRO> indexRoClazz;

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
		showRoClazz = (Class<ShowReturnRO>) actualTypes[actualTypes.length - 2];
		indexRoClazz = (Class<IndexReturnRO>) actualTypes[actualTypes.length - 1];
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResultRO show(@PathVariable ID id) {
		verifyOperation(CRUDActionType.Show);
		return wrapResult(() -> {
			CRUDRequestBuilder builder = crudHandler.show(id, entityClazz, showRoClazz);
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
			ReadCRUDRequestBuilder builder = crudHandler.index(filter, entityClazz, indexRoClazz);
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
			ReadCRUDRequestBuilder builder = crudHandler.index(filter, entityClazz);
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
			ReadCRUDRequestBuilder builder = crudHandler.index(filter, entityClazz, indexRoClazz);
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
			ReadCRUDRequestBuilder builder = crudHandler.index(filter, entityClazz);
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
			CRUDRequestBuilder builder = crudHandler.createFrom(ro, entityClazz, showRoClazz);
			if(shouldEnforce()) {
				builder.enforceAccess(getAccessorType(), getAccessorId());
			}

			return builder.execute();
		});
	}

	@RequestMapping(value = "/many", method = RequestMethod.POST)
	@ResponseBody
	public ResultRO createMany(@RequestBody List<CreateRO> ros) {
		verifyOperation(CRUDActionType.Create);
		return wrapResult(() -> {
			Set<ShowReturnRO> successful = new HashSet<>();
			List<ManyFailedReason<CreateRO>> failed = new ArrayList();
			for(CreateRO ro : ros) {
				CRUDRequestBuilder builder = crudHandler.createFrom(ro, entityClazz, showRoClazz);
				if(shouldEnforce()) {
					builder.enforceAccess(getAccessorType(), getAccessorId());
				}

				try {
					successful.add((ShowReturnRO) builder.execute());
				} catch(Exception e) {
					failed.add(new ManyFailedReason(ro, e.getMessage()));
				}
			}

			return new ManyCrudResult(successful, failed);
		});
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResultRO update(@PathVariable ID id, @RequestBody UpdateRO ro) {
		verifyOperation(CRUDActionType.Update);
		return wrapResult(() -> {
			CRUDRequestBuilder builder = crudHandler.updateFrom(id, ro, entityClazz, showRoClazz);
			if(shouldEnforce()) {
				builder.enforceAccess(getAccessorType(), getAccessorId());
			}

			return builder.execute();
		});
	}

	@RequestMapping(value = "/many", method = RequestMethod.PUT)
	@ResponseBody
	public ResultRO updateMany(@RequestBody List<UpdateRO> ros) {
		verifyOperation(CRUDActionType.Update);
		return wrapResult(() -> {
			Set<ShowReturnRO> successful = new HashSet<>();
			List<ManyFailedReason<UpdateRO>> failed = new ArrayList();
			for(UpdateRO ro : ros) {
				CRUDRequestBuilder builder = crudHandler.updateFrom(ro.getId(), ro, entityClazz, showRoClazz);
				if(shouldEnforce()) {
					builder.enforceAccess(getAccessorType(), getAccessorId());
				}

				try {
					successful.add((ShowReturnRO) builder.execute());
				} catch(Exception e) {
					failed.add(new ManyFailedReason(ro, e.getMessage()));
				}
			}

			return new ManyCrudResult(successful, failed);
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
			Set<ID> successful = new HashSet<>();
			List<ManyFailedReason<ID>> failed = new ArrayList<>();
			for(ID id : ids) {
				CRUDRequestBuilder builder = crudHandler.delete(id, entityClazz);
				if(shouldEnforce()) {
					builder.enforceAccess(getAccessorType(), getAccessorId());
				}

				try {
					builder.execute();
					successful.add(id);
				} catch(Exception e) {
					failed.add(new ManyFailedReason(id, e.getMessage()));
				}
			}

			return new ManyCrudResult(successful, failed);
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
