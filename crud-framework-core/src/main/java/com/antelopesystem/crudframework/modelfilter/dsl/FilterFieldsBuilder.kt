package com.antelopesystem.crudframework.modelfilter.dsl

import com.antelopesystem.crudframework.modelfilter.BaseRawJunction
import com.antelopesystem.crudframework.modelfilter.FilterField
import com.antelopesystem.crudframework.modelfilter.dsl.annotation.FilterFieldDsl
import com.antelopesystem.crudframework.modelfilter.enums.FilterFieldDataType
import com.antelopesystem.crudframework.modelfilter.enums.FilterFieldOperation
import java.util.*


@FilterFieldDsl
class FilterFieldsBuilder(private val filterFields: MutableList<FilterField> = mutableListOf()) {

    infix fun String.Equal(target: String) {
        filterFields += FilterField(this, FilterFieldOperation.Equal, FilterFieldDataType.String, target)
    }

    infix fun String.Equal(target: Int) {
        filterFields += FilterField(this, FilterFieldOperation.Equal, FilterFieldDataType.Integer, target)
    }

    infix fun String.Equal(target: Long) {
        filterFields += FilterField(this, FilterFieldOperation.Equal, FilterFieldDataType.Long, target)
    }

    infix fun String.Equal(target: Double) {
        filterFields += FilterField(this, FilterFieldOperation.Equal, FilterFieldDataType.Double, target)
    }

    infix fun String.Equal(target: Boolean) {
        filterFields += FilterField(this, FilterFieldOperation.Equal, FilterFieldDataType.Boolean, target)
    }

    infix fun String.Equal(target: Date) {
        filterFields += FilterField(this, FilterFieldOperation.Equal, FilterFieldDataType.Date, target)
    }

    infix fun String.Equal(target: Enum<*>) {
        filterFields += FilterField(this, FilterFieldOperation.Equal, target.javaClass.canonicalName, target)
    }

    infix fun String.NotEqual(target: String) {
        filterFields += FilterField(this, FilterFieldOperation.NotEqual, FilterFieldDataType.String, target)
    }

    infix fun String.NotEqual(target: Int) {
        filterFields += FilterField(this, FilterFieldOperation.NotEqual, FilterFieldDataType.Integer, target)
    }

    infix fun String.NotEqual(target: Long) {
        filterFields += FilterField(this, FilterFieldOperation.NotEqual, FilterFieldDataType.Long, target)
    }

    infix fun String.NotEqual(target: Double) {
        filterFields += FilterField(this, FilterFieldOperation.NotEqual, FilterFieldDataType.Double, target)
    }

    infix fun String.NotEqual(target: Boolean) {
        filterFields += FilterField(this, FilterFieldOperation.NotEqual, FilterFieldDataType.Boolean, target)
    }

    infix fun String.NotEqual(target: Date) {
        filterFields += FilterField(this, FilterFieldOperation.NotEqual, FilterFieldDataType.Date, target)
    }

    infix fun String.NotEqual(target: Enum<*>) {
        filterFields += FilterField(this, FilterFieldOperation.NotEqual, target.javaClass.canonicalName, target)
    }

    infix fun String.Contains(target: String) {
        filterFields += FilterField(this, FilterFieldOperation.Contains, FilterFieldDataType.String, target)
    }

    infix fun String.StartsWith(target: String) {
        filterFields += FilterField(this, FilterFieldOperation.StartsWith, FilterFieldDataType.String, target)
    }

    infix fun String.EndsWith(target: String) {
        filterFields += FilterField(this, FilterFieldOperation.EndsWith, FilterFieldDataType.String, target)
    }

    infix fun String.GreaterThan(target: Int) {
        filterFields += FilterField(this, FilterFieldOperation.GreaterThan, FilterFieldDataType.Integer, target)
    }

    infix fun String.GreaterThan(target: Long) {
        filterFields += FilterField(this, FilterFieldOperation.GreaterThan, FilterFieldDataType.Long, target)
    }

    infix fun String.GreaterThan(target: Double) {
        filterFields += FilterField(this, FilterFieldOperation.GreaterThan, FilterFieldDataType.Double, target)
    }

    infix fun String.GreaterThan(target: Date) {
        filterFields += FilterField(this, FilterFieldOperation.GreaterThan, FilterFieldDataType.Date, target)
    }

    infix fun String.GreaterOrEqual(target: Int) {
        filterFields += FilterField(this, FilterFieldOperation.GreaterEqual, FilterFieldDataType.Integer, target)
    }

    infix fun String.GreaterOrEqual(target: Long) {
        filterFields += FilterField(this, FilterFieldOperation.GreaterEqual, FilterFieldDataType.Long, target)
    }

    infix fun String.GreaterOrEqual(target: Double) {
        filterFields += FilterField(this, FilterFieldOperation.GreaterEqual, FilterFieldDataType.Double, target)
    }

    infix fun String.GreaterOrEqual(target: Date) {
        filterFields += FilterField(this, FilterFieldOperation.GreaterEqual, FilterFieldDataType.Date, target)
    }

    infix fun String.LowerThan(target: Int) {
        filterFields += FilterField(this, FilterFieldOperation.LowerThan, FilterFieldDataType.Integer, target)
    }

    infix fun String.LowerThan(target: Long) {
        filterFields += FilterField(this, FilterFieldOperation.LowerThan, FilterFieldDataType.Long, target)
    }

    infix fun String.LowerThan(target: Double) {
        filterFields += FilterField(this, FilterFieldOperation.LowerThan, FilterFieldDataType.Double, target)
    }

    infix fun String.LowerThan(target: Date) {
        filterFields += FilterField(this, FilterFieldOperation.LowerThan, FilterFieldDataType.Date, target)
    }

    infix fun String.LowerOrEqual(target: Int) {
        filterFields += FilterField(this, FilterFieldOperation.LowerEqual, FilterFieldDataType.Integer, target)
    }

    infix fun String.LowerOrEqual(target: Long) {
        filterFields += FilterField(this, FilterFieldOperation.LowerEqual, FilterFieldDataType.Long, target)
    }

    infix fun String.LowerOrEqual(target: Double) {
        filterFields += FilterField(this, FilterFieldOperation.LowerEqual, FilterFieldDataType.Double, target)
    }

    infix fun String.LowerOrEqual(target: Date) {
        filterFields += FilterField(this, FilterFieldOperation.LowerEqual, FilterFieldDataType.Date, target)
    }

    @JvmName("stringRequireIn")
    infix fun String.RequireIn(target: Collection<String>) {
        if(target.isEmpty()) {
            noop()
        } else {
            this.In(target)
        }
    }

    @JvmName("intRequireIn")
    infix fun String.RequireIn(target: Collection<Int>) {
        if(target.isEmpty()) {
            noop()
        } else {
            this.In(target)
        }
    }

    @JvmName("longRequireIn")
    infix fun String.RequireIn(target: Collection<Long>) {
        if(target.isEmpty()) {
            noop()
        } else {
            this.In(target)
        }
    }

    @JvmName("doubleRequireIn")
    infix fun String.RequireIn(target: Collection<Double>) {
        if(target.isEmpty()) {
            noop()
        } else {
            this.In(target)
        }
    }

    @JvmName("dateRequireIn")
    infix fun String.RequireIn(target: Collection<Date>) {
        if(target.isEmpty()) {
            noop()
        } else {
            this.In(target)
        }
    }

    @JvmName("enumRequireIn")
    infix fun <T : Enum<T>> String.RequireIn(target: Collection<T>) {
        if(target.isEmpty()) {
            noop()
        } else {
            this.In(target)
        }
    }

    @JvmName("stringRequireNotIn")
    infix fun String.RequireNotIn(target: Collection<String>) {
        if(target.isEmpty()) {
            noop()
        } else {
            this.NotIn(target)
        }
    }

    @JvmName("intRequireNotIn")
    infix fun String.RequireNotIn(target: Collection<Int>) {
        if(target.isEmpty()) {
            noop()
        } else {
            this.NotIn(target)
        }
    }

    @JvmName("longRequireNotIn")
    infix fun String.RequireNotIn(target: Collection<Long>) {
        if(target.isEmpty()) {
            noop()
        } else {
            this.NotIn(target)
        }
    }

    @JvmName("doubleRequireNotIn")
    infix fun String.RequireNotIn(target: Collection<Double>) {
        if(target.isEmpty()) {
            noop()
        } else {
            this.NotIn(target)
        }
    }

    @JvmName("dateRequireNotIn")
    infix fun String.RequireNotIn(target: Collection<Date>) {
        if(target.isEmpty()) {
            noop()
        } else {
            this.NotIn(target)
        }
    }

    @JvmName("enumRequireNotIn")
    infix fun <T : Enum<T>> String.RequireNotIn(target: Collection<T>) {
        if(target.isEmpty()) {
            noop()
        } else {
            this.NotIn(target)
        }
    }

    @JvmName("stringIn")
    infix fun String.In(target: Collection<String>) {
        filterFields += FilterField(this, FilterFieldOperation.In, FilterFieldDataType.String, *target.toTypedArray())
    }

    @JvmName("intIn")
    infix fun String.In(target: Collection<Int>) {
        filterFields += FilterField(this, FilterFieldOperation.In, FilterFieldDataType.Integer, *target.toTypedArray())
    }

    @JvmName("longIn")
    infix fun String.In(target: Collection<Long>) {
        filterFields += FilterField(this, FilterFieldOperation.In, FilterFieldDataType.Long, *target.toTypedArray())
    }

    @JvmName("doubleIn")
    infix fun String.In(target: Collection<Double>) {
        filterFields += FilterField(this, FilterFieldOperation.In, FilterFieldDataType.Double, *target.toTypedArray())
    }

    @JvmName("dateIn")
    infix fun String.In(target: Collection<Date>) {
        filterFields += FilterField(this, FilterFieldOperation.In, FilterFieldDataType.Date, *target.toTypedArray())
    }

    @JvmName("enumIn")
    infix fun <T : Enum<T>> String.In(target: Collection<T>) {
        filterFields += FilterField(this, FilterFieldOperation.In, target.first()::class.java.canonicalName, *(target as Collection<*>).toTypedArray())
    }

    @JvmName("stringNotIn")
    infix fun String.NotIn(target: Collection<String>) {
        filterFields += FilterField(this, FilterFieldOperation.NotIn, FilterFieldDataType.String, *target.toTypedArray())
    }

    @JvmName("intNotIn")
    infix fun String.NotIn(target: Collection<Int>) {
        filterFields += FilterField(this, FilterFieldOperation.NotIn, FilterFieldDataType.Integer, *target.toTypedArray())
    }

    @JvmName("longNotIn")
    infix fun String.NotIn(target: Collection<Long>) {
        filterFields += FilterField(this, FilterFieldOperation.NotIn, FilterFieldDataType.Long, *target.toTypedArray())
    }

    @JvmName("doubleNotIn")
    infix fun String.NotIn(target: Collection<Double>) {
        filterFields += FilterField(this, FilterFieldOperation.NotIn, FilterFieldDataType.Double, *target.toTypedArray())
    }

    @JvmName("dateNotIn")
    infix fun String.NotIn(target: Collection<Date>) {
        filterFields += FilterField(this, FilterFieldOperation.NotIn, FilterFieldDataType.Date, *target.toTypedArray())
    }

    @JvmName("enumNotIn")
    infix fun <T : Enum<T>> String.NotIn(target: Collection<T>) {
        filterFields += FilterField(this, FilterFieldOperation.NotIn, target.first()::class.java.canonicalName, *(target as Collection<*>).toTypedArray())
    }

    @JvmName("stringContainsIn")
    infix fun String.ContainsIn(target: Collection<String>) {
        filterFields += FilterField(this, FilterFieldOperation.ContainsIn, FilterFieldDataType.String, *(target as Collection<*>).toTypedArray())
    }

    @JvmName("stringNotContainsIn")
    infix fun String.NotContainsIn(target: Collection<String>) {
        filterFields += FilterField(this, FilterFieldOperation.NotContainsIn, FilterFieldDataType.String, *(target as Collection<*>).toTypedArray())
    }


    infix fun String.Between(target: Int): BetweenBuilder<Int> {
        return BetweenBuilder(this, target, FilterFieldDataType.Integer)
    }

    infix fun String.Between(target: Long): BetweenBuilder<Long> {
        return BetweenBuilder(this, target, FilterFieldDataType.Long)
    }

    infix fun String.Between(target: Double): BetweenBuilder<Double> {
        return BetweenBuilder(this, target, FilterFieldDataType.Double)
    }

    infix fun String.Between(target: Date): BetweenBuilder<Date> {
        return BetweenBuilder(this, target, FilterFieldDataType.Date)
    }

    infix fun String.isNull(condition: Boolean) {
        when (condition) {
            true -> filterFields += FilterField(this, FilterFieldOperation.IsNull, FilterFieldDataType.Object, null, null)
            false -> filterFields += FilterField(this, FilterFieldOperation.IsNotNull, FilterFieldDataType.Object, null, null)

        }
    }

    fun String.isNull() {
        filterFields += FilterField(this, FilterFieldOperation.IsNull, FilterFieldDataType.Object, null, null)
    }

    fun String.isNotNull() {
        filterFields += FilterField(this, FilterFieldOperation.IsNotNull, FilterFieldDataType.Object, null, null)
    }

    fun rawJunction(junctionSupplier: () -> BaseRawJunction<*>) {
        filterFields += FilterField().apply {
            this.operation = FilterFieldOperation.RawJunction
            this.dataType = FilterFieldDataType.None
            this.values = arrayOf(junctionSupplier())
        }
    }

    fun and(setup: FilterFieldsBuilder.() -> Unit) {
        val filterFieldsBuilder = FilterFieldsBuilder()
        setup(filterFieldsBuilder)

        filterFields += FilterField().apply {
            this.children = filterFieldsBuilder.build()
            this.operation = FilterFieldOperation.And
        }
    }

    fun or(setup: FilterFieldsBuilder.() -> Unit) {
        val filterFieldsBuilder = FilterFieldsBuilder()
        setup(filterFieldsBuilder)

        filterFields += FilterField().apply {
            this.children = filterFieldsBuilder.build()
            this.operation = FilterFieldOperation.Or
        }
    }

    fun not(setup: FilterFieldsBuilder.() -> Unit) {
        val filterFieldsBuilder = FilterFieldsBuilder()
        setup(filterFieldsBuilder)

        filterFields += FilterField().apply {
            this.children = filterFieldsBuilder.build()
            this.operation = FilterFieldOperation.Not
        }
    }

    fun noop() {
        filterFields += FilterField().apply {
            this.operation = FilterFieldOperation.Noop
        }
    }

    fun add(filterField: FilterField) {
        filterFields += filterField
    }

    infix fun <T> BetweenBuilder<T>.And(target: T) {
        filterFields += this.build(target)
    }

    fun build() = filterFields.toList()
}