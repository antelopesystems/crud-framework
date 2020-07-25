package com.antelopesystem.crudframework.utils.cluster.lock.manager

import com.antelopesystem.crudframework.utils.cluster.lock.ParameterLock
import java.util.concurrent.locks.Lock

class LocalLockManager : LockManager {
    override fun getLock(lockKey: String): Lock {
        return ParameterLock.getCanonicalParameterLock(lockKey)
    }
}