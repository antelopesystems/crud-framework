package com.antelopesystem.crudframework.jpa.dao;

import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.ResultTransformer;

import java.util.List;

/**
 * Created by Shani on 05/03/2018.
 */
public class AliasAwareCriteriaDTO implements Criteria {

	private Criteria criteria;

	private List<String> aliases;

	public AliasAwareCriteriaDTO() {
	}

	public AliasAwareCriteriaDTO(Criteria criteria, List<String> aliases) {
		this.criteria = criteria;
		this.aliases = aliases;
	}

	public Criteria getInnerCriteria() {
		return criteria;
	}

	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public Criteria addQueryHint(String s) {
		return null;
	}

	@Override
	public String getAlias() {
		return criteria.getAlias();
	}

	@Override
	public Criteria setProjection(Projection projection) {
		return criteria.setProjection(projection);
	}

	@Override
	public Criteria add(Criterion criterion) {
		return criteria.add(criterion);
	}

	@Override
	public Criteria addOrder(Order order) {
		return criteria.addOrder(order);
	}

	@Override
	public Criteria setFetchMode(String associationPath, FetchMode mode) throws HibernateException {
		return criteria.setFetchMode(associationPath, mode);
	}

	@Override
	public Criteria setLockMode(LockMode lockMode) {
		return criteria.setLockMode(lockMode);
	}

	@Override
	public Criteria setLockMode(String alias, LockMode lockMode) {
		return criteria.setLockMode(alias, lockMode);
	}

	@Override
	public Criteria createAlias(String associationPath, String alias) throws HibernateException {
		return criteria.createAlias(associationPath, alias);
	}

	@Override
	public Criteria createAlias(String associationPath, String alias, JoinType joinType) throws HibernateException {
		return criteria.createAlias(associationPath, alias, joinType);
	}

	@Override
	public Criteria createAlias(String associationPath, String alias, int joinType) throws HibernateException {
		return criteria.createAlias(associationPath, alias, joinType);
	}

	@Override
	public Criteria createAlias(String associationPath, String alias, JoinType joinType, Criterion withClause) throws HibernateException {
		return criteria.createAlias(associationPath, alias, joinType, withClause);
	}

	@Override
	public Criteria createAlias(String associationPath, String alias, int joinType, Criterion withClause) throws HibernateException {
		return criteria.createAlias(associationPath, alias, joinType, withClause);
	}

	@Override
	public Criteria createCriteria(String associationPath) throws HibernateException {
		return criteria.createCriteria(associationPath);
	}

	@Override
	public Criteria createCriteria(String associationPath, JoinType joinType) throws HibernateException {
		return criteria.createCriteria(associationPath, joinType);
	}

	@Override
	public Criteria createCriteria(String associationPath, int joinType) throws HibernateException {
		return criteria.createCriteria(associationPath, joinType);
	}

	@Override
	public Criteria createCriteria(String associationPath, String alias) throws HibernateException {
		return criteria.createCriteria(associationPath, alias);
	}

	@Override
	public Criteria createCriteria(String associationPath, String alias, JoinType joinType) throws HibernateException {
		return criteria.createCriteria(associationPath, alias, joinType);
	}

	@Override
	public Criteria createCriteria(String associationPath, String alias, int joinType) throws HibernateException {
		return criteria.createCriteria(associationPath, alias, joinType);
	}

	@Override
	public Criteria createCriteria(String associationPath, String alias, JoinType joinType, Criterion withClause) throws HibernateException {
		return criteria.createCriteria(associationPath, alias, joinType, withClause);
	}

	@Override
	public Criteria createCriteria(String associationPath, String alias, int joinType, Criterion withClause) throws HibernateException {
		return criteria.createCriteria(associationPath, alias, joinType, withClause);
	}

	@Override
	public Criteria setResultTransformer(ResultTransformer resultTransformer) {
		return criteria.setResultTransformer(resultTransformer);
	}

	@Override
	public Criteria setMaxResults(int maxResults) {
		return criteria.setMaxResults(maxResults);
	}

	@Override
	public Criteria setFirstResult(int firstResult) {
		return criteria.setFirstResult(firstResult);
	}

	@Override
	public boolean isReadOnlyInitialized() {
		return criteria.isReadOnlyInitialized();
	}

	@Override
	public boolean isReadOnly() {
		return criteria.isReadOnly();
	}

	@Override
	public Criteria setReadOnly(boolean readOnly) {
		return criteria.setReadOnly(readOnly);
	}

	@Override
	public Criteria setFetchSize(int fetchSize) {
		return criteria.setFetchSize(fetchSize);
	}

	@Override
	public Criteria setTimeout(int timeout) {
		return criteria.setTimeout(timeout);
	}

	@Override
	public Criteria setCacheable(boolean cacheable) {
		return criteria.setCacheable(cacheable);
	}

	@Override
	public Criteria setCacheRegion(String cacheRegion) {
		return criteria.setCacheRegion(cacheRegion);
	}

	@Override
	public Criteria setComment(String comment) {
		return criteria.setComment(comment);
	}

	@Override
	public Criteria setFlushMode(FlushMode flushMode) {
		return criteria.setFlushMode(flushMode);
	}

	@Override
	public Criteria setCacheMode(CacheMode cacheMode) {
		return criteria.setCacheMode(cacheMode);
	}

	@Override
	public List list() throws HibernateException {
		return criteria.list();
	}

	@Override
	public ScrollableResults scroll() throws HibernateException {
		return criteria.scroll();
	}

	@Override
	public ScrollableResults scroll(ScrollMode scrollMode) throws HibernateException {
		return criteria.scroll(scrollMode);
	}

	@Override
	public Object uniqueResult() throws HibernateException {
		return criteria.uniqueResult();
	}
}
