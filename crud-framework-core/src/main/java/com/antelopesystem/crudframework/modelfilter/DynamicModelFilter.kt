package com.antelopesystem.crudframework.modelfilter

class DynamicModelFilter(
    var start: Int? = null,
    var limit: Int? = null,
    var orders: MutableSet<OrderDTO> = mutableSetOf(),
    val filterFields: MutableList<FilterField> = mutableListOf()
) {
    val cacheKey: String get() = "CacheKey_" + this.javaClass.simpleName + "_" + this.hashCode()


    constructor() : this(null, null, mutableSetOf(), mutableListOf())

    constructor(filterFields: MutableList<FilterField>) : this(null, null, mutableSetOf(), filterFields)

    fun add(filterField: FilterField): DynamicModelFilter {
        filterFields.add(filterField)
        return this
    }

    fun addOrder(orderDTO: OrderDTO): DynamicModelFilter {
        orders.add(orderDTO)
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DynamicModelFilter

        if (start != other.start) return false
        if (limit != other.limit) return false
        if (orders != other.orders) return false
        if (filterFields != other.filterFields) return false

        return true
    }

    override fun hashCode(): Int {
        var result = start ?: 0
        result = 31 * result + (limit ?: 0)
        result = 31 * result + orders.hashCode()
        result = 31 * result + filterFields.hashCode()
        return result
    }

    override fun toString(): String {
        return "DynamicModelFilter(start=$start, limit=$limit, orders=$orders, cacheKey='$cacheKey', filterFields=$filterFields)"
    }
}