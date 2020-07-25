package com.antelopesystem.crudframework.utils.cluster.lock

import com.antelopesystem.crudframework.utils.cluster.lock.manager.LocalLockManager
import com.antelopesystem.crudframework.utils.cluster.lock.manager.LockManager
import com.antelopesystem.crudframework.utils.cluster.lock.manager.MongoLockManager
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.util.ClassUtils

@Configuration
class LockConfiguration {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Bean
    fun lockToAspect(): LockToAspect {
        return LockToAspect()
    }

    @Bean
    fun lockManager(): LockManager {
        try {
            log.debug("Attempting to locate MongoTemplate on the classpath")
            val clazz = ClassUtils.forName("org.springframework.data.mongodb.core.MongoTemplate", LockConfiguration::class.java.classLoader)
            log.debug("Found MongoTemplate on the classpath, attempting to find a bean of the type")
            val mongoTemplate = applicationContext.getBean(clazz)
            log.info("Using Mongo for locking")
            return MongoLockManager(mongoTemplate as MongoTemplate)
        } catch(e: ClassNotFoundException) {
            log.debug("MongoTemplate was not found on the classpath")
        } catch(e: NoSuchBeanDefinitionException) {
            log.debug("MongoTemplate was foudn on the classpath, but not defined as a bean")
        } catch(e: Exception) {
        }

        log.info("Using ParameterLock for locking")
        return LocalLockManager()
    }

    companion object {
        private val log = LoggerFactory.getLogger(LockConfiguration::class.java)
    }
}