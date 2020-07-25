package com.antelopesystem.crudframework.utils.cluster.leaderelection

import com.antelopesystem.crudframework.utils.cluster.lock.LockConfiguration
import com.antelopesystem.crudframework.utils.cluster.lock.manager.LockManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@AutoConfigureAfter(LockConfiguration::class)
@ConditionalOnBean(LockManager::class)
class LeaderElectionConfiguration {
    @Bean
    fun leaderElector(lockManager: LockManager, leaderListeners: List<LeaderListener>, @Value("\${spring.application.name:default}") applicationName: String): LeaderElector {
        return LeaderElector(lockManager, leaderListeners, applicationName)
    }
}