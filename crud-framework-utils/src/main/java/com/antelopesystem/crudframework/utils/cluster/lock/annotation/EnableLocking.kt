package com.antelopesystem.crudframework.utils.cluster.lock.annotation

import com.antelopesystem.crudframework.utils.cluster.lock.LockConfiguration
import org.springframework.context.annotation.Import


@Import(LockConfiguration::class)
annotation class EnableLocking {
}