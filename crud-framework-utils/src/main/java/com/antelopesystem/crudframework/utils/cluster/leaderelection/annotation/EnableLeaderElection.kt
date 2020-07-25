package com.antelopesystem.crudframework.utils.cluster.leaderelection.annotation

import com.antelopesystem.crudframework.utils.cluster.leaderelection.LeaderElectionConfiguration
import com.antelopesystem.crudframework.utils.cluster.lock.LockConfiguration
import com.antelopesystem.crudframework.utils.cluster.lock.annotation.EnableLocking
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.context.annotation.Import

@EnableLocking
@Import(LeaderElectionConfiguration::class)
annotation class EnableLeaderElection {
}