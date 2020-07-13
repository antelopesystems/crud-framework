package com.antelopesystem.crudframework.crud.hooks.interfaces;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface CRUDHooks<ID extends Serializable, Entity extends BaseCrudEntity<ID>> {

	default Class<Entity> getType() {
		Type[] interfaces = getClass().getGenericInterfaces();
		for(Type iface : interfaces) {
			if(iface instanceof ParameterizedType && CRUDHooks.class.isAssignableFrom(((ParameterizedTypeImpl) iface).getRawType())) {
				return (Class<Entity>) ((ParameterizedType) iface).getActualTypeArguments()[0];
			}
		}

		return null; // Not possible
	}
}
