package com.antelopesystem.crudframework.jpa;

import com.antelopesystem.crudframework.jpa.model.JpaBaseEntity;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGeneratorHelper;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class OverrideableGeneratedValueGenerator extends IdentityGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor s, Object entity) {
		JpaBaseEntity baseEntity = (JpaBaseEntity) entity;
		if(baseEntity.getId() > 0) {
			// the identifier has been set manually => use it
			return baseEntity.getId();
		} else {
			return IdentifierGeneratorHelper.POST_INSERT_INDICATOR;
		}
	}
}

