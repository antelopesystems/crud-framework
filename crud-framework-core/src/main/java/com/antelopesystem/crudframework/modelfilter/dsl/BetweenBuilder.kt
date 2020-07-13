package com.antelopesystem.crudframework.modelfilter.dsl

import com.antelopesystem.crudframework.modelfilter.FilterField
import com.antelopesystem.crudframework.modelfilter.enums.FilterFieldDataType
import com.antelopesystem.crudframework.modelfilter.enums.FilterFieldOperation

class BetweenBuilder<T>(val fieldName: String, val source: T, val type: FilterFieldDataType) {

    infix fun build(target: T): FilterField {
        return FilterField(fieldName, FilterFieldOperation.Between, type, listOf(source, target))
    }

}