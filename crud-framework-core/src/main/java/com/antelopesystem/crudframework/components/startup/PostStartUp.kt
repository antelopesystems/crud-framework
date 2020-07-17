package com.antelopesystem.crudframework.components.startup

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by Shani on 27/12/2018.
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class PostStartUp(val priority: Int = 0)