package com.antelopesystem.crudframework.modelfilter;

import java.io.Serializable;
import java.util.Objects;

public abstract class BaseModelFilter implements Serializable {

	private static final long serialVersionUID = -5774621989094488798L;

	private Integer start;

	private Integer limit;

	private String orderBy = "id";

	private boolean orderDesc = true;

	private String returnColumn;

	public Integer getStart() {
		return start;
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseModelFilter> T setStart(Integer start) {
		this.start = start;
		return (T) this;
	}

	public Integer getLimit() {
		return limit;
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseModelFilter> T setLimit(Integer limit) {
		this.limit = limit;
		return (T) this;
	}

	public String getOrderBy() {
		return orderBy;
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseModelFilter> T setOrderBy(String orderBy) {
		this.orderBy = orderBy;
		return (T) this;
	}

	public boolean getOrderDesc() {
		return orderDesc;
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseModelFilter> T setOrderDesc(Boolean orderDesc) {
		this.orderDesc = orderDesc;
		return (T) this;
	}

	public String getReturnColumn() {
		return returnColumn;
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseModelFilter> T setReturnColumn(String returnColumn) {
		this.returnColumn = returnColumn;
		return (T) this;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof BaseModelFilter)) {
			return false;
		}
		BaseModelFilter that = (BaseModelFilter) o;
		return orderDesc == that.orderDesc &&
				Objects.equals(start, that.start) &&
				Objects.equals(limit, that.limit) &&
				Objects.equals(orderBy, that.orderBy) &&
				Objects.equals(returnColumn, that.returnColumn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(start, limit, orderBy, orderDesc, returnColumn);
	}


	@Override
	public String toString() {
		return "BaseModelFilter [" +
				"start=" + start +
				", limit=" + limit +
				", orderBy=" + orderBy +
				", orderDesc=" + orderDesc +
				", returnColumn=" + returnColumn +
				']';
	}

	public String getCacheKey() {
		return "CacheKey_" + this.getClass().getSimpleName() + "_" + this.hashCode();
	}
}
