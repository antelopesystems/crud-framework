package com.antelopesystem.crudframework.crud.hooks;

import com.antelopesystem.crudframework.crud.hooks.interfaces.*;
import com.antelopesystem.crudframework.model.BaseCrudEntity;

import java.io.Serializable;

@Deprecated
/**
 * @deprecated
 * @see CRUDHooks
 */
public abstract class EntityPersistentHooks<ID extends Serializable, Entity extends BaseCrudEntity<ID>> implements CreateFromHooks<ID, Entity>, CreateHooks<ID, Entity>, DeleteHooks<ID, Entity>, IndexHooks<ID, Entity>, ShowByHooks<ID, Entity>, ShowHooks<ID, Entity>, UpdateFromHooks<ID, Entity>, UpdateHooks<ID, Entity> {

}
