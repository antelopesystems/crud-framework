package com.antelopesystem.crudframework.ro;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public class PagingRO implements Serializable {

	// ------------------------ Constants -----------------------
	private static final long serialVersionUID = 1L;

	// ------------------------ Fields --------------------------

	@Nullable
	private Integer start;

	@Nullable
	private Integer limit;

	@Nullable
	private Long total;

	@Nullable
	private Boolean hasMore;

	// ------------------------ Public methods ------------------
	// ------------------------ Constructors --------------------
	public PagingRO() {

	}

	public PagingRO(Integer start, Integer limit, Long total) {
		this.start = start;
		this.limit = limit;
		this.total = total;
	}

	public PagingRO(Integer start, Integer limit, Long total, Boolean hasMore) {
		this.start = start;
		this.limit = limit;
		this.total = total;
		this.hasMore = hasMore;
	}

	// ------------------------ Field's handlers ----------------
	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(Boolean hasMore) {
		this.hasMore = hasMore;
	}
}
