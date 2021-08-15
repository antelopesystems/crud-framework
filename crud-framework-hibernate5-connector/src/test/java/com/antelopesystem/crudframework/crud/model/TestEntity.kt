package com.antelopesystem.crudframework.crud.model;

import com.antelopesystem.crudframework.fieldmapper.annotation.MappedField;
import com.antelopesystem.crudframework.fieldmapper.transformer.CommaDelimitedStringToListTransformer;
import com.antelopesystem.crudframework.fieldmapper.transformer.DateToLongTransformer;
import com.antelopesystem.crudframework.fieldmapper.transformer.LongToCurrencyDoubleTransformer;
import com.antelopesystem.crudframework.fieldmapper.transformer.ToStringTransformer;
import com.antelopesystem.crudframework.jpa.model.BaseJpaEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "test_entity")
public class TestEntity extends BaseJpaEntity {

	@MappedField(target = TestEntityRO.class, transformer = CommaDelimitedStringToListTransformer.class, mapTo = "stringList")
	private String commaDelimitedString = "val1,val2,val3,val4";

	@MappedField(target = TestEntityRO.class, transformer = LongToCurrencyDoubleTransformer.class, mapTo = "doubleCurrency")
	private long longCurrency = 100000L;

	@MappedField(target = TestEntityRO.class)
	@MappedField(target = TestEntityRO.class, transformer = ToStringTransformer.class, mapTo = "genericVariableString")
	private long genericVariable = 198283L;

	@MappedField(target = TestEntityRO.class, transformer = DateToLongTransformer.class)
	private Date date = new Date(10000L);

	public TestEntity() {
	}

	public TestEntity(String commaDelimitedString, long longCurrency, long genericVariable, Date date) {
		this.commaDelimitedString = commaDelimitedString;
		this.longCurrency = longCurrency;
		this.genericVariable = genericVariable;
		this.date = date;
	}

	public String getCommaDelimitedString() {
		return commaDelimitedString;
	}

	public void setCommaDelimitedString(String commaDelimitedString) {
		this.commaDelimitedString = commaDelimitedString;
	}

	public long getLongCurrency() {
		return longCurrency;
	}

	public void setLongCurrency(long longCurrency) {
		this.longCurrency = longCurrency;
	}

	public long getGenericVariable() {
		return genericVariable;
	}

	public void setGenericVariable(long genericVariable) {
		this.genericVariable = genericVariable;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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
		if(!super.equals(o)) {
			return false;
		}
		TestEntity that = (TestEntity) o;
		return longCurrency == that.longCurrency &&
				genericVariable == that.genericVariable &&
				Objects.equals(commaDelimitedString, that.commaDelimitedString) &&
				Objects.equals(date, that.date);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), commaDelimitedString, longCurrency, genericVariable, date);
	}
}
