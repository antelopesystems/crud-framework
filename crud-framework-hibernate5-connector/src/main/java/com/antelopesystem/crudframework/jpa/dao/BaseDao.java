package com.antelopesystem.crudframework.jpa.dao;

import com.antelopesystem.crudframework.jpa.model.BaseJpaEntity;

import java.io.Serializable;

/**
 * Base interface for DAO.
 */
public interface BaseDao {

	/**
	 * Find entity in DB.
	 *
	 * @param clazz class of entity.
	 * @param id UID of entity.
	 * @return found entity
	 */
	<T extends BaseJpaEntity> T findObject(Class<T> clazz, Serializable id);

	/**
	 * Persist entity to DB.
	 *
	 * @param object entity for saving.
	 * @return persisted entity
	 */
	<T> T saveOrUpdate(T object);

	/**
	 * Persist entity to DB. More useful in long-running conversations with an extended Session context.
	 *
	 * @param object entity for saving.
	 */
	<T> void persist(T object);

	/**
	 * Flush session.
	 */
	void flush();

	/**
	 * Clear session cache.
	 */
	void clear();
}
