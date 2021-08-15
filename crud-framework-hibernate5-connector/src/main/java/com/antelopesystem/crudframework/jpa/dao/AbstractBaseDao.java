package com.antelopesystem.crudframework.jpa.dao;

import com.antelopesystem.crudframework.jpa.annotation.CrudJoinType;
import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.model.PersistentEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import com.antelopesystem.crudframework.modelfilter.FilterField;
import com.antelopesystem.crudframework.modelfilter.JpaRawJunctionDTO;
import com.antelopesystem.crudframework.modelfilter.OrderDTO;
import com.antelopesystem.crudframework.modelfilter.enums.FilterFieldOperation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

public abstract class AbstractBaseDao implements BaseDao {

	@PersistenceContext
	protected EntityManager entityManager;

	private static final Set<Class<?>> PRIMITIVE_AND_BOXED_TYPES = new HashSet<>();

	static {
		PRIMITIVE_AND_BOXED_TYPES.add(boolean.class);
		PRIMITIVE_AND_BOXED_TYPES.add(byte.class);
		PRIMITIVE_AND_BOXED_TYPES.add(short.class);
		PRIMITIVE_AND_BOXED_TYPES.add(char.class);
		PRIMITIVE_AND_BOXED_TYPES.add(int.class);
		PRIMITIVE_AND_BOXED_TYPES.add(long.class);
		PRIMITIVE_AND_BOXED_TYPES.add(float.class);
		PRIMITIVE_AND_BOXED_TYPES.add(double.class);
		PRIMITIVE_AND_BOXED_TYPES.add(Boolean.class);
		PRIMITIVE_AND_BOXED_TYPES.add(Character.class);
		PRIMITIVE_AND_BOXED_TYPES.add(Byte.class);
		PRIMITIVE_AND_BOXED_TYPES.add(Short.class);
		PRIMITIVE_AND_BOXED_TYPES.add(String.class);
		PRIMITIVE_AND_BOXED_TYPES.add(Integer.class);
		PRIMITIVE_AND_BOXED_TYPES.add(Long.class);
		PRIMITIVE_AND_BOXED_TYPES.add(Float.class);
		PRIMITIVE_AND_BOXED_TYPES.add(Double.class);
	}

	protected Session getCurrentSession() {
		return entityManager.unwrap(Session.class);
	}

	/**
	 * Return entity by given class and UUID.
	 *
	 * @param clazz required class
	 * @param id unique identificator
	 * @return entity with given class and UUID.
	 */
	public <T extends BaseCrudEntity<?>> T findObject(Class<T> clazz, Serializable id) {
		return (T) getCurrentSession().get(clazz, id);
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
			getCurrentSession().delete(object);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T saveOrUpdate(T object) {
		getCurrentSession().saveOrUpdate(object);
		return object;
	}

	/**
	 * Persist session cache.
	 */
	@Override
	public <T> void persist(T object) {
		getCurrentSession().persist(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void flush() {
		getCurrentSession().flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		getCurrentSession().clear();
	}

	/**
	 * Set start and limit to criteria if it is given
	 *
	 * @param criteria target criteria
	 * @param start first result starting from 0
	 * @param limit maximum of selected items
	 */
	protected void setBoundaries(Criteria criteria, Integer start, Integer limit) {
		if(criteria == null) {
			return;
		}

		if(null != start) {
			criteria.setFirstResult(start);
		}

		if(null != limit) {
			criteria.setMaxResults(limit);
		}
	}

	protected AliasAwareCriteriaDTO buildCriteria(DynamicModelFilter dynamicModelFilter, Class clazz) {
		Criteria criteria = getCurrentSession().createCriteria(clazz);

		List<String> aliases = new ArrayList<>();

		if(dynamicModelFilter != null) {
			Set<String> flatFilterFieldNames = new HashSet<>();

			if(dynamicModelFilter.getFilterFields() != null && !dynamicModelFilter.getFilterFields().isEmpty()) {
				flattenFilterFieldNames(flatFilterFieldNames, dynamicModelFilter.getFilterFields());
				for(FilterField filterField : dynamicModelFilter.getFilterFields()) {
					criteria.add(buildCriterion(filterField));
				}
			}

			createAliases(clazz, criteria, aliases, null, flatFilterFieldNames);
		}

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return new AliasAwareCriteriaDTO(criteria, aliases);
	}

	private void flattenFilterFieldNames(Set<String> flatFilterFieldNames, List<FilterField> filterFields) {
		for(FilterField filterField : filterFields) {
			if(filterField.getFieldName() != null) {
				flatFilterFieldNames.add(filterField.getFieldName());
			}

			if(filterField.getOperation() == FilterFieldOperation.RawJunction && filterField.getValue1() instanceof JpaRawJunctionDTO) {
				flatFilterFieldNames.addAll(((JpaRawJunctionDTO) filterField.getValue1()).getRequestedAliases());
			}

			if(filterField.getChildren() != null && !filterField.getChildren().isEmpty()) {
				flattenFilterFieldNames(flatFilterFieldNames, filterField.getChildren());
			}
		}
	}

	private Criterion buildCriterion(FilterField filterField) {

		Junction junction = Restrictions.and();

		if((filterField.getOperation() == FilterFieldOperation.RawJunction || filterField.getOperation() == FilterFieldOperation.Noop) || filterField.getChildren() != null || isValidSimpleFilterField(filterField)) {

			switch(filterField.getOperation()) {
				case Equal:
					junction.add(Restrictions.eq(filterField.getFieldName(), filterField.getValue1()));
					break;
				case NotEqual:
					junction.add(Restrictions.not(Restrictions.eq(filterField.getFieldName(), filterField.getValue1())));
					break;
				case In:
					junction.add(Restrictions.in(filterField.getFieldName(), filterField.getValues()));
					break;
				case NotIn:
					junction.add(Restrictions.not(Restrictions.in(filterField.getFieldName(), filterField.getValues())));
					break;
				case GreaterEqual:
					junction.add(Restrictions.ge(filterField.getFieldName(), filterField.getValue1()));
					break;
				case GreaterThan:
					junction.add(Restrictions.gt(filterField.getFieldName(), filterField.getValue1()));
					break;
				case LowerEqual:
					junction.add(Restrictions.le(filterField.getFieldName(), filterField.getValue1()));
					break;
				case LowerThan:
					junction.add(Restrictions.lt(filterField.getFieldName(), filterField.getValue1()));
					break;
				case Between:
					junction.add(Restrictions.between(filterField.getFieldName(), filterField.getValue1(), filterField.getValue2()));
					break;
				case Contains:
					if(filterField.getValue1() != null && !filterField.getValue1().toString().trim().isEmpty()) {
						String valueString = filterField.getValue1().toString();
						junction.add(Restrictions.like(filterField.getFieldName(), replaceSpecialCharacters(valueString), MatchMode.ANYWHERE).ignoreCase());
					}
					break;
				case StartsWith:
					if(filterField.getValue1() != null && !filterField.getValue1().toString().trim().isEmpty()) {
						String valueString = filterField.getValue1().toString();
						junction.add(Restrictions.like(filterField.getFieldName(), replaceSpecialCharacters(valueString), MatchMode.START).ignoreCase());
					}
					break;
				case EndsWith:
					if(filterField.getValue1() != null && !filterField.getValue1().toString().trim().isEmpty()) {
						String valueString = filterField.getValue1().toString();
						junction.add(Restrictions.like(filterField.getFieldName(), replaceSpecialCharacters(valueString), MatchMode.END).ignoreCase());
					}
					break;
				case IsNull:
					junction.add(Restrictions.isNull(filterField.getFieldName()));
					break;
				case IsNotNull:
					junction.add(Restrictions.isNotNull(filterField.getFieldName()));
					break;
				case And:
					if(filterField.getChildren() != null && !filterField.getChildren().isEmpty()) {
						Conjunction conjunction = Restrictions.and();
						for(FilterField child : filterField.getChildren()) {
							conjunction.add(buildCriterion(child));
						}

						junction.add(conjunction);
					}
					break;
				case Or:
					if(filterField.getChildren() != null && !filterField.getChildren().isEmpty()) {
						Disjunction disjunction = Restrictions.or();
						for(FilterField child : filterField.getChildren()) {
							disjunction.add(buildCriterion(child));
						}

						junction.add(disjunction);
					}
					break;
				case Not:
					if(filterField.getChildren() != null && !filterField.getChildren().isEmpty()) {
						FilterField child = filterField.getChildren().get(0);
						junction.add(Restrictions.not(buildCriterion(child)));
					}
					break;
				case ContainsIn:
					Disjunction containsInJunction = Restrictions.or();
					if(filterField.getValues() != null && filterField.getValues().length > 0) {
						for(Object value : filterField.getValues()) {
							if(value != null && !value.toString().trim().isEmpty()) {
								containsInJunction.add(Restrictions.like(filterField.getFieldName(), value.toString(), MatchMode.ANYWHERE).ignoreCase());
							}
						}
					}
					junction.add(containsInJunction);
					break;
				case NotContainsIn:
					Disjunction notContainsInJunction = Restrictions.or();
					if(filterField.getValues() != null && filterField.getValues().length > 0) {
						for(Object value : filterField.getValues()) {
							if(value != null && !value.toString().trim().isEmpty()) {
								notContainsInJunction.add(Restrictions.like(filterField.getFieldName(), value.toString(), MatchMode.ANYWHERE).ignoreCase());
							}
						}
					}
					junction.add(Restrictions.not(notContainsInJunction));
					break;

				case RawJunction:
					JpaRawJunctionDTO dto = (JpaRawJunctionDTO) filterField.getValue1();
					if(dto != null && dto.getJunction() != null) {
						junction.add(dto.getJunction());
					}
					break;
				case Noop:
					junction.add(Restrictions.sqlRestriction("1=0"));
					break;
			}
		}

		return junction;
	}

	private boolean isValidSimpleFilterField(FilterField filterField) {
		return filterField.getFieldName() != null && !filterField.getFieldName().trim().isEmpty() &&
				filterField.getOperation() != null && areValuesValid(filterField);
	}

	private void createAliases(Class clazz, Criteria criteria, List<String> aliases, String existingAlias, Set<String> flatFilterFieldNames) {
		for(Field field : clazz.getDeclaredFields()) {
			Class type = field.getType();
			boolean isCollection = false;
			if(Collection.class.isAssignableFrom(type)) {
				type = (Class<?>) (((ParameterizedTypeImpl) field.getGenericType()).getActualTypeArguments())[0];
				isCollection = true;
			}

			CrudJoinType crudJoinType = field.getDeclaredAnnotation(CrudJoinType.class);

			String aliasPath;
			if(existingAlias != null) {
				aliasPath = existingAlias + "." + field.getName();
			} else {
				aliasPath = field.getName();
			}

			// Is the field we're dealing with an element collection?
			if(isCollection && (PRIMITIVE_AND_BOXED_TYPES.contains(type) || Enum.class.isAssignableFrom(type))) {
				if(flatFilterFieldNames.stream().anyMatch(flatName -> flatName.equals(aliasPath + ".elements"))) {
					criteria.createAlias(aliasPath, aliasPath);
					aliases.add(aliasPath.replace(".", "/"));
				}
			} else if(PersistentEntity.class.isAssignableFrom(type)) { // Or an entity?
				/**
				 * We use slashes because hibernate replaces them with _, {@see org.hibernate.internal.util.StringHelper}
				 */

				String newAlias = aliasPath.replace(".", "/");

				if(flatFilterFieldNames.stream().anyMatch(name -> name.startsWith(newAlias + ".") || name.startsWith(newAlias + "/"))) {
					if(crudJoinType != null) {
						criteria.createAlias(aliasPath, newAlias, crudJoinType.joinType());
					} else {
						criteria.createAlias(aliasPath, newAlias);
					}
					aliases.add(aliasPath.replace(".", "/"));
					createAliases(type, criteria, aliases, aliasPath, flatFilterFieldNames);
				}
			}
		}
	}

	private boolean areValuesValid(FilterField filterField) {
		List<FilterFieldOperation> simpleOperationsWithoutValue = Arrays.asList(FilterFieldOperation.IsNotNull, FilterFieldOperation.IsNull);
		if(simpleOperationsWithoutValue.contains(filterField.getOperation())) {
			return true;
		} else {
			return filterField.getValues() != null && filterField.getValues().length > 0;
		}
	}

	protected long getCriteriaCount(Criteria criteria) {
		if(criteria == null) {
			return 0;
		}
		Object count = criteria.setProjection(Projections.rowCount()).uniqueResult();

		return count == null ? 0L : (long) count;
	}

	protected Criteria setOrder(Criteria criteria, DynamicModelFilter modelFilter) {
		Set<OrderDTO> orders = modelFilter.getOrders();
		boolean appliedOrder = false;
		for (OrderDTO order : orders) {
			if(order.getBy() == null || order.getBy().trim().isEmpty()) {
				continue;
			}
			if(order.getDescending()) {
				criteria.addOrder(Order.desc(order.getBy()));
			} else {
				criteria.addOrder(Order.asc(order.getBy()));
			}
			appliedOrder = true;
		}
		if(!appliedOrder) {
			criteria.addOrder(Order.desc("id"));
		}

		return criteria;
	}

	protected String replaceSpecialCharacters(String string) {
		return string.replace("_", "\\_").replace("%", "\\%");
	}
}
