package com.antelopesystem.crudframework.utils.cluster.leaderelection

import com.antelopesystem.crudframework.utils.cluster.lock.manager.LockManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.slf4j.LoggerFactory
import java.util.concurrent.locks.Lock
import javax.annotation.PostConstruct
import kotlin.reflect.jvm.jvmName

class LeaderElector(
        private val lockManager: LockManager,
        private val leaderListeners: List<LeaderListener>,
        private val applicationName: String,
        private val lock: Lock = lockManager.getLock("${applicationName}_leader")
) {
    @PostConstruct
    fun nominateForLeadership() {
        GlobalScope.async {
            log.info("Attempting to acquire leadership")
            lock.lock()
            log.info("Leadership acquired, calling ${leaderListeners.size} listeners")
            for (leaderListener in leaderListeners) {
                log.trace("Listener ${leaderListener::class.jvmName} about to be invoked")
                try {
                    leaderListener.onElected()
                } catch(e: Exception) {
                    log.error("Listener ${leaderListener::class.jvmName} failed on invocation", e)
                }
            }

        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(LeaderElector::class.java)
    }
}