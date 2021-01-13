package com.antelopesystem.crudframework.utils.cluster.lock

import kotlinx.coroutines.*
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock

class MongoDistributedLock(private val lockKey: String, private val mongoTemplate: MongoTemplate) : Lock {
    private var lockId: ObjectId? = null

    override fun lock() {
        if(lockId != null) {
            log.info("Lock $lockKey is already acquired by this instance, silently aborting")
            return
        }

        runBlocking {
            while(lockId == null) {
                val result = tryLock()
                if(result) {
                    break
                }
                delay(1000)
            }
        }
    }

    override fun tryLock(time: Long, unit: TimeUnit): Boolean {
        if(lockId != null) {
            log.info("Lock $lockKey is already acquired by this instance, silently aborting")
            return true
        }

        try {
            runBlocking {
                withTimeout(unit.toMillis(time)) {
                    lock()
                }
            }
            return true
        } catch(e: TimeoutCancellationException) {
            return false
        }
    }

    override fun tryLock(): Boolean {
        if(lockId != null) {
            log.info("Lock $lockKey is already acquired by this instance, silently aborting")
            return true
        }

        try {
            val lock = LockDocument(lockKey)
            mongoTemplate.save(lock)
            lockId = lock.id
            log.info("Acquired lock $lockKey")
            GlobalScope.async {
                delay(5000); // Wait 5 seconds to first heartbet
                while(lockId != null) {
                    log.info("Updating heartbeat for lock $lockKey at _id $lockId")
                    mongoTemplate.findAndModify(getLockQuery(), Update().set("heartbeat", Date()), LockDocument::class.java)
                    delay(5000)
                }
            }
            return true
        } catch(e: Exception) {
            log.info("Could not acquire lock $lockKey: ${e.message}")
            return false
        }
    }

    override fun unlock() {
        if(lockId == null) {
            log.info("Lock $lockKey is not acquired by this instance, silently aborting")
            return
        }

        mongoTemplate.remove(getLockQuery(), LockDocument::class.java)
        lockId = null
        log.info("Unlocked lock $lockKey")
    }

    override fun lockInterruptibly() {
        throw java.lang.UnsupportedOperationException()
    }

    override fun newCondition(): Condition {
        throw UnsupportedOperationException()
    }

    private fun getLockQuery(): Query {
        Objects.requireNonNull(lockId)
        return Query().addCriteria(Criteria.where("_id").isEqualTo(lockId))
    }

    companion object {
        private val log = LoggerFactory.getLogger(MongoDistributedLock::class.java)
    }

}