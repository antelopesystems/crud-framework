package com.antelopesystem.crudframework.utils.component.componentmap

import com.antelopesystem.crudframework.utils.component.componentmap.annotation.ComponentMap
import com.antelopesystem.crudframework.utils.component.componentmap.annotation.ComponentMapKey
import com.antelopesystem.crudframework.utils.component.componentmap.model.SingletonComponentMap
import com.antelopesystem.crudframework.utils.utils.ParameterLock
import com.antelopesystem.crudframework.utils.utils.ReflectionUtils
import com.antelopesystem.crudframework.utils.utils.getGenericClass
import com.antelopesystem.crudframework.utils.utils.resolveNestedGeneric
import org.apache.commons.lang3.ClassUtils
import org.springframework.aop.TargetClassAware
import org.springframework.aop.framework.Advised
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.core.annotation.AnnotationUtils
import java.lang.reflect.Method

class ComponentMapPostProcessor : BeanPostProcessor {

    private val componentMaps: MutableMap<ComponentMapIdentifier, MutableMap<Any, MutableList<Any>>> = mutableMapOf()

    override fun postProcessAfterInitialization(bean: Any, beanName: String?): Any {
        try {
            registerComponentMapKeyIfExists(bean)
            fillComponentMapIfExists(bean)
        } catch (e: Exception) {
        }
        return bean
    }

    private fun fillComponentMapIfExists(bean: Any) {
        var handler = bean
        if (handler is TargetClassAware) {
            try {
                handler = (handler as Advised).targetSource.target
            } catch (e: java.lang.Exception) {
            }
        }
        val fields = ReflectionUtils.getFields(handler.javaClass)

        for (field in fields) {
            if (field.isAnnotationPresent(ComponentMap::class.java)) {
                if (!Map::class.java.isAssignableFrom(field.type)) {
                    error("@ComponentMap may only be used on maps")
                }

                val mapped = field.getAnnotation(ComponentMap::class.java)
                try {
                    val keyClazz = field.getGenericClass(0)!!
                    var valueClazz = field.getGenericClass(1)!!
                    var isList = false
                    if(Collection::class.java.isAssignableFrom(valueClazz)) {
                        isList = true
                        valueClazz = field.resolveNestedGeneric(1)
                    }

                    ReflectionUtils.makeAccessible(field)
                    val map = getOrCreateComponentMap(keyClazz, valueClazz)
                    if(isList) {
                        field[handler] = map
                    } else {
                        field[handler] = SingletonComponentMap(map)
                    }
                } catch (e: java.lang.Exception) {
                }
            }
        }
    }

    private fun getOrCreateComponentMap(initialKeyType: Class<*>, initialValueType: Class<*>) : MutableMap<Any, MutableList<Any>> {
        val identifier = ComponentMapIdentifier(initialKeyType, initialValueType)
        var map = componentMaps[identifier]
        if(map != null) {
            return map
        }

        val lock = ParameterLock.getCanonicalParameterLock("${initialKeyType.canonicalName}_${initialValueType.canonicalName}")
        lock.lock()
        try {
            map = componentMaps[identifier]
            if(map != null) {
                return map
            }

            componentMaps[identifier] = mutableMapOf()
            return componentMaps[identifier]!!
        } finally {
            lock.unlock()
        }
    }

    private fun registerComponentMapKeyIfExists(bean: Any) {
        val methods = ReflectionUtils.getMethods(bean.javaClass)

        for (method in methods) {
            val annotation = AnnotationUtils.findAnnotation(method, ComponentMapKey::class.java)
            if (annotation != null) {
                try {
                    val key = method.invoke(bean)
                    val keyClass = key::class.java
                    val valueClass = getMethodDeclarer(method)
                    val map = getOrCreateComponentMap(keyClass, valueClass)
                    val list = map.computeIfAbsent(key) { mutableListOf() }
                    list += bean
                } catch (e: Exception) {
                }
            }
        }
    }


}

private data class ComponentMapIdentifier(val keyClazz: Class<*>, val valueClazz: Class<*>)

fun getMethodDeclarer(method: Method): Class<*> {
    var declaringClass: Class<*> = method.declaringClass
    val methodName: String = method.name
    val parameterTypes: Array<Class<*>> = method.parameterTypes
    for (interfaceType in ClassUtils.getAllInterfaces(declaringClass)) try {
        return interfaceType.getMethod(methodName, *parameterTypes).declaringClass
    } catch (ex: NoSuchMethodException) {
    }
    while (true) {
        declaringClass = declaringClass.superclass
        if (declaringClass == null) break
        try {
            val newMethod = declaringClass.getMethod(methodName, *parameterTypes)
            return getMethodDeclarer(newMethod)
        } catch (ex: NoSuchMethodException) {
            break
        }
    }

    error("Could not find method declarer for ${method.declaringClass.canonicalName}::${method.name}")
}

