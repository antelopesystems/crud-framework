package com.antelopesystem.crudframework.web.ro

import java.io.Serializable

data class MassUpdateResult<ID>(
        val successful: Set<ID>,
        val failed: Map<ID, String>
)

