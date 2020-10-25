package com.antelopesystem.crudframework.jpa.lazyinitializer.annotation

import com.antelopesystem.crudframework.crud.annotation.WithHooks
import com.antelopesystem.crudframework.jpa.lazyinitializer.LazyInitializerPersistentHooks

@WithHooks(hooks = [LazyInitializerPersistentHooks::class])
annotation class DynamicLazyInitialization

