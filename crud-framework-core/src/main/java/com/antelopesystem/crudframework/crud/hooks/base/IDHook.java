package com.antelopesystem.crudframework.crud.hooks.base;

import java.io.Serializable;

public interface IDHook<ID extends Serializable> extends CRUDHook {

	void run(ID id);
}
