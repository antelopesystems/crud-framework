package com.antelopesystem.crudframework.exception

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import kotlin.reflect.full.createInstance

@Aspect
class WrapExceptionAspect {

    @Pointcut("@within(wrapExceptionAnnotation)")
    fun typeLevel(wrapExceptionAnnotation: WrapException) {
    }

    @Pointcut("@annotation(wrapExceptionAnnotation)")
    fun methodLevel(wrapExceptionAnnotation: WrapException) {
    }

    @Around("methodLevel(wrapExceptionAnnotation) || typeLevel(wrapExceptionAnnotation)")
    @Throws(Throwable::class)
    fun invokeExceptionTreeAwareMethod(pjp: ProceedingJoinPoint, wrapExceptionAnnotation: WrapException): Any? {
        val method = (pjp.signature as MethodSignature).method
        var actualAnnotation = method.getAnnotation(WrapException::class.java)

        if (actualAnnotation != null) {
            actualAnnotation = method.getAnnotation(WrapException::class.java)
        }

        try {
            return pjp.proceed(pjp.args)
        } catch (e: Exception) {
            val exceptionClazz = actualAnnotation.value
            if(exceptionClazz.java.isAssignableFrom(e::class.java)) {
                throw e
            }

            val exceptionInstance = actualAnnotation.value.createInstance().initCause(e)
            throw exceptionInstance
        }
    }
}