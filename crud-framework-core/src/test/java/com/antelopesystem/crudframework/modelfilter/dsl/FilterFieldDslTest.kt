package com.antelopesystem.crudframework.modelfilter.dsl

import com.antelopesystem.crudframework.modelfilter.FilterField
import com.antelopesystem.crudframework.modelfilter.enums.FilterFieldDataType
import com.antelopesystem.crudframework.modelfilter.enums.FilterFieldOperation
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.*

class FilterFieldDslTest {
    @Test
    fun `test Equal#String`() {
        val filterField = and {
            "test" Equal "value"
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.Equal,
            FilterFieldDataType.String,
            1,
            arrayOf("value")
        )
    }

    @Test
    fun `test Equal#Int`() {
        val filterField = and {
            "test" Equal 1
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.Equal,
            FilterFieldDataType.Integer,
            1,
            arrayOf(1)
        )
    }

    @Test
    fun `test Equal#Long`() {
        val filterField = and {
            "test" Equal 1L
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.Equal,
            FilterFieldDataType.Long,
            1,
            arrayOf(1L)
        )
    }

    @Test
    fun `test Equal#Double`() {
        val filterField = and {
            "test" Equal 1.0
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.Equal,
            FilterFieldDataType.Double,
            1,
            arrayOf(1.0)
        )
    }

    @Test
    fun `test Equal#Boolean`() {
        val filterField = and {
            "test" Equal true
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.Equal,
            FilterFieldDataType.Boolean,
            1,
            arrayOf(true)
        )
    }

    @Test
    fun `test Equal#Date`() {
        val filterField = and {
            "test" Equal Date(0)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.Equal,
            FilterFieldDataType.Date,
            1,
            arrayOf(Date(0))
        )
    }

    @Test
    fun `test Equal#Enum`() {
        val filterField = and {
            "test" Equal TestEnum.One
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.Equal,
            FilterFieldDataType.Enum,
            1,
            arrayOf(TestEnum.One),
            TestEnum::class.java.canonicalName
        )
    }

    @Test
    fun `test NotEqual#String`() {
        val filterField = and {
            "test" NotEqual "value"
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotEqual,
            FilterFieldDataType.String,
            1,
            arrayOf("value")
        )
    }

    @Test
    fun `test NotEqual#Int`() {
        val filterField = and {
            "test" NotEqual 1
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotEqual,
            FilterFieldDataType.Integer,
            1,
            arrayOf(1)
        )
    }

    @Test
    fun `test NotEqual#Long`() {
        val filterField = and {
            "test" NotEqual 1L
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotEqual,
            FilterFieldDataType.Long,
            1,
            arrayOf(1L)
        )
    }

    @Test
    fun `test NotEqual#Double`() {
        val filterField = and {
            "test" NotEqual 1.0
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotEqual,
            FilterFieldDataType.Double,
            1,
            arrayOf(1.0)
        )
    }

    @Test
    fun `test NotEqual#Boolean`() {
        val filterField = and {
            "test" NotEqual true
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotEqual,
            FilterFieldDataType.Boolean,
            1,
            arrayOf(true)
        )
    }

    @Test
    fun `test NotEqual#Date`() {
        val filterField = and {
            "test" NotEqual Date(0)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotEqual,
            FilterFieldDataType.Date,
            1,
            arrayOf(Date(0))
        )
    }

    @Test
    fun `test NotEqual#Enum`() {
        val filterField = and {
            "test" NotEqual TestEnum.One
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotEqual,
            FilterFieldDataType.Enum,
            1,
            arrayOf(TestEnum.One),
            TestEnum::class.java.canonicalName
        )
    }

    @Test
    fun `test StartsWith#String`() {
        val filterField = and {
            "test" StartsWith "value"
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.StartsWith,
            FilterFieldDataType.String,
            1,
            arrayOf("value")
        )
    }

    @Test
    fun `test EndsWith#String`() {
        val filterField = and {
            "test" EndsWith "value"
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.EndsWith,
            FilterFieldDataType.String,
            1,
            arrayOf("value")
        )
    }

    @Test
    fun `test GreaterThan#Int`() {
        val filterField = and {
            "test" GreaterThan 1
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.GreaterThan,
            FilterFieldDataType.Integer,
            1,
            arrayOf(1)
        )
    }

    @Test
    fun `test GreaterThan#Long`() {
        val filterField = and {
            "test" GreaterThan 1L
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.GreaterThan,
            FilterFieldDataType.Long,
            1,
            arrayOf(1L)
        )
    }

    @Test
    fun `test GreaterThan#Double`() {
        val filterField = and {
            "test" GreaterThan 1.0
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.GreaterThan,
            FilterFieldDataType.Double,
            1,
            arrayOf(1.0)
        )
    }

    @Test
    fun `test GreaterThan#Date`() {
        val filterField = and {
            "test" GreaterThan Date(0)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.GreaterThan,
            FilterFieldDataType.Date,
            1,
            arrayOf(Date(0))
        )
    }

    @Test
    fun `test GreaterOrEqual#Int`() {
        val filterField = and {
            "test" GreaterOrEqual 1
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.GreaterEqual,
            FilterFieldDataType.Integer,
            1,
            arrayOf(1)
        )
    }

    @Test
    fun `test GreaterOrEqual#Long`() {
        val filterField = and {
            "test" GreaterOrEqual 1L
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.GreaterEqual,
            FilterFieldDataType.Long,
            1,
            arrayOf(1L)
        )
    }

    @Test
    fun `test GreaterOrEqual#Double`() {
        val filterField = and {
            "test" GreaterOrEqual 1.0
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.GreaterEqual,
            FilterFieldDataType.Double,
            1,
            arrayOf(1.0)
        )
    }

    @Test
    fun `test GreaterOrEqual#Date`() {
        val filterField = and {
            "test" GreaterOrEqual Date(0)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.GreaterEqual,
            FilterFieldDataType.Date,
            1,
            arrayOf(Date(0))
        )
    }

    @Test
    fun `test LowerThan#Long`() {
        val filterField = and {
            "test" LowerThan 1L
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.LowerThan,
            FilterFieldDataType.Long,
            1,
            arrayOf(1L)
        )
    }

    @Test
    fun `test LowerThan#Double`() {
        val filterField = and {
            "test" LowerThan 1.0
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.LowerThan,
            FilterFieldDataType.Double,
            1,
            arrayOf(1.0)
        )
    }

    @Test
    fun `test LowerThan#Date`() {
        val filterField = and {
            "test" LowerThan Date(0)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.LowerThan,
            FilterFieldDataType.Date,
            1,
            arrayOf(Date(0))
        )
    }

    @Test
    fun `test LowerOrEqual#Long`() {
        val filterField = and {
            "test" LowerOrEqual 1L
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.LowerEqual,
            FilterFieldDataType.Long,
            1,
            arrayOf(1L)
        )
    }

    @Test
    fun `test LowerOrEqual#Double`() {
        val filterField = and {
            "test" LowerOrEqual 1.0
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.LowerEqual,
            FilterFieldDataType.Double,
            1,
            arrayOf(1.0)
        )
    }

    @Test
    fun `test LowerOrEqual#Date`() {
        val filterField = and {
            "test" LowerOrEqual Date(0)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.LowerEqual,
            FilterFieldDataType.Date,
            1,
            arrayOf(Date(0))
        )
    }

    @Test
    fun `test RequireIn#String if empty`() {
        val filterField = and {
            "test" RequireIn listOf<String>()
        }.children.first()

        expectThat(filterField.operation)
            .isEqualTo(FilterFieldOperation.Noop)
    }

    @Test
    fun `test RequireIn#String`() {
        val filterField = and {
            "test" RequireIn listOf("a", "b")
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.In,
            FilterFieldDataType.String,
            2,
            arrayOf("a", "b")
        )
    }

    @Test
    fun `test RequireIn#Int if empty`() {
        val filterField = and {
            "test" RequireIn listOf<Int>()
        }.children.first()

        expectThat(filterField.operation)
            .isEqualTo(FilterFieldOperation.Noop)
    }

    @Test
    fun `test RequireIn#Int`() {
        val filterField = and {
            "test" RequireIn listOf(1, 2)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.In,
            FilterFieldDataType.Integer,
            2,
            arrayOf(1, 2)
        )
    }

    @Test
    fun `test RequireIn#Long if empty`() {
        val filterField = and {
            "test" RequireIn listOf<Long>()
        }.children.first()

        expectThat(filterField.operation)
            .isEqualTo(FilterFieldOperation.Noop)
    }

    @Test
    fun `test RequireIn#Long`() {
        val filterField = and {
            "test" RequireIn listOf(1L, 2L)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.In,
            FilterFieldDataType.Long,
            2,
            arrayOf(1L, 2L)
        )
    }

    @Test
    fun `test RequireIn#Double if empty`() {
        val filterField = and {
            "test" RequireIn listOf<Double>()
        }.children.first()

        expectThat(filterField.operation)
            .isEqualTo(FilterFieldOperation.Noop)
    }

    @Test
    fun `test RequireIn#Double`() {
        val filterField = and {
            "test" RequireIn listOf(1.0, 2.0)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.In,
            FilterFieldDataType.Double,
            2,
            arrayOf(1.0, 2.0)
        )
    }

    @Test
    fun `test RequireIn#Date if empty`() {
        val filterField = and {
            "test" RequireIn listOf<Date>()
        }.children.first()

        expectThat(filterField.operation)
            .isEqualTo(FilterFieldOperation.Noop)
    }

    @Test
    fun `test RequireIn#Date`() {
        val filterField = and {
            "test" RequireIn listOf(Date(0), Date(1))
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.In,
            FilterFieldDataType.Date,
            2,
            arrayOf(Date(0), Date(1))
        )
    }

    @Test
    fun `test RequireIn#Enum if empty`() {
        val filterField = and {
            "test" RequireIn listOf<TestEnum>()
        }.children.first()

        expectThat(filterField.operation)
            .isEqualTo(FilterFieldOperation.Noop)
    }

    @Test
    fun `test RequireIn#Enum`() {
        val filterField = and {
            "test" RequireIn listOf(TestEnum.One, TestEnum.Two)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.In,
            FilterFieldDataType.Enum,
            2,
            arrayOf(TestEnum.One, TestEnum.Two),
            TestEnum::class.java.canonicalName
        )
    }

    @Test
    fun `test RequireNotIn#String if empty`() {
        val filterField = and {
            "test" RequireNotIn listOf<String>()
        }.children.first()

        expectThat(filterField.operation)
            .isEqualTo(FilterFieldOperation.Noop)
    }

    @Test
    fun `test RequireNotIn#String`() {
        val filterField = and {
            "test" RequireNotIn listOf("a", "b")
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotIn,
            FilterFieldDataType.String,
            2,
            arrayOf("a", "b")
        )
    }

    @Test
    fun `test RequireNotIn#Int if empty`() {
        val filterField = and {
            "test" RequireNotIn listOf<Int>()
        }.children.first()

        expectThat(filterField.operation)
            .isEqualTo(FilterFieldOperation.Noop)
    }

    @Test
    fun `test RequireNotIn#Int`() {
        val filterField = and {
            "test" RequireNotIn listOf(1, 2)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotIn,
            FilterFieldDataType.Integer,
            2,
            arrayOf(1, 2)
        )
    }

    @Test
    fun `test RequireNotIn#Long if empty`() {
        val filterField = and {
            "test" RequireNotIn listOf<Long>()
        }.children.first()

        expectThat(filterField.operation)
            .isEqualTo(FilterFieldOperation.Noop)
    }

    @Test
    fun `test RequireNotIn#Long`() {
        val filterField = and {
            "test" RequireNotIn listOf(1L, 2L)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotIn,
            FilterFieldDataType.Long,
            2,
            arrayOf(1L, 2L)
        )
    }

    @Test
    fun `test RequireNotIn#Double if empty`() {
        val filterField = and {
            "test" RequireNotIn listOf<Double>()
        }.children.first()

        expectThat(filterField.operation)
            .isEqualTo(FilterFieldOperation.Noop)
    }

    @Test
    fun `test RequireNotIn#Double`() {
        val filterField = and {
            "test" RequireNotIn listOf(1.0, 2.0)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotIn,
            FilterFieldDataType.Double,
            2,
            arrayOf(1.0, 2.0)
        )
    }

    @Test
    fun `test RequireNotIn#Date if empty`() {
        val filterField = and {
            "test" RequireNotIn listOf<Date>()
        }.children.first()

        expectThat(filterField.operation)
            .isEqualTo(FilterFieldOperation.Noop)
    }

    @Test
    fun `test RequireNotIn#Date`() {
        val filterField = and {
            "test" RequireNotIn listOf(Date(0), Date(1))
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotIn,
            FilterFieldDataType.Date,
            2,
            arrayOf(Date(0), Date(1))
        )
    }

    @Test
    fun `test RequireNotIn#Enum if empty`() {
        val filterField = and {
            "test" RequireNotIn listOf<TestEnum>()
        }.children.first()

        expectThat(filterField.operation)
            .isEqualTo(FilterFieldOperation.Noop)
    }

    @Test
    fun `test RequireNotIn#Enum`() {
        val filterField = and {
            "test" RequireNotIn listOf(TestEnum.One, TestEnum.Two)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotIn,
            FilterFieldDataType.Enum,
            2,
            arrayOf(TestEnum.One, TestEnum.Two),
            TestEnum::class.java.canonicalName
        )
    }

    @Test
    fun `test In#String`() {
        val filterField = and {
            "test" In listOf("a", "b")
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.In,
            FilterFieldDataType.String,
            2,
            arrayOf("a", "b")
        )
    }

    @Test
    fun `test In#Int`() {
        val filterField = and {
            "test" In listOf(1, 2)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.In,
            FilterFieldDataType.Integer,
            2,
            arrayOf(1, 2)
        )
    }

    @Test
    fun `test In#Long`() {
        val filterField = and {
            "test" In listOf(1L, 2L)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.In,
            FilterFieldDataType.Long,
            2,
            arrayOf(1L, 2L)
        )
    }

    @Test
    fun `test In#Double`() {
        val filterField = and {
            "test" In listOf(1.0, 2.0)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.In,
            FilterFieldDataType.Double,
            2,
            arrayOf(1.0, 2.0)
        )
    }

    @Test
    fun `test In#Date`() {
        val filterField = and {
            "test" In listOf(Date(0), Date(1))
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.In,
            FilterFieldDataType.Date,
            2,
            arrayOf(Date(0), Date(1))
        )
    }

    @Test(expected = NoSuchElementException::class)
    fun `test In#Enum if empty`() {
        // Since we derive the type from the first element in the collection, this will throw an exception
        val filterField = and {
            "test" In listOf<TestEnum>()
        }.children.first()
    }

    @Test
    fun `test In#Enum`() {
        val filterField = and {
            "test" In listOf(TestEnum.One, TestEnum.Two)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.In,
            FilterFieldDataType.Enum,
            2,
            arrayOf(TestEnum.One, TestEnum.Two),
            TestEnum::class.java.canonicalName
        )
    }

    @Test
    fun `test NotIn#String`() {
        val filterField = and {
            "test" NotIn listOf("a", "b")
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotIn,
            FilterFieldDataType.String,
            2,
            arrayOf("a", "b")
        )
    }

    @Test
    fun `test NotIn#NotInt`() {
        val filterField = and {
            "test" NotIn listOf(1, 2)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotIn,
            FilterFieldDataType.Integer,
            2,
            arrayOf(1, 2)
        )
    }

    @Test
    fun `test NotIn#Long`() {
        val filterField = and {
            "test" NotIn listOf(1L, 2L)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotIn,
            FilterFieldDataType.Long,
            2,
            arrayOf(1L, 2L)
        )
    }

    @Test
    fun `test NotIn#Double`() {
        val filterField = and {
            "test" NotIn listOf(1.0, 2.0)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotIn,
            FilterFieldDataType.Double,
            2,
            arrayOf(1.0, 2.0)
        )
    }

    @Test
    fun `test NotIn#Date`() {
        val filterField = and {
            "test" NotIn listOf(Date(0), Date(1))
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotIn,
            FilterFieldDataType.Date,
            2,
            arrayOf(Date(0), Date(1))
        )
    }

    @Test(expected = NoSuchElementException::class)
    fun `test NotIn#Enum if empty`() {
        // Since we derive the type from the first element in the collection, this will throw an exception
        val filterField = and {
            "test" NotIn listOf<TestEnum>()
        }.children.first()
    }

    @Test
    fun `test NotIn#Enum`() {
        val filterField = and {
            "test" NotIn listOf(TestEnum.One, TestEnum.Two)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotIn,
            FilterFieldDataType.Enum,
            2,
            arrayOf(TestEnum.One, TestEnum.Two),
            TestEnum::class.java.canonicalName
        )
    }

    @Test
    fun `test ContainsIn#String`() {
        val filterField = and {
            "test" ContainsIn listOf("a", "b")
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.ContainsIn,
            FilterFieldDataType.String,
            2,
            arrayOf("a", "b")
        )
    }

    @Test
    fun `test NotContainsIn#String`() {
        val filterField = and {
            "test" NotContainsIn listOf("a", "b")
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.NotContainsIn,
            FilterFieldDataType.String,
            2,
            arrayOf("a", "b")
        )
    }

    @Test
    fun `test Between#Int`() {
        val filterField = and {
            "test" Between 1 And 2
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.Between,
            FilterFieldDataType.Integer,
            2,
            arrayOf(1, 2)
        )
    }

    @Test
    fun `test Between#Long`() {
        val filterField = and {
            "test" Between 1L And 2L
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.Between,
            FilterFieldDataType.Long,
            2,
            arrayOf(1L, 2L)
        )
    }

    @Test
    fun `test Between#Double`() {
        val filterField = and {
            "test" Between 1.0 And 2.0
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.Between,
            FilterFieldDataType.Double,
            2,
            arrayOf(1.0, 2.0)
        )
    }

    @Test
    fun `test Between#Date`() {
        val filterField = and {
            "test" Between Date(0) And Date(1)
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.Between,
            FilterFieldDataType.Date,
            2,
            arrayOf(Date(0), Date(1))
        )
    }

    @Test
    fun `test isNull`() {
        val filterField = and {
            "test".isNull()
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.IsNull,
            FilterFieldDataType.Object,
            2,
            arrayOf(null, null)
        )
    }

    @Test
    fun `test isNotNull`() {
        val filterField = and {
            "test".isNotNull()
        }.children.first()

        filterField.runAssertions(
            "test",
            FilterFieldOperation.IsNotNull,
            FilterFieldDataType.Object,
            2,
            arrayOf(null, null)
        )
    }

    private fun FilterField.runAssertions(expectedFieldName: String, expectedOperation: FilterFieldOperation, expectedDataType: FilterFieldDataType, expectedValuesLength: Int, expectedValues: Array<Any?>, expectedEnumType: String? = null) {
        expectThat(this.fieldName)
            .isEqualTo(expectedFieldName)
        expectThat(this.operation)
            .isEqualTo(expectedOperation)
        expectThat(this.dataType)
            .isEqualTo(expectedDataType)
        expectThat(this.values.size)
            .isEqualTo(expectedValuesLength)
        expectThat(this.values)
            .isEqualTo(expectedValues)

        if(expectedEnumType != null) {
            expectThat(this.enumType)
                .isEqualTo(enumType)
        }
    }
}

enum class TestEnum {
    One, Two, Three
}