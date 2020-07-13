package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.dataaccess.DataAccessManager;
import com.antelopesystem.crudframework.crud.dataaccess.model.DataAccessorDTO;
import com.antelopesystem.crudframework.crud.decorator.ObjectDecorator;
import com.antelopesystem.crudframework.crud.exception.CRUDException;
import com.antelopesystem.crudframework.crud.model.EntityMetadataDTO;
import com.antelopesystem.crudframework.exception.tree.core.ErrorCode;
import com.antelopesystem.crudframework.exception.tree.core.ExceptionOverride;
import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;

import java.io.Serializable;
import java.util.List;

public interface CrudHelper {

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> boolean isEntityDeleted(Entity entity);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> void decorateFilter(DynamicModelFilter filter, Class<Entity> entityClazz, DataAccessorDTO accessorDTO, boolean forUpdate);

	/* transactional */
	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> List<Entity> getEntities(DynamicModelFilter filter, Class<Entity> entityClazz, DataAccessorDTO accessorDTO, Boolean persistCopy, boolean forUpdate);

	/* transactional */
	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> long getEntitiesCount(DynamicModelFilter filter, Class<Entity> entityClazz, DataAccessorDTO accessorDTO, boolean forUpdate);

	/* transactional */
	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity getEntityById(ID entityId, Class<Entity> entityClazz, Boolean persistCopy, DataAccessorDTO accessorDTO, boolean forUpdate);

	/* transactional */
	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> long getEntityCountById(ID entityId, Class<Entity> entityClazz, DataAccessorDTO accessorDTO, boolean forUpdate);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> DataAccessManager getAccessorManager(Class<?> accessorClazz, Class<Entity> entityClazz);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> void checkEntityImmutability(Class<Entity> clazz);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> void checkEntityDeletability(Class<Entity> clazz);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> EntityMetadataDTO getEntityMetadata(Class<Entity> entityClazz);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> void evictEntityFromCache(Entity entity);

	<Entity, E> ObjectDecorator<Entity, E> getObjectDecorator(Class<Entity> fromClass, Class<E> toClass);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> org.springframework.cache.Cache getEntityCache(Class<Entity> clazz);

	@ExceptionOverride(value = CRUDException.class, errorCode = ErrorCode.ValidationError)
	void validate(Object target);

	@ExceptionOverride(value = CRUDException.class, errorCode = ErrorCode.ROGenerationError)
	<Entity, RO> RO getRO(Entity fromObject, Class<RO> toClazz);

	@ExceptionOverride(value = CRUDException.class, errorCode = ErrorCode.ROGenerationError)
	<Entity, RO> List<RO> getROs(List<Entity> fromObjects, Class<RO> toClazz);

	@ExceptionOverride(value = CRUDException.class, errorCode = ErrorCode.FillError)
	<Entity, RO> RO fill(Entity fromObject, Class<RO> toClazz);

	@ExceptionOverride(value = CRUDException.class, errorCode = ErrorCode.FillError)
	<Entity, RO> void fill(Entity fromObject, RO toObject);

	<Entity> void setTotalToPagingCache(Class<Entity> entityClazz, DynamicModelFilter filter, long total);

	<Entity> Long getTotalFromPagingCache(Class<Entity> entityClazz, DynamicModelFilter filter);

	<ID extends Serializable, Entity extends BaseCrudEntity<ID>> CrudDao getCrudDaoForEntity(Class<Entity> entityClazz);
}
