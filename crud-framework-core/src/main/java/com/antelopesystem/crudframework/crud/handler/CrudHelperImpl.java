package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.cache.CacheManagerAdapter;
import com.antelopesystem.crudframework.crud.cache.CacheUtils;
import com.antelopesystem.crudframework.crud.cache.CrudCache;
import com.antelopesystem.crudframework.crud.cache.CrudCacheOptions;
import com.antelopesystem.crudframework.crud.configuration.CrudFrameworkConfiguration;
import com.antelopesystem.crudframework.crud.configuration.properties.CrudFrameworkProperties;
import com.antelopesystem.crudframework.crud.dataaccess.DataAccessManager;
import com.antelopesystem.crudframework.crud.dataaccess.model.DataAccessorDTO;
import com.antelopesystem.crudframework.crud.decorator.ObjectDecorator;
import com.antelopesystem.crudframework.crud.exception.CrudException;
import com.antelopesystem.crudframework.crud.exception.CrudInvalidStateException;
import com.antelopesystem.crudframework.crud.exception.CrudTransformationException;
import com.antelopesystem.crudframework.crud.exception.CrudValidationException;
import com.antelopesystem.crudframework.crud.hooks.interfaces.CRUDHooks;
import com.antelopesystem.crudframework.crud.model.EntityCacheMetadata;
import com.antelopesystem.crudframework.crud.model.EntityMetadataDTO;
import com.antelopesystem.crudframework.exception.WrapException;
import com.antelopesystem.crudframework.exception.dto.ErrorField;
import com.antelopesystem.crudframework.fieldmapper.FieldMapper;
import com.antelopesystem.crudframework.fieldmapper.transformer.DateToLongTransformer;
import com.antelopesystem.crudframework.fieldmapper.transformer.LongToDateTransformer;
import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformer;
import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import com.antelopesystem.crudframework.modelfilter.FilterField;
import com.antelopesystem.crudframework.modelfilter.FilterFields;
import com.antelopesystem.crudframework.modelfilter.enums.FilterFieldDataType;
import com.antelopesystem.crudframework.modelfilter.enums.FilterFieldOperation;
import com.antelopesystem.crudframework.utils.component.componentmap.annotation.ComponentMap;
import com.antelopesystem.crudframework.utils.utils.FieldUtils;
import com.antelopesystem.crudframework.utils.utils.ReflectionUtils;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class CrudHelperImpl implements CrudHelper {

	private FieldMapper fieldMapper = new FieldMapper();

	@ComponentMap
	private Map<String, ObjectDecorator> objectDecoratorMap;

	private Map<String, CrudCache> cacheMap = new HashMap<>();

	@ComponentMap
	private Map<String, DataAccessManager> dataAccessManagers;

	private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Autowired(required = false)
	private List<CrudDao> crudDaos = new ArrayList<>();

	private Map<Class<? extends BaseCrudEntity<?>>, CrudDao> crudDaoMap = new HashMap<>();

	@Resource(name = "crudHelper")
	private CrudHelper crudHelperProxy;

	@Autowired
	private ApplicationContext applicationContext;

	private Map<Class<? extends BaseCrudEntity<?>>, EntityMetadataDTO> entityMetadataDTOs = new HashMap<>();

	private CrudCache pagingCache;

	@Autowired
	private CacheManagerAdapter cacheManagerAdapter;

	@Autowired(required = false)
	private Map<String, FieldTransformer> fieldTransformers = new HashMap<>();

	@Autowired
	private CrudFrameworkProperties properties;

	@PostConstruct
	private void init() {
		initFieldMapper();
		pagingCache = cacheManagerAdapter.createCache("pagingCache",
				new CrudCacheOptions(
						60L,
						60L,
						10000L
				));
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>, HooksType extends CRUDHooks> List<HooksType> getHooks(Class<HooksType> crudHooksClazz, Class<Entity> entityClazz) {
		EntityMetadataDTO metadataDTO = getEntityMetadata(entityClazz);
	 	Set<HooksType> matchingAnnotationHooks = (Set<HooksType>) metadataDTO.getHooksFromAnnotations()
				.stream()
				.filter(hook -> crudHooksClazz.isAssignableFrom(hook.getClass()))
				.collect(Collectors.toSet());
		List<HooksType> hooks = applicationContext.getBeansOfType(crudHooksClazz).values()
				.stream()
				.filter(c -> c.getType() == entityClazz)
				.collect(Collectors.toList());
		hooks.addAll(matchingAnnotationHooks);
		return hooks;
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> boolean isEntityDeleted(Entity entity) {
		if(entity == null) {
			return true;
		}

		Class<Entity> clazz = (Class<Entity>) entity.getClass();

		EntityMetadataDTO metadataDTO = getEntityMetadata(clazz);

		if(metadataDTO.getDeleteableType() == EntityMetadataDTO.DeleteableType.Hard) {
			return false;
		}

		if(metadataDTO.getDeleteField() == null) {
			return false;
		}

		ReflectionUtils.makeAccessible(metadataDTO.getDeleteField());
		return (boolean) ReflectionUtils.getField(metadataDTO.getDeleteField(), entity);
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> void decorateFilter(DynamicModelFilter filter, Class<Entity> entityClazz, DataAccessorDTO accessorDTO, boolean forUpdate) {
		EntityMetadataDTO metadataDTO = getEntityMetadata(entityClazz);
		if(metadataDTO.getDeleteableType() == EntityMetadataDTO.DeleteableType.Soft) {
			Field deleteField = metadataDTO.getDeleteField();
			if(deleteField != null) {
				filter.add(FilterFields.eq(deleteField.getName(), FilterFieldDataType.Boolean, false));
			}
		}

		if(accessorDTO != null) {
			DataAccessManager dataAccessManager = getAccessorManager(accessorDTO.getAccessorClazz(), entityClazz);
			if(dataAccessManager != null) {
				if(forUpdate) {
					dataAccessManager.decorateUpdateOperation(filter, accessorDTO.getAccessorId(), accessorDTO.getAccessorClazz());
				} else {
					dataAccessManager.decorateViewOperation(filter, accessorDTO.getAccessorId(), accessorDTO.getAccessorClazz());
				}
			}
		}

		validateAndFillFilterFieldMetadata(filter.getFilterFields(), metadataDTO);
	}

	@Override
	public void validateAndFillFilterFieldMetadata(List<FilterField> filterFields, EntityMetadataDTO metadataDTO) {
		for(FilterField filterField : filterFields) {
			if(filterField.isValidated()) {
				continue;
			}
			if(filterField.getOperation() == null) {
				throw new IllegalStateException("A FilterField must have an operation");
			}

			boolean isJunction = filterField.getOperation() == FilterFieldOperation.And || filterField.getOperation() == FilterFieldOperation.Or || filterField.getOperation() == FilterFieldOperation.Not || filterField.getOperation() == FilterFieldOperation.RawJunction;
			if(isJunction) {
				if(filterField.getChildren() != null && !filterField.getChildren().isEmpty()) {
					validateAndFillFilterFieldMetadata(filterField.getChildren(), metadataDTO);
				}
			} else {
				if(filterField.getFieldName() != null) {
					String fieldName = filterField.getFieldName();
					if(fieldName.endsWith(".elements")) {
						fieldName = fieldName.substring(0, fieldName.lastIndexOf(".elements"));
					}
					if(!metadataDTO.getFields().containsKey(fieldName)) {
						throw new RuntimeException("Cannot filter field [ " + fieldName + " ] as it was not found on entity [ " + metadataDTO.getSimpleName() + " ]");
					}

					Field field = metadataDTO.getFields().get(fieldName);
					Class<?> fieldClazz = field.getType();

					if(Collection.class.isAssignableFrom(field.getType())) {
						Class<?> potentialFieldClazz = FieldUtils.getGenericClass(field, 0);

						if(potentialFieldClazz != null) {
							fieldClazz = potentialFieldClazz;
						}
					}

					FilterFieldDataType fieldDataType = getDataTypeFromClass(fieldClazz);
					filterField.setDataType(fieldDataType);
					if(fieldDataType == FilterFieldDataType.Enum) {
						filterField.setEnumType(fieldClazz.getName());
					}
				}
			}
			filterField.validate();
		}
	}

	private FilterFieldDataType getDataTypeFromClass(Class clazz) {
		if(String.class.equals(clazz)) {
			return FilterFieldDataType.String;
		} else if(int.class.equals(clazz) || Integer.class.equals(clazz)) {
			return FilterFieldDataType.Integer;
		} else if(long.class.equals(clazz) || Long.class.equals(clazz)) {
			return FilterFieldDataType.Long;
		} else if(double.class.equals(clazz) || Double.class.equals(clazz)) {
			return FilterFieldDataType.Double;
		} else if(Date.class.equals(clazz)) {
			return FilterFieldDataType.Date;
		} else if(boolean.class.equals(clazz) || Boolean.class.equals(clazz)) {
			return FilterFieldDataType.Boolean;
		} else if(Enum.class.isAssignableFrom(clazz)) {
			return FilterFieldDataType.Enum;
		}

		return FilterFieldDataType.Object;
	}

	/* transactional */
	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> List<Entity> getEntities(DynamicModelFilter filter, Class<Entity> entityClazz, DataAccessorDTO accessorDTO, Boolean persistCopy,
			boolean forUpdate) {
		decorateFilter(filter, entityClazz, accessorDTO, forUpdate);

		if(persistCopy == null) {
			persistCopy = getEntityMetadata(entityClazz).getAlwaysPersistCopy();
		}

		List<Entity> result = getCrudDaoForEntity(entityClazz).index(filter, entityClazz);
		if(persistCopy) {
			result.forEach(BaseCrudEntity::saveOrGetCopy);
		}

		return result;
	}

	/* transactional */
	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> long getEntitiesCount(DynamicModelFilter filter, Class<Entity> entityClazz, DataAccessorDTO accessorDTO, boolean forUpdate) {
		decorateFilter(filter, entityClazz, accessorDTO, forUpdate);
		return getCrudDaoForEntity(entityClazz).indexCount(filter, entityClazz);
	}

	/* transactional */
	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> Entity getEntityById(ID entityId, Class<Entity> entityClazz, Boolean persistCopy, DataAccessorDTO accessorDTO, boolean forUpdate) {
		FilterFieldDataType entityIdDataType = FilterFieldDataType.get(entityId.getClass());
		Objects.requireNonNull(entityIdDataType, "Could not assert entityId type");

		DynamicModelFilter filter = new DynamicModelFilter()
				.add(FilterFields.eq("id", entityIdDataType, entityId));
		List<Entity> entities = getEntities(filter, entityClazz, accessorDTO, persistCopy, forUpdate);
		Entity entity = null;
		if(entities.size() > 0) {
			entity = entities.get(0);
		}

		return entity;
	}

	/* transactional */
	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> long getEntityCountById(ID entityId, Class<Entity> entityClazz, DataAccessorDTO accessorDTO, boolean forUpdate) {
		FilterFieldDataType entityIdDataType = FilterFieldDataType.get(entityId.getClass());
		Objects.requireNonNull(entityIdDataType, "Could not assert entityId type");

		DynamicModelFilter filter = new DynamicModelFilter()
				.add(FilterFields.eq("id", entityIdDataType, entityId));

		return getEntitiesCount(filter, entityClazz, accessorDTO, forUpdate);
	}


	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> DataAccessManager getAccessorManager(Class<?> accessorClazz, Class<Entity> entityClazz) {
		return dataAccessManagers.get(accessorClazz.getName() + "_" + entityClazz.getName());
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> void checkEntityImmutability(Class<Entity> clazz) {
		EntityMetadataDTO metadataDTO = getEntityMetadata(clazz);
		if(metadataDTO.getImmutable()) {
			throw new CrudInvalidStateException("Entity of type [ " + clazz.getSimpleName() + " ] is immutable");
		}
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> void checkEntityDeletability(Class<Entity> clazz) {
		EntityMetadataDTO metadataDTO = getEntityMetadata(clazz);
		if(metadataDTO.getDeleteableType() == EntityMetadataDTO.DeleteableType.None) {
			throw new CrudInvalidStateException("Entity of type [ " + clazz.getSimpleName() + " ] can not be deleted");
		}

		if(metadataDTO.getDeleteableType() == EntityMetadataDTO.DeleteableType.Soft) {
			if(metadataDTO.getDeleteField() == null) {
				throw new CrudInvalidStateException("Entity of type [ " + clazz.getSimpleName() + " ] is set for soft delete but is missing @DeleteColumn");
			}

			if(!ClassUtils.isAssignable(boolean.class, metadataDTO.getDeleteField().getType())) {
				throw new CrudInvalidStateException("Entity of type [ " + clazz.getSimpleName() + " ] has an invalid @DeleteColumn - column must be of type boolean");
			}
		}
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> EntityMetadataDTO getEntityMetadata(Class<Entity> entityClazz) {
		return entityMetadataDTOs.computeIfAbsent(entityClazz, x -> {
			EntityMetadataDTO metadataDTO = new EntityMetadataDTO(entityClazz);
			for(Class<CRUDHooks<?, ?>> hookType : metadataDTO.getHookTypesFromAnnotations()) {
				try {
					CRUDHooks<ID, Entity> hooks = (CRUDHooks<ID, Entity>) applicationContext.getBean(hookType);
					metadataDTO.getHooksFromAnnotations().add(hooks);
				} catch(BeansException e) {
					throw new CrudInvalidStateException("Could not get bean for persistent hooks class of type [ " + hookType.getCanonicalName() + " ]. Error: " + e.getMessage());
				}
			}
			return metadataDTO;
		});
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> void evictEntityFromCache(Entity entity) {
		Objects.requireNonNull(entity, "entity cannot be null");

		CrudCache cache = crudHelperProxy.getEntityCache(entity.getClass());

		if(cache == null) {
			return;
		}

		CacheUtils.removeFromCacheIfKeyContains(cache, entity.getCacheKey());
	}

	@Override
	public <Entity, E> ObjectDecorator<Entity, E> getObjectDecorator(Class<Entity> fromClass, Class<E> toClass) {
		return objectDecoratorMap.get(fromClass.getName() + "_" + toClass.getName());
	}

	@Override
	@WrapException(CrudException.class)
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> CrudCache getEntityCache(Class<Entity> clazz) {
		if(cacheMap.containsKey(clazz.getName())) {
			return cacheMap.get(clazz.getName());
		}

		EntityMetadataDTO dto = getEntityMetadata(clazz);
		EntityCacheMetadata cacheMetadata = dto.getCacheMetadata();
		if(cacheMetadata == null) {
			cacheMap.put(clazz.getName(), null);
			return null;
		}

		CrudCache cache = cacheManagerAdapter.getCache(cacheMetadata.getName());
		if(cache == null) {
			if(cacheMetadata.getCreateIfMissing()) {
				cache = cacheManagerAdapter.createCache(cacheMetadata.getName(), cacheMetadata.getOptions());
			} else {
				throw new CrudException("Cache for entity [ " + clazz.getSimpleName() + " ] with name [ " + dto.getCacheMetadata().getName() + " ] not found");
			}

		}
		cacheMap.put(clazz.getName(), cache);

		return cache;
	}

	@Override
	@WrapException(CrudValidationException.class)
	public void validate(Object target) {
		Objects.requireNonNull(target, "target cannot be null");
		Set<ConstraintViolation<Object>> violations = validator.validate(target);
		List<ErrorField> errorFields = new ArrayList<>();
		for(ConstraintViolation<Object> violation : violations) {
			errorFields.add(new ErrorField(violation.getPropertyPath().toString(), violation.getMessage(), violation.getConstraintDescriptor().getAttributes()));
		}

		if(!errorFields.isEmpty()) {
			throw new CrudValidationException("Field Validation Failed");
		}
	}

	@Override
	@WrapException(CrudTransformationException.class)
	public <Entity, RO> RO getRO(Entity fromObject, Class<RO> toClazz) {
		Objects.requireNonNull(fromObject, "fromObject cannot be null");
		Objects.requireNonNull(toClazz, "toClazz cannot be null");

		RO toObject = fieldMapper.processMappedFields(fromObject, toClazz);
		ObjectDecorator objectDecorator = getObjectDecorator(fromObject.getClass(), toClazz);
		if(objectDecorator != null) {
			objectDecorator.decorate(fromObject, toObject);
		}

		return toObject;
	}

	@Override
	@WrapException(CrudTransformationException.class)
	public <Entity, RO> List<RO> getROs(List<Entity> fromObjects, Class<RO> toClazz) {
		List<RO> toObjects = new ArrayList<RO>();
		for(Entity fromObject : fromObjects) {
			toObjects.add(crudHelperProxy.getRO(fromObject, toClazz));
		}

		return toObjects;
	}

	@Override
	@WrapException(CrudTransformationException.class)
	public <Entity, RO> RO fill(Entity fromObject, Class<RO> toClazz) {
		Objects.requireNonNull(fromObject, "fromObject cannot be null");
		Objects.requireNonNull(toClazz, "toClazz cannot be null");

		RO toObject = fieldMapper.processMappedFields(fromObject, toClazz);
		ObjectDecorator objectDecorator = getObjectDecorator(fromObject.getClass(), toObject.getClass());
		if(objectDecorator != null) {
			objectDecorator.decorate(fromObject, toObject);
		}

		return toObject;
	}

	@Override
	@WrapException(CrudTransformationException.class)
	public <Entity, RO> void fill(Entity fromObject, RO toObject) {
		Objects.requireNonNull(fromObject, "fromObject cannot be null");
		Objects.requireNonNull(toObject, "toObject cannot be null");

		fieldMapper.processMappedFields(fromObject, toObject);
		ObjectDecorator objectDecorator = getObjectDecorator(fromObject.getClass(), toObject.getClass());
		if(objectDecorator != null) {
			objectDecorator.decorate(fromObject, toObject);
		}
	}

	@Override
	public <Entity> void setTotalToPagingCache(Class<Entity> entityClazz, DynamicModelFilter filter, long total) {
		String cacheKey = entityClazz.getName() + "_" + filter.getFilterFields().hashCode();
		pagingCache.put(cacheKey, total);
	}

	@Override
	public <Entity> Long getTotalFromPagingCache(Class<Entity> entityClazz, DynamicModelFilter filter) {
		String cacheKey = entityClazz.getName() + "_" + filter.getFilterFields().hashCode();
		return (Long) pagingCache.get(cacheKey);
	}

	@Override
	public <ID extends Serializable, Entity extends BaseCrudEntity<ID>> CrudDao getCrudDaoForEntity(Class<Entity> entityClazz) {
		return crudDaoMap.computeIfAbsent(entityClazz, x -> {
			Class entityDaoClazz = getEntityMetadata(entityClazz).getDaoClazz();
			for(CrudDao dao : crudDaos) {
				if(getTrueProxyClass(dao).equals(entityDaoClazz)) {
					return dao;
				}
			}
			return null;
		});
	}

	private void initFieldMapper() {
		for(Map.Entry<String, FieldTransformer> entry : fieldTransformers.entrySet()) {
			fieldMapper.registerTransformer(entry.getKey(), entry.getValue());
		}

		if(properties.getDefaultTransformersEnabled()) {
			fieldMapper.registerDefaultTransformer(new DateToLongTransformer());
			fieldMapper.registerDefaultTransformer(new LongToDateTransformer());
		}
	}

	private <T> Class<T> getTrueProxyClass(T proxy) {
		if(AopUtils.isJdkDynamicProxy(proxy)) {
			try {
				return (Class<T>) ((Advised) proxy).getTargetSource().getTarget().getClass();
			} catch(Exception e) {
				return null;
			}
		} else {
			return (Class<T>) ClassUtils.getUserClass(proxy.getClass());
		}
	}
}
