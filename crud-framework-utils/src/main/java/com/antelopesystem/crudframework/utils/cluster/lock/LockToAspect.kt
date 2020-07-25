package com.antelopesystem.crudframework.utils.cluster.lock

import com.antelopesystem.crudframework.utils.cluster.lock.annotation.LockTo
import com.antelopesystem.crudframework.utils.cluster.lock.manager.LockManager
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.CodeSignature
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext


@Aspect
class LockToAspect {

    @Autowired
    private lateinit var lockManager: LockManager

    @Around("@annotation(com.antelopesystem.crudframework.utils.cluster.lock.annotation.LockTo)")
    fun lockMethod(pjp: ProceedingJoinPoint): Any? {
        val signature = pjp.signature as MethodSignature
        val annotation = signature.method.getAnnotation(LockTo::class.java)
        val parser = SpelExpressionParser()
        val context = StandardEvaluationContext()
        val codeSignature = pjp.signature as CodeSignature
        val argNames = codeSignature.parameterNames
        val args = pjp.args
        for (i in 0 until argNames.size) {
            context.setVariable(argNames[i], args[i])
        }
        val lockKey = parser.parseExpression(annotation.key).getValue(context) as String
        val lock = lockManager.getLock(lockKey)
        lock.lock()
        try {
            if(signature.returnType === Void.TYPE) {
                pjp.proceed()
                return null
            }
            return pjp.proceed()
        } finally {
            lock.unlock()
        }
    }
}