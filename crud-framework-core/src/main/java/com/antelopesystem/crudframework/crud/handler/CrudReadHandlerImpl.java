package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.dataaccess.model.DataAccessorDTO;
import com.antelopesystem.crudframework.crud.enums.ShowByMode;
import com.antelopesystem.crudframework.crud.exception.CrudReadException;
import com.antelopesystem.crudframework.crud.hooks.HooksDTO;
import com.antelopesystem.crudframework.crud.hooks.index.*;
import com.antelopesystem.crudframework.crud.hooks.interfaces.*;
import com.antelopesystem.crudframework.crud.hooks.show.*;
import com.antelopesystem.crudframework.crud.hooks.show.by.*;
import com.antelopesystem.crudframework.exception.WrapException;
import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import com.antelopesystem.crudframework.ro.PagingDTO;
import com.antelopesystem.crudframework.ro.PagingRO;
import com.antelopesystem.crudframework.utils.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

@WrapException(CrudReadException.class)
public class CrudReadHandlerImpl implements CrudReadHandler {

	@Autowired
	private CrudHelper crudHelper;

	@Resource(name = "crudReadHandler")
	private CrudReadHandler crudReadHandlerProxy;

	private static Random random = new Random();

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, Filter extends DynamicModelFilter> PagingDTO<Entity> indexInternal(Filter filter, Class<Entity> clazz,
			HooksDTO<CRUDPreIndexHook<ID, Entity, Filter>, CRUDOnIndexHook<ID, Entity, Filter>, CRUDPostIndexHook<ID, Entity, Filter>> hooks,
			boolean fromCache, Boolean persistCopy, DataAccessorDTO accessorDTO, boolean count) {
		if(filter == null) {
			filter = (Filter) new DynamicModelFilter();
		}

		List<IndexHooks> indexHooksList = crudHelper.getHooks(IndexHooks.class, clazz);

		if(indexHooksList != null && !indexHooksList.isEmpty()) {
			for(IndexHooks<ID, Entity> indexHooks : indexHooksList) {
				hooks.getPreHooks().add(0, indexHooks::preIndex);
				hooks.getOnHooks().add(0, indexHooks::onIndex);
				hooks.getPostHooks().add(0, indexHooks::postIndex);
			}
		}

		org.springframework.cache.Cache cache = null;

		if(fromCache) {
			cache = crudHelper.getEntityCache(clazz);
		}

		for(CRUDPreIndexHook<ID, Entity, Filter> preHook : hooks.getPreHooks()) {
			preHook.run(filter);
		}

		String cacheKey = filter.getCacheKey();
		if(count) {
			cacheKey = "count_" + cacheKey;
		}


		Filter finalFilter = filter;
		PagingDTO<Entity> result = (PagingDTO<Entity>) CacheUtils.getObjectAndCache(() -> crudReadHandlerProxy.indexTransactional(finalFilter, clazz, hooks.getOnHooks(), persistCopy, accessorDTO, count), cacheKey, cache);

		for(CRUDPostIndexHook<ID, Entity, Filter> postHook : hooks.getPostHooks()) {
			postHook.run(filter, result);
		}

		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, Filter extends DynamicModelFilter> PagingDTO<Entity> indexTransactional(Filter filter, Class<Entity> clazz,
			List<CRUDOnIndexHook<ID, Entity, Filter>> onHooks,
			Boolean persistCopy, DataAccessorDTO accessorDTO, boolean count) {
		PagingDTO<Entity> result;
		if(!count) {
			long total;
			List<Entity> data;
			boolean hasMore;

			if(filter.getLimit() != null) {
				filter.setLimit(filter.getLimit() + 1);
				data = crudHelper.getEntities(filter, clazz, accessorDTO, persistCopy, false);
				hasMore = data.size() == filter.getLimit();
				filter.setLimit(filter.getLimit() - 1);
				int start = filter.getStart() == null ? 0 : filter.getStart();
				if(hasMore) {
					data.remove(data.size() - 1);
				} else {
					crudHelper.setTotalToPagingCache(clazz, filter, data.size() + start);
				}

				Long cachedTotal = crudHelper.getTotalFromPagingCache(clazz, filter);
				if(cachedTotal != null) {
					hasMore = false;
					total = cachedTotal;
				} else {
					total = data.size() + start;
				}
			} else {
				data = crudHelper.getEntities(filter, clazz, accessorDTO, persistCopy, false);
				hasMore = false;
				total = data.size();
				crudHelper.setTotalToPagingCache(clazz, filter, total);

			}

			result = new PagingDTO<>(new PagingRO(filter.getStart(), filter.getLimit(), total, hasMore), data);
		} else {
			long total = crudHelper.getEntitiesCount(filter, clazz, accessorDTO, false);
			result = new PagingDTO<>(new PagingRO(0, 0, total), null);
			crudHelper.setTotalToPagingCache(clazz, filter, total);
		}

		for(CRUDOnIndexHook<ID, Entity, Filter> onHook : onHooks) {
			onHook.run(filter, result);
		}

		return result;
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, Filter extends DynamicModelFilter> Entity showByInternal(Filter filter, Class<Entity> clazz,
			HooksDTO<CRUDPreShowByHook<ID, Entity, Filter>, CRUDOnShowByHook<ID, Entity>, CRUDPostShowByHook<ID, Entity>> hooks, boolean fromCache, Boolean persistCopy, ShowByMode mode, DataAccessorDTO accessorDTO) {

		if(filter == null) {
			filter = (Filter) new DynamicModelFilter();
		}

		List<ShowByHooks> showByHooksList = crudHelper.getHooks(ShowByHooks.class, clazz);

		if(showByHooksList != null && !showByHooksList.isEmpty()) {
			for(ShowByHooks<ID, Entity> showByHooks : showByHooksList) {
				hooks.getPreHooks().add(0, showByHooks::preShowBy);
				hooks.getOnHooks().add(0, showByHooks::onShowBy);
				hooks.getPostHooks().add(0, showByHooks::postShowBy);
			}
		}

		for(CRUDPreShowByHook<ID, Entity, Filter> preHook : hooks.getPreHooks()) {
			preHook.run(filter);
		}

		org.springframework.cache.Cache cache = null;
		if(fromCache) {
			cache = crudHelper.getEntityCache(clazz);
		}

		Filter finalFilter = filter;

		Entity entity = (Entity) CacheUtils.getObjectAndCache(() -> crudReadHandlerProxy.showByTransactional(finalFilter, clazz, hooks.getOnHooks(), persistCopy, mode, accessorDTO), "showBy_" + filter.hashCode(), cache);

		for(CRUDPostShowByHook<ID, Entity> postHook : hooks.getPostHooks()) {
			postHook.run(entity);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, Filter extends DynamicModelFilter> Entity showByTransactional(Filter filter, Class<Entity> clazz, List<CRUDOnShowByHook<ID, Entity>> onHooks,
			Boolean persistCopy,
			ShowByMode mode,
			DataAccessorDTO accessorDTO) {
		List<Entity> entities = crudHelper.getEntities(filter, clazz, accessorDTO, persistCopy, false);
		Entity entity = null;
		switch(mode) {
			case THROW_EXCEPTION:
				if(entities.size() > 1) {
					throw new CrudReadException("Received a non unique result");
				}
				entity = entities.size() > 0 ? entities.get(0) : null;

				break;
			case RETURN_FIRST:
				if(entities.size() > 0) {
					entity = entities.get(0);
				}
				break;
			case RETURN_RANDOM:
				if(entities.size() > 0) {
					entity = entities.get(random.nextInt(entities.size()));
				}

				break;
		}

		for(CRUDOnShowByHook<ID, Entity> onHook : onHooks) {
			onHook.run(entity);
		}

		return entity;
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity showInternal(ID id, Class<Entity> clazz,
			HooksDTO<CRUDPreShowHook<ID, Entity>, CRUDOnShowHook<ID, Entity>, CRUDPostShowHook<ID, Entity>> hooks, boolean fromCache, Boolean persistCopy, DataAccessorDTO accessorDTO) {

		List<ShowHooks> showHooksList = crudHelper.getHooks(ShowHooks.class, clazz);

		if(showHooksList != null && !showHooksList.isEmpty()) {
			for(ShowHooks<ID, Entity> showHooks : showHooksList) {
				hooks.getPreHooks().add(0, showHooks::preShow);
				hooks.getOnHooks().add(0, showHooks::onShow);
				hooks.getPostHooks().add(0, showHooks::postShow);
			}
		}

		for(CRUDPreShowHook<ID, Entity> preHook : hooks.getPreHooks()) {
			preHook.run(id);
		}

		org.springframework.cache.Cache cache = null;
		if(fromCache) {
			cache = crudHelper.getEntityCache(clazz);
		}

		Entity entity = (Entity) CacheUtils.getObjectAndCache(() -> crudReadHandlerProxy.showTransactional(id, clazz, hooks.getOnHooks(), persistCopy, accessorDTO), BaseCrudEntity.Companion.getCacheKey(clazz, id), cache);

		for(CRUDPostShowHook<ID, Entity> postHook : hooks.getPostHooks()) {
			postHook.run(entity);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity showTransactional(ID id, Class<Entity> clazz, List<CRUDOnShowHook<ID, Entity>> onHooks, Boolean persistCopy, DataAccessorDTO accessorDTO) {
		Entity entity = crudHelper.getEntityById(id, clazz, persistCopy, accessorDTO, false);

		for(CRUDOnShowHook<ID, Entity> onHook : onHooks) {
			onHook.run(entity);
		}

		return entity;
	}
}
