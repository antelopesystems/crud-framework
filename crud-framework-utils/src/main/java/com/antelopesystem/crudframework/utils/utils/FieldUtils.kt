@file:JvmName("FieldUtils")
package com.antelopesystem.crudframework.utils.utils

import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.lang.reflect.WildcardType

fun Field.getGenericClass(index: Int) : Class<*>? {
    return try {
        val genericType = this.genericType as ParameterizedType
        val typeArgument = genericType.actualTypeArguments[index]
        if(typeArgument is WildcardType) {
            val upperBound = typeArgument.upperBounds[0]
            return if(upperBound is ParameterizedType) {
                upperBound.rawType as Class<*>
            } else {
                upperBound as Class<*>
            }
        }
        return typeArgument as Class<*>
    } catch(e: ArrayIndexOutOfBoundsException) { null }
}

/**
 * Resolve a sublevel generic type (For example Map<*, List<Child>>)
 */
fun Field.resolveNestedGeneric(parentIndex: Int, childIndex: Int = 0): Class<*> {
    val genericType = this.genericType
    if(genericType !is ParameterizedType) {
        error("${this.type} is not a parameterized type")
    }
    var childType = genericType.actualTypeArguments[parentIndex]
    while(childType is WildcardType) {
        childType = childType.upperBounds[0]
    }

    if(childType is ParameterizedType) {
        var returnValue = childType.actualTypeArguments[childIndex]
        while(returnValue is WildcardType) {
            returnValue = returnValue.upperBounds[0]
        }
        return returnValue as Class<*>
    }

    return childType as Class<*>
}