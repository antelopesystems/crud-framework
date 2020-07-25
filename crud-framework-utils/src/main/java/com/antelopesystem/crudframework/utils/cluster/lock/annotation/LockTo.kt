package com.antelopesystem.crudframework.utils.cluster.lock.annotation

import org.intellij.lang.annotations.Language

@Target(allowedTargets = [AnnotationTarget.FUNCTION])
annotation class LockTo(@Language("SpEL") val key: String)