package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.enums.ShowByMode;
import com.antelopesystem.crudframework.crud.exception.CrudException;
import com.antelopesystem.crudframework.crud.hooks.create.*;
import com.antelopesystem.crudframework.crud.hooks.create.from.*;
import com.antelopesystem.crudframework.crud.hooks.delete.*;
import com.antelopesystem.crudframework.crud.hooks.index.*;
import com.antelopesystem.crudframework.crud.hooks.show.*;
import com.antelopesystem.crudframework.crud.hooks.show.by.*;
import com.antelopesystem.crudframework.crud.hooks.update.*;
import com.antelopesystem.crudframework.crud.hooks.update.from.*;
import com.antelopesystem.crudframework.crud.model.*;
import com.antelopesystem.crudframework.exception.WrapException;
import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import com.antelopesystem.crudframework.ro.PagingDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

@WrapException(CrudException.class)
public class CrudHandlerImpl implements CrudHandler {

	@Autowired
	private CrudReadHandler crudReadHandler;

	@Autowired
	private CrudUpdateHandler crudUpdateHandler;

	@Autowired
	private CrudDeleteHandler crudDeleteHandler;

	@Autowired
	private CrudCreateHandler crudCreateHandler;

	@Autowired
	private CrudHelper crudHelper;

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, Filter extends DynamicModelFilter> ReadCRUDRequestBuilder<CRUDPreIndexHook<ID, Entity, Filter>, CRUDOnIndexHook<ID, Entity, Filter>, CRUDPostIndexHook<ID, Entity, Filter>, PagingDTO<Entity>> index(
			Filter filter, Class<Entity> clazz) {
		return new ReadCRUDRequestBuilder<>(
				(hooks, fromCache, persistCopy, accessorDTO) -> crudReadHandler.indexInternal(filter, clazz, hooks, fromCache, persistCopy, accessorDTO, false),
				(hooks, fromCache, persistCopy, accessorDTO) -> crudReadHandler.indexInternal(filter, clazz, hooks, fromCache, persistCopy, accessorDTO, true).getPagingRO().getTotal()
		);
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, Filter extends DynamicModelFilter, RO> ReadCRUDRequestBuilder<CRUDPreIndexHook<ID, Entity, Filter>, CRUDOnIndexHook<ID, Entity, Filter>, CRUDPostIndexHook<ID, Entity, Filter>, PagingDTO<RO>> index(
			Filter filter, Class<Entity> clazz, Class<RO> toClazz) {
		return new ReadCRUDRequestBuilder<>(
				(hooks, fromCache, persistCopy, accessorDTO) -> {
					PagingDTO<Entity> resultDTO = crudReadHandler.indexInternal(filter, clazz, hooks, fromCache, persistCopy, accessorDTO, false);
					List<RO> mappedResults = crudHelper.fillMany(resultDTO.getData(), toClazz);
					return new PagingDTO<>(resultDTO.getPagingRO(), mappedResults);
				}, (hooks, fromCache, persistCopy, accessorDTO) -> crudReadHandler.indexInternal(filter, clazz, hooks, fromCache, persistCopy, accessorDTO, true).getPagingRO().getTotal());
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> UpdateCRUDRequestBuilder<CRUDPreDeleteHook<ID, Entity>, CRUDOnDeleteHook<ID, Entity>, CRUDPostDeleteHook<ID, Entity>, Void> delete(ID id,
			Class<Entity> clazz) {
		return new UpdateCRUDRequestBuilder<>((hooks, accessorDTO) -> {
			crudDeleteHandler.deleteInternal(id, clazz, hooks, accessorDTO);
			return null;
		});
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> UpdateCRUDRequestBuilder<CRUDPreCreateFromHook<ID, Entity>, CRUDOnCreateFromHook<ID, Entity>, CRUDPostCreateFromHook<ID, Entity>, Entity> createFrom(
			Object object, Class<Entity> clazz) {
		return new UpdateCRUDRequestBuilder<>((hooks, accessorDTO) -> crudCreateHandler.createFromInternal(object, clazz, hooks, accessorDTO));
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, RO> UpdateCRUDRequestBuilder<CRUDPreCreateFromHook<ID, Entity>, CRUDOnCreateFromHook<ID, Entity>, CRUDPostCreateFromHook<ID, Entity>, RO> createFrom(
			Object object, Class<Entity> clazz, Class<RO> toClazz) {
		return new UpdateCRUDRequestBuilder<>((hooks, accessorDTO) -> {
			Entity result = crudCreateHandler.createFromInternal(object, clazz, hooks, accessorDTO);
			return crudHelper.fill(result, toClazz);
		});
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> UpdateCRUDRequestBuilder<CRUDPreCreateHook<ID, Entity>, CRUDOnCreateHook<ID, Entity>, CRUDPostCreateHook<ID, Entity>, Entity> create(
			Entity entity) {
		return new UpdateCRUDRequestBuilder<>((hooks, accessorDTO) -> crudCreateHandler.createInternal(entity, hooks, accessorDTO));
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, RO> UpdateCRUDRequestBuilder<CRUDPreCreateHook<ID, Entity>, CRUDOnCreateHook<ID, Entity>, CRUDPostCreateHook<ID, Entity>, RO> create(Entity entity,
			Class<RO> toClazz) {
		return new UpdateCRUDRequestBuilder<>((hooks, accessorDTO) -> {
			Entity result = crudCreateHandler.createInternal(entity, hooks, accessorDTO);
			return crudHelper.fill(result, toClazz);
		});
	}


	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> UpdateCRUDRequestBuilder<CRUDPreUpdateFromHook<ID, Entity>, CRUDOnUpdateFromHook<ID, Entity>, CRUDPostUpdateFromHook<ID, Entity>, Entity> updateFrom(
			ID id, Object object, Class<Entity> clazz) {
		return new UpdateCRUDRequestBuilder<>((hooks, accessorDTO) -> crudUpdateHandler.updateFromInternal(id, object, clazz, hooks, accessorDTO));
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, RO> UpdateCRUDRequestBuilder<CRUDPreUpdateFromHook<ID, Entity>, CRUDOnUpdateFromHook<ID, Entity>, CRUDPostUpdateFromHook<ID, Entity>, RO> updateFrom(
			ID id, Object object, Class<Entity> clazz, Class<RO> toClazz) {
		return new UpdateCRUDRequestBuilder<>((hooks, accessorDTO) -> {
			Entity result = crudUpdateHandler.updateFromInternal(id, object, clazz, hooks, accessorDTO);
			return crudHelper.fill(result, toClazz);
		});
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> UpdateCRUDRequestBuilder<CRUDPreUpdateHook<ID, Entity>, CRUDOnUpdateHook<ID, Entity>, CRUDPostUpdateHook<ID, Entity>, Entity> update(
			Entity entity) {
		return new UpdateCRUDRequestBuilder<>((hooks, accessorDTO) -> crudUpdateHandler.updateInternal(entity, hooks, accessorDTO));
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, RO> UpdateCRUDRequestBuilder<CRUDPreUpdateHook<ID, Entity>, CRUDOnUpdateHook<ID, Entity>, CRUDPostUpdateHook<ID, Entity>, RO> update(Entity entity,
			Class<RO> toClazz) {
		return new UpdateCRUDRequestBuilder<>((hooks, accessorDTO) -> {
			Entity result = crudUpdateHandler.updateInternal(entity, hooks, accessorDTO);
			return crudHelper.fill(result, toClazz);
		});
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> MassUpdateCRUDRequestBuilder<CRUDPreUpdateHook<ID, Entity>, CRUDOnUpdateHook<ID, Entity>, CRUDPostUpdateHook<ID, Entity>, List<Entity>> update(
			List<Entity> entities) {
		return new MassUpdateCRUDRequestBuilder<>((hooks, persistCopy, accessorDTO) -> crudUpdateHandler.updateManyTransactional(entities, hooks, persistCopy, accessorDTO));
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, RO> MassUpdateCRUDRequestBuilder<CRUDPreUpdateHook<ID, Entity>, CRUDOnUpdateHook<ID, Entity>, CRUDPostUpdateHook<ID, Entity>, List<RO>> update(
			List<Entity> entities, Class<RO> toClazz) {
		return new MassUpdateCRUDRequestBuilder<>((hooks, persistCopy, accessorDTO) -> {
			List<Entity> result = crudUpdateHandler.updateManyTransactional(entities, hooks, persistCopy, accessorDTO);
			return crudHelper.fillMany(result, toClazz);
		});
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> MassUpdateCRUDRequestBuilder<CRUDPreUpdateHook<ID, Entity>, CRUDOnUpdateHook<ID, Entity>, CRUDPostUpdateHook<ID, Entity>, List<Entity>> updateByFilter(
			DynamicModelFilter filter, Class<Entity> entityClazz) {
		return new MassUpdateCRUDRequestBuilder<>((hooks, persistCopy, accessorDTO) -> crudUpdateHandler.updateByFilterTransactional(filter, entityClazz, hooks, persistCopy, accessorDTO));
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, RO> MassUpdateCRUDRequestBuilder<CRUDPreUpdateHook<ID, Entity>, CRUDOnUpdateHook<ID, Entity>, CRUDPostUpdateHook<ID, Entity>, List<RO>> updateByFilter(
			DynamicModelFilter filter, Class<Entity> entityClazz, Class<RO> toClazz) {
		return new MassUpdateCRUDRequestBuilder<>((hooks, persistCopy, accessorDTO) -> {
			List<Entity> result = crudUpdateHandler.updateByFilterTransactional(filter, entityClazz, hooks, persistCopy, accessorDTO);
			return crudHelper.fillMany(result, toClazz);
		});
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, Filter extends DynamicModelFilter> ReadCRUDRequestBuilder<CRUDPreShowByHook<ID, Entity, Filter>, CRUDOnShowByHook<ID, Entity>, CRUDPostShowByHook<ID, Entity>, Entity> showBy(
			Filter filter, Class<Entity> clazz) {
		return showBy(filter, clazz, ShowByMode.THROW_EXCEPTION);
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, Filter extends DynamicModelFilter> ReadCRUDRequestBuilder<CRUDPreShowByHook<ID, Entity, Filter>, CRUDOnShowByHook<ID, Entity>, CRUDPostShowByHook<ID, Entity>, Entity> showBy(
			Filter filter, Class<Entity> clazz, ShowByMode mode) {
		return new ReadCRUDRequestBuilder<>(
				(hooks, fromCache, persistCopy, accessorDTO) -> crudReadHandler.showByInternal(filter, clazz, hooks, fromCache, persistCopy, mode, accessorDTO),
				(hooks, fromCache, persistCopy, accessorDTO) -> crudReadHandler.showByInternal(filter, clazz, hooks, fromCache, persistCopy, mode, accessorDTO) != null ? 1L : 0L
		);
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, Filter extends DynamicModelFilter, RO> ReadCRUDRequestBuilder<CRUDPreShowByHook<ID, Entity, Filter>, CRUDOnShowByHook<ID, Entity>, CRUDPostShowByHook<ID, Entity>, RO> showBy(
			Filter filter, Class<Entity> clazz, Class<RO> toClazz) {
		return showBy(filter, clazz, toClazz, ShowByMode.THROW_EXCEPTION);
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, Filter extends DynamicModelFilter, RO> ReadCRUDRequestBuilder<CRUDPreShowByHook<ID, Entity, Filter>, CRUDOnShowByHook<ID, Entity>, CRUDPostShowByHook<ID, Entity>, RO> showBy(
			Filter filter, Class<Entity> clazz, Class<RO> toClazz, ShowByMode mode) {
		return new ReadCRUDRequestBuilder<>(
				(hooks, fromCache, persistCopy, accessorDTO) -> {
					Entity result = crudReadHandler.showByInternal(filter, clazz, hooks, fromCache, persistCopy, mode, accessorDTO);
					if(result == null) {
						return null;
					}

					return crudHelper.fill(result, toClazz);
				}, (hooks, fromCache, persistCopy, accessorDTO) -> crudReadHandler.showByInternal(filter, clazz, hooks, fromCache, persistCopy, mode, accessorDTO) != null ? 1L : 0L
		);
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> ReadCRUDRequestBuilder<CRUDPreShowHook<ID, Entity>, CRUDOnShowHook<ID, Entity>, CRUDPostShowHook<ID, Entity>, Entity> show(ID id,
			Class<Entity> clazz) {
		return new ReadCRUDRequestBuilder<>(
				(hooks, fromCache, persistCopy, accessorDTO) -> crudReadHandler.showInternal(id, clazz, hooks, fromCache, persistCopy, accessorDTO),
				(hooks, fromCache, persistCopy, accessorDTO) -> crudReadHandler.showInternal(id, clazz, hooks, fromCache, persistCopy, accessorDTO) != null ? 1L : 0L
		);
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, RO> ReadCRUDRequestBuilder<CRUDPreShowHook<ID, Entity>, CRUDOnShowHook<ID, Entity>, CRUDPostShowHook<ID, Entity>, RO> show(ID id,
			Class<Entity> clazz, Class<RO> toClazz) {
		return new ReadCRUDRequestBuilder<>((hooks, fromCache, persistCopy, accessorDTO) -> {
			Entity result = crudReadHandler.showInternal(id, clazz, hooks, fromCache, persistCopy, accessorDTO);
			if(result == null) {
				return null;
			}

			return crudHelper.fill(result, toClazz);
		}, (hooks, fromCache, persistCopy, accessorDTO) -> crudReadHandler.showInternal(id, clazz, hooks, fromCache, persistCopy, accessorDTO) != null ? 1L : 0L
		);
	}

	@Override
	public <Entity, RO> RO fill(Entity fromObject, Class<RO> toClazz) {
		return crudHelper.fill(fromObject, toClazz);
	}

	@Override
	public <Entity, RO> void fill(Entity fromObject, RO toObject) {
		crudHelper.fill(fromObject, toObject);
	}

	@Override
	public <From, To> List<To> fillMany(List<From> fromObjects, Class<To> toClazz) {
		return crudHelper.fillMany(fromObjects, toClazz);
	}

	@Override
	public void validate(Object target) {
		crudHelper.validate(target);
	}

}
