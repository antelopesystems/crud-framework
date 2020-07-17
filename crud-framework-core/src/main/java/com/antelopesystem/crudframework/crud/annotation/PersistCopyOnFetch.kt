package com.antelopesystem.crudframework.crud.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Whether or not to persist a copy of the original state of the entity on fetch
 */
@Retention(RetentionPolicy.RUNTIME)
annotation class PersistCopyOnFetch 