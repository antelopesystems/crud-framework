package com.antelopesystem.crudframework.utils.cluster.lock.manager

import com.antelopesystem.crudframework.utils.cluster.lock.MongoDistributedLock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import java.util.concurrent.locks.Lock

class MongoLockManager(val mongoTemplate: MongoTemplate) : LockManager {
    override fun getLock(lockKey: String): Lock {
        return MongoDistributedLock(lockKey, mongoTemplate)

    }
}