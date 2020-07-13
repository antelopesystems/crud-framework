package com.antelopesystem.crudframework.modelfilter.dsl

import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter
import com.antelopesystem.crudframework.modelfilter.FilterField
import com.antelopesystem.crudframework.modelfilter.dsl.annotation.FilterFieldDsl


@FilterFieldDsl
class ModelFilterBuilder(var orderBy: String = "", var orderDesc: Boolean = true, var start: Int = 0, var limit: Int = 10000, var filterFields: List<FilterField> = mutableListOf()) {


    fun where(setup: FilterFieldsBuilder.() -> Unit) {
        val filterFieldsBuilder = FilterFieldsBuilder()
        filterFieldsBuilder.setup()
        this.filterFields += filterFieldsBuilder.build()
    }

    fun order(setup: OrderBuilder.() -> Unit) {
        val orderBuilder = OrderBuilder()
        orderBuilder.setup()
        val (orderBy, orderDesc) = orderBuilder.build()

        this.orderBy = orderBy
        this.orderDesc = orderDesc
    }

    fun build(): DynamicModelFilter {
        val filter = DynamicModelFilter()

        filter
                .setOrderBy<DynamicModelFilter>(orderBy)
                .setOrderDesc<DynamicModelFilter>(orderDesc)
                .setStart<DynamicModelFilter>(start)
                .setLimit<DynamicModelFilter>(limit)
                .filterFields = filterFields
        return filter

    }

}