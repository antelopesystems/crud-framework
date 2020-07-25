package com.antelopesystem.crudframework.utils.cluster.lock

import java.lang.ref.Reference
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class ParameterLock private constructor(private val key: Any, private val lock: Lock) : Lock {

    private class WeakKeyLockPair(param: Any, lock: Lock) {
        /** The weakly-referenced parameter. If it were strongly referenced, the entries of
         * the lock Map would never be garbage collected, causing a memory leak.  */
        val param: Reference<Any>

        /** The actual lock object on which threads will synchronize.  */
        val lock: Lock

        init {
            this.param = WeakReference(param)
            this.lock = lock
        }
    }

    override fun lock() {
        lock.lock()
    }

    @Throws(InterruptedException::class)
    override fun lockInterruptibly() {
        lock.lockInterruptibly()
    }

    override fun tryLock(): Boolean {
        return lock.tryLock()
    }

    @Throws(InterruptedException::class)
    override fun tryLock(time: Long, unit: TimeUnit): Boolean {
        return lock.tryLock(time, unit)
    }

    override fun unlock() {
        lock.unlock()
    }

    override fun newCondition(): Condition {
        return lock.newCondition()
    }

    override fun toString(): String {
        return "ParameterLock [" +
                "key=" + key +
                ", lock=" + lock +
                ']'
    }

    companion object {
        /** Holds a WeakKeyLockPair for each parameter. The mapping may be deleted upon garbage collection
         * if the canonical key is not strongly referenced anymore (by the threads using the Lock).  */
        private val locks: MutableMap<Any?, WeakKeyLockPair> = WeakHashMap()

        fun getCanonicalParameterLock(param: Any): Lock {
            var canonical: Any? = null
            var lock: Lock? = null
            synchronized(locks) {
                var pair = locks[param]
                if (pair != null) {
                    canonical = pair.param.get() // could return null!
                }
                if (canonical == null) { // no such entry or the reference was cleared in the meantime
                    canonical = param // the first thread (the current thread) delivers the new canonical key
                    pair = WeakKeyLockPair(canonical!!, ReentrantLock())
                    locks[canonical] = pair
                }
            }

            // the canonical key is strongly referenced now...
            lock = locks[canonical]!!.lock // ...so this is guaranteed not to return null
            // ... but the key must be kept strongly referenced after this method returns,
            // so wrap it in the Lock implementation, which a thread of course needs
            // to be able to synchronize. This enforces a thread to have a strong reference
            // to the key, while it isn't aware of it (as this method declares to return a
            // Lock rather than a ParameterLock).
            return ParameterLock(canonical!!, lock)
        }
    }

}