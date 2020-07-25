package com.antelopesystem.crudframework.utils.cluster.leaderelection

interface LeaderListener {

    fun onElected()
}