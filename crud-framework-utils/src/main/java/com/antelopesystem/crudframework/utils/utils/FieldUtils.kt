@file:JvmName("FieldUtils")
package com.antelopesystem.crudframework.utils.utils

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
import sun.reflect.generics.reflectiveObjects.WildcardTypeImpl
import java.lang.reflect.Field
import java.lang.reflect.WildcardType

fun Field.getGenericClass(index: Int) : Class<*>? {
    return try {
        val genericType = this.genericType as ParameterizedTypeImpl
        val typeArgument = genericType.actualTypeArguments[index]
        if(typeArgument is WildcardTypeImpl) {
            return typeArgument.upperBounds[0] as Class<*>
        }
        return typeArgument as Class<*>
    } catch(e: ArrayIndexOutOfBoundsException) { null }
}