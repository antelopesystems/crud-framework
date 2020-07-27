package com.mycompany.crudframework.crud.ro

import java.io.Serializable

data class ManyCrudResult<SuccessfulResult, FailedResult>(
        val successful: Set<SuccessfulResult>,
        val failed: List<ManyFailedReason<FailedResult>>
)
