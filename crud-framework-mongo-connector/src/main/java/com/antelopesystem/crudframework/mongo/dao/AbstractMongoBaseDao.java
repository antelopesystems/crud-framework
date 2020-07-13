package com.antelopesystem.crudframework.mongo.dao;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.*;
import com.antelopesystem.crudframework.modelfilter.enums.FilterFieldOperation;
import com.antelopesystem.crudframework.mongo.modelfilter.MongoRawJunctionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Pattern;

public abstract class AbstractMongoBaseDao {

	@Autowired
	protected MongoTemplate mongoTemplate;

	private static final Set<Class<?>> primitiveAndBoxedTypes = new HashSet<>();

	static {
		primitiveAndBoxedTypes.add(boolean.class);
		primitiveAndBoxedTypes.add(byte.class);
		primitiveAndBoxedTypes.add(short.class);
		primitiveAndBoxedTypes.add(char.class);
		primitiveAndBoxedTypes.add(int.class);
		primitiveAndBoxedTypes.add(long.class);
		primitiveAndBoxedTypes.add(float.class);
		primitiveAndBoxedTypes.add(double.class);
		primitiveAndBoxedTypes.add(Boolean.class);
		primitiveAndBoxedTypes.add(Character.class);
		primitiveAndBoxedTypes.add(Byte.class);
		primitiveAndBoxedTypes.add(Short.class);
		primitiveAndBoxedTypes.add(String.class);
		primitiveAndBoxedTypes.add(Integer.class);
		primitiveAndBoxedTypes.add(Long.class);
		primitiveAndBoxedTypes.add(Float.class);
		primitiveAndBoxedTypes.add(Double.class);
	}

	/**
	 * Return entity by given class and UUID.
	 *
	 * @param clazz required class
	 * @param id unique identificator
	 * @return entity with given class and UUID.
	 */
	public <T extends BaseCrudEntity> T findObject(Class<T> clazz, Serializable id) {
		Criteria criteria = Criteria.where("id").is(id);
		return mongoTemplate.findOne(new Query().addCriteria(criteria), clazz);
	}

	/**
	 * Return entity list by given class and a list of UUIDs.
	 *
	 * @param clazz required class
	 * @param ids a list of UUIDs
	 * @return a list of entities with given class and matching UUID
	 */
	public <T extends BaseCrudEntity> List<T> findObjectByIds(Class<T> clazz, Serializable ids[]) {
		Criteria criteria = Criteria.where("id").in(ids);
		return mongoTemplate.find(new Query().addCriteria(criteria), clazz);
	}

	/**
	 * Delete entity with given class and UUID from DB
	 *
	 * @param clazz required class
	 * @param id unique identificator
	 */
	public void deleteObject(Class clazz, Serializable id) {
		deleteObject(findObject(clazz, id));
	}

	/**
	 * Delete entity from DB.
	 *
	 * @param object entity for removing.
	 */
	public void deleteObject(Object object) {
		if(null != object) {
			mongoTemplate.remove(object);
		}
	}

	protected Query buildQuery(DynamicModelFilter filter) {
		Query query = new Query();

		if(filter != null) {
			if(filter.getFilterFields() != null && !filter.getFilterFields().isEmpty()) {
				for(FilterField filterField : filter.getFilterFields()) {
					query.addCriteria(buildCriterion(filterField));

				}
			}

		}
		return query;
	}


	private Criteria buildCriterion(FilterField filterField) {
		Criteria criteria = new Criteria();

		if(filterField.getOperation() == FilterFieldOperation.RawJunction || filterField.getChildren() != null || isValidSimpleFilterField(filterField)) {

			switch(filterField.getOperation()) {
				case Equal:
					criteria.andOperator(Criteria.where(filterField.getFieldName()).is(filterField.getValue1()));
					break;
				case NotEqual:
					criteria.andOperator(Criteria.where(filterField.getFieldName()).ne(filterField.getValue1()));
					break;
				case In:
					criteria.andOperator(Criteria.where(filterField.getFieldName()).in(filterField.getValues()));
					break;
				case NotIn:
					criteria.andOperator(Criteria.where(filterField.getFieldName()).nin(filterField.getValues()));
					break;
				case GreaterEqual:
					criteria.andOperator(Criteria.where(filterField.getFieldName()).gte(filterField.getValue1()));
					break;
				case GreaterThan:
					criteria.andOperator(Criteria.where(filterField.getFieldName()).gt(filterField.getValue1()));
					break;
				case LowerEqual:
					criteria.andOperator(Criteria.where(filterField.getFieldName()).lte(filterField.getValue1()));
					break;
				case LowerThan:
					criteria.andOperator(Criteria.where(filterField.getFieldName()).lt(filterField.getValue1()));
					break;
				case Between:
					criteria.andOperator(
							Criteria
									.where(filterField.getFieldName())
									.gte(filterField.getValue1())
									.and(filterField.getFieldName())
									.lte(filterField.getValue2()));
					break;
				case Contains:
					if(filterField.getValue1() != null && !filterField.getValue1().toString().trim().isEmpty()) {
						criteria.andOperator(
								Criteria.where(filterField.getFieldName()).regex(Pattern.compile(filterField.getValue1().toString(), Pattern.LITERAL & Pattern.CASE_INSENSITIVE))
						);
					}
					break;
				case StartsWith:
					if(filterField.getValue1() != null && !filterField.getValue1().toString().trim().isEmpty()) {
						Pattern pattern = Pattern.compile(filterField.getValue1().toString(), Pattern.LITERAL & Pattern.CASE_INSENSITIVE);
						criteria.andOperator(
								Criteria.where(filterField.getFieldName()).regex("^" + pattern.toString())
						);
					}
					break;
				case EndsWith:
					if(filterField.getValue1() != null && !filterField.getValue1().toString().trim().isEmpty()) {
						Pattern pattern = Pattern.compile(filterField.getValue1().toString(), Pattern.LITERAL & Pattern.CASE_INSENSITIVE);
						criteria.andOperator(
								Criteria.where(filterField.getFieldName()).regex(pattern.toString() + "$")
						);
					}
					break;
				case IsNull:
					criteria.andOperator(new Criteria().orOperator(
							Criteria.where(filterField.getFieldName()).exists(false),
							Criteria.where(filterField.getFieldName()).is(null)
					));
					break;
				case IsNotNull:
					criteria.andOperator(Criteria.where(filterField.getFieldName()).ne(null));
					break;
				case And:
					if(filterField.getChildren() != null && !filterField.getChildren().isEmpty()) {
						List<Criteria> criterias = new ArrayList<>();
						for(FilterField child : filterField.getChildren()) {
							criterias.add(buildCriterion(child));
						}

						criteria.andOperator(criterias.toArray(new Criteria[]{}));
					}
					break;
				case Or:
					if(filterField.getChildren() != null && !filterField.getChildren().isEmpty()) {
						List<Criteria> criterias = new ArrayList<>();
						for(FilterField child : filterField.getChildren()) {
							criterias.add(buildCriterion(child));
						}

						criteria.orOperator(criterias.toArray(new Criteria[]{}));
					}
					break;
				case Not:
					if(filterField.getChildren() != null && !filterField.getChildren().isEmpty()) {
						FilterField child = filterField.getChildren().get(0);
						criteria.not().andOperator(buildCriterion(child));
					}
					break;
				case ContainsIn:
					if(filterField.getValues() != null && filterField.getValues().length > 0) {
						List<Criteria> criterias = new ArrayList<>();
						for(Object value : filterField.getValues()) {
							if(value != null && !value.toString().trim().isEmpty()) {
								criterias.add(new Criteria()
										.andOperator(
												Criteria.where(filterField.getFieldName()).regex(Pattern.compile(value.toString(), Pattern.LITERAL & Pattern.CASE_INSENSITIVE))
										));
							}
						}

						criteria.andOperator(criterias.toArray(new Criteria[]{}));
					}
					break;
				case NotContainsIn:
					if(filterField.getValues() != null && filterField.getValues().length > 0) {
						List<Criteria> criterias = new ArrayList<>();
						for(Object value : filterField.getValues()) {
							if(value != null && !value.toString().trim().isEmpty()) {
								criterias.add(new Criteria()
										.andOperator(
												Criteria.where(filterField.getFieldName()).regex(Pattern.compile(value.toString(), Pattern.LITERAL & Pattern.CASE_INSENSITIVE))
										));
							}
						}

						criteria.not().andOperator(criterias.toArray(new Criteria[]{}));
					}
					break;

				case RawJunction:
					MongoRawJunctionDTO dto = (MongoRawJunctionDTO) filterField.getValue1();
					if(dto != null && dto.getJunction() != null) {
						criteria.andOperator(dto.getJunction());
					}
					break;
			}
		}

		return criteria;
	}

	private boolean isValidSimpleFilterField(FilterField filterField) {
		return filterField.getFieldName() != null && !filterField.getFieldName().trim().isEmpty() &&
				filterField.getOperation() != null && areValuesValid(filterField);
	}

	private boolean areValuesValid(FilterField filterField) {
		List<FilterFieldOperation> simpleOperationsWithoutValue = Arrays.asList(FilterFieldOperation.IsNotNull, FilterFieldOperation.IsNull);
		if(simpleOperationsWithoutValue.contains(filterField.getOperation())) {
			return true;
		} else {
			return filterField.getValues() != null && filterField.getValues().length > 0;
		}
	}

	protected void setOrder(Query query, String orderBy, boolean orderDesc) {
		if(orderBy != null && !orderBy.trim().isEmpty()) {
			query.with(Sort.by(
					orderDesc ? Sort.Direction.DESC : Sort.Direction.ASC,
					orderBy
			));
		} else {
			query.with(Sort.by(
					orderDesc ? Sort.Direction.DESC : Sort.Direction.ASC,
					"id"
			));
		}
	}

	protected void setBoundaries(Query query, Integer start, Integer limit) {
		if(start != null) {
			query.skip(start);
		}

		if(limit != null) {
			query.limit(limit);
		}
	}

	protected String replaceSpecialCharacters(String string) {
		return string.replace("_", "\\_").replace("%", "\\%");
	}
}
