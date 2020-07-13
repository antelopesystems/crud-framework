package com.antelopesystem.crudframework.ro;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PagingDTO<Payload> {

	@NotNull
	private PagingRO pagingRO;

	@Nullable
	private List<Payload> data;

	public PagingDTO() {
	}

	public PagingDTO(PagingRO pagingRO, List<Payload> data) {
		this.pagingRO = pagingRO;
		this.data = data;
	}

	public PagingRO getPagingRO() {
		return pagingRO;
	}

	public void setPagingRO(PagingRO pagingRO) {
		this.pagingRO = pagingRO;
	}

	public List<Payload> getData() {
		return data;
	}

	public void setData(List<Payload> data) {
		this.data = data;
	}
}
