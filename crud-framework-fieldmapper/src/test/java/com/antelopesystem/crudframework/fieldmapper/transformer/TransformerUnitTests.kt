package com.antelopesystem.crudframework.fieldmapper.transformer

import com.antelopesystem.crudframework.fieldmapper.annotation.StringTrimLength
import com.antelopesystem.crudframework.fieldmapper.transformer.annotation.EnumType
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.isNull
import java.lang.reflect.Field
import java.util.*

class TransformerUnitTests {
    @Test
    fun testCommaDelimitedStringToListTransformer() {
        val testString = "var1,var2,var3,var4"
        val expectedOutcome = Arrays.asList("var1", "var2", "var3", "var4")
        val transformer = CommaDelimitedStringToListTransformer()
        val outcome: List<String?> = transformer.transform(TestPojo.getField("testString"), TestPojo.getField("testStringList"), testString, TestPojo.INSTANCE, TestPojo.INSTANCE) as List<String?>
        expectThat(outcome)
            .isEqualTo(expectedOutcome)
    }

    @Test
    fun testStringListToCommaDelimitedStringTransformer() {
        val testStringList = Arrays.asList("var1", "var2", "var3", "var4")
        val expectedOutcome = "var1,var2,var3,var4"
        val transformer = StringListToCommaDelimitedStringTransformer()
        val outcome = transformer.transform(TestPojo.getField("testStringList"), TestPojo.getField("testString"), testStringList, TestPojo.INSTANCE, TestPojo.INSTANCE)
        expectThat(outcome)
            .isEqualTo(expectedOutcome)
    }

    @Test
    fun testCurrencyDoubleToLongTransformer() {
        val testDouble = 112.2
        val expectedOutcome: Long = 11220
        val transformer = CurrencyDoubleToLongTransformer()
        val outcome = transformer.transform(TestPojo.getField("testDouble"), TestPojo.getField("testLong"), testDouble, TestPojo.INSTANCE, TestPojo.INSTANCE)
        expectThat(outcome)
            .isEqualTo(expectedOutcome)
    }

    @Test
    fun testLongToCurrencyDoubleTransformer() {
        val testLong: Long = 11220
        val expectedOutcome = 112.2
        val transformer = LongToCurrencyDoubleTransformer()
        val outcome = transformer.transform(TestPojo.getField("testLong"), TestPojo.getField("testDouble"), testLong, TestPojo.INSTANCE, TestPojo.INSTANCE)
        expectThat(outcome)
            .isEqualTo(expectedOutcome)

    }

    @Test
    fun testDateToLongTransformer() {
        val testDate = Date(100000)
        val expectedOutcome = testDate.time
        val transformer = DateToLongTransformer()
        val outcome = transformer.transform(TestPojo.getField("testDate"), TestPojo.getField("testLong"), testDate, TestPojo.INSTANCE, TestPojo.INSTANCE)
        expectThat(outcome)
            .isEqualTo(expectedOutcome)
    }

    @Test
    fun testLongToDateTransformer() {
        val testLong: Long = 100000
        val expectedOutcome = Date(testLong)
        val transformer = LongToDateTransformer()
        val outcome = transformer.transform(TestPojo.getField("testLong"), TestPojo.getField("testDate"), testLong, TestPojo.INSTANCE, TestPojo.INSTANCE)
        expectThat(outcome)
            .isEqualTo(expectedOutcome)
    }

    @Test
    fun testToStringTransformer() {
        val testInt = 130405
        val expectedOutcome = testInt.toString()
        val transformer = ToStringTransformer()
        val outcome = transformer.transform(TestPojo.getField("testInt"), TestPojo.getField("testString"), testInt, TestPojo.INSTANCE, TestPojo.INSTANCE)
        expectThat(outcome)
            .isEqualTo(expectedOutcome)
    }

    @Test
    fun testDefaultTransformer() {
        val outcome = DefaultTransformer().transform(TestPojo.getField("testLong"), TestPojo.getField("testLong"), 1L, TestPojo.INSTANCE, TestPojo.INSTANCE)
        expectThat(outcome)
            .isEqualTo(1L)
    }

    @Test
    fun testCommaDelimitedStringToEnumListTransformer() {
        val testString = "First,Third"
        val expectedOutcome = Arrays.asList(TestEnum.First, TestEnum.Third)
        val outcome = CommaDelimitedStringToEnumListTransformer().transform(TestPojo.getField("testString"), TestPojo.getField("testEnumList"), testString, TestPojo.INSTANCE, TestPojo.INSTANCE)
        expectThat(outcome)
            .isEqualTo(expectedOutcome)
    }

    @Test
    fun testEnumListToCommaDelimitedString() {
        val testEnumList = Arrays.asList(TestEnum.Second, TestEnum.First, TestEnum.Second)
        val expectedOutcome = "Second,First,Second"
        val outcome = EnumListToCommaDelimitedString().transform(TestPojo.getField("testEnumList"), TestPojo.getField("testString"), testEnumList, TestPojo.INSTANCE, TestPojo.INSTANCE)
        expectThat(outcome)
            .isEqualTo(expectedOutcome)
    }

    @Test
    fun testEnumToStringTransformer() {
        val testEnum = TestEnum.Third
        val expectedOutcome = "Third"
        val enumToStringTransformer = EnumToStringTransformer()
        val outcome = enumToStringTransformer.transform(TestPojo.getField("testEnum"), TestPojo.getField("testString"), testEnum, TestPojo.INSTANCE, TestPojo.INSTANCE)

        expectThat(outcome)
            .isEqualTo(expectedOutcome)
    }

    @Test
    fun `test StringTrimTransformer fails on missing @StringTrimLength annotation`() {
        val transformer = StringTrimTransformer()
        expectThrows<IllegalStateException> {
            transformer.transform(TestPojo.getField("testString"), TestPojo.getField("testString"), "test", TestPojo.INSTANCE, TestPojo.INSTANCE)
        }
    }

    @Test
    fun `test StringTrimTransformer returns null on null`() {
        val transformer = StringTrimTransformer()
        val outcome = transformer.transform(TestPojo.getField("testStringTrim"), TestPojo.getField("testStringTrim"), null, TestPojo.INSTANCE, TestPojo.INSTANCE)
        expectThat(outcome)
            .isNull()
    }

    @Test
    fun `test StringTrimTransformer happy flow`() {
        val transformer = StringTrimTransformer()
        val input = "testing123"
        val expectedOutcome = "testi"
        val outcome = transformer.transform(TestPojo.getField("testStringTrim"), TestPojo.getField("testStringTrim"), input, TestPojo.INSTANCE, TestPojo.INSTANCE)
        expectThat(outcome)
            .isEqualTo(expectedOutcome)
    }

    internal class TestPojo {
        private val testString: String? = null
        private val testStringList: List<String>? = null
        private val testLong: Long? = null
        private val testDouble: Double? = null
        private val testInt: Int? = null
        private val testDate: Date? = null
        private val testEnum = TestEnum.Third

        @EnumType(TestEnum::class)
        private val testEnumList: List<TestEnum>? = null

        @StringTrimLength(5)
        private val testStringTrim: String? = null

        companion object {
            val INSTANCE = TestPojo()
            fun getField(name: String?): Field {
                return try {
                    TestPojo::class.java.getDeclaredField(name)
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }
            }
        }
    }

    private enum class TestEnum {
        First, Second, Third
    }
}