package com.antelopesystem.crudframework.crud.model;

import com.antelopesystem.crudframework.fieldmapper.annotation.MappedField;
import com.antelopesystem.crudframework.fieldmapper.transformer.CurrencyDoubleToLongTransformer;
import com.antelopesystem.crudframework.fieldmapper.transformer.LongToDateTransformer;
import com.antelopesystem.crudframework.fieldmapper.transformer.StringListToCommaDelimitedStringTransformer;
import com.antelopesystem.crudframework.jpa.ro.AbstractJpaUpdatableCrudRO;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TestEntityRO extends AbstractJpaUpdatableCrudRO {

	@MappedField(target = TestEntity.class, transformer = StringListToCommaDelimitedStringTransformer.class, mapTo = "commaDelimitedString")
	private List<String> stringList = Arrays.asList("val1", "val2", "val3", "val4");

	@MappedField(target = TestEntity.class, transformer = CurrencyDoubleToLongTransformer.class, mapTo = "longCurrency")
	private double doubleCurrency = 1000.0;

	@MappedField(target = TestEntity.class)
	private long genericVariable = 198283L;

	private String genericVariableString = "198283";

	@MappedField(target = TestEntity.class, transformer = LongToDateTransformer.class)
	private long date = 10000L;

	public TestEntityRO() {
	}

	public TestEntityRO(List<String> stringList, double doubleCurrency, long genericVariable, String genericVariableString, long date) {
		this.stringList = stringList;
		this.doubleCurrency = doubleCurrency;
		this.genericVariable = genericVariable;
		this.genericVariableString = genericVariableString;
		this.date = date;
	}

	public List<String> getStringList() {
		return stringList;
	}

	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}

	public double getDoubleCurrency() {
		return doubleCurrency;
	}

	public void setDoubleCurrency(double doubleCurrency) {
		this.doubleCurrency = doubleCurrency;
	}

	public long getGenericVariable() {
		return genericVariable;
	}

	public void setGenericVariable(long genericVariable) {
		this.genericVariable = genericVariable;
	}

	public String getGenericVariableString() {
		return genericVariableString;
	}

	public void setGenericVariableString(String genericVariableString) {
		this.genericVariableString = genericVariableString;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o == null || getClass() != o.getClass()) {
			return false;
		}

		TestEntityRO that = (TestEntityRO) o;
		return Double.compare(that.doubleCurrency, doubleCurrency) == 0 &&
				genericVariable == that.genericVariable &&
				date == that.date &&
				Objects.equals(stringList, that.stringList) &&
				Objects.equals(genericVariableString, that.genericVariableString);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), stringList, doubleCurrency, genericVariable, genericVariableString, date);
	}
}
