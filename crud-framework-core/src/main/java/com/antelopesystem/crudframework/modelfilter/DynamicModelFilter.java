package com.antelopesystem.crudframework.modelfilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Shani on 22/02/2018.
 */
public class DynamicModelFilter extends BaseModelFilter {

	protected List<FilterField> filterFields = new ArrayList<>();

	public DynamicModelFilter() {
	}

	public DynamicModelFilter(List<FilterField> filterFields) {
		this.filterFields = filterFields;
	}

	public List<FilterField> getFilterFields() {
		return filterFields;
	}

	public void setFilterFields(List<FilterField> filterFields) {
		this.filterFields = filterFields;
	}


	public DynamicModelFilter add(FilterField filterField) {
		filterFields.add(filterField);
		return this;
	}


	@Override
	public String toString() {
		return "DynamicModelFilter [" + super.toString() +
				", filterFields=" + filterFields +
				']';
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o == null || getClass() != o.getClass()) {
			return false;
		}
		if(!super.equals(o)) {
			return false;
		}
		DynamicModelFilter that = (DynamicModelFilter) o;
		return Objects.equals(filterFields, that.filterFields);
	}

	@Override
	public int hashCode() {

		return Objects.hash(super.hashCode(), filterFields);
	}
}
