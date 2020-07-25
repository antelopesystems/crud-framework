package com.antelopesystem.crudframework.utils.cluster.lock.manager

import java.util.concurrent.locks.Lock

interface LockManager {
    fun getLock(lockKey: String): Lock
}

