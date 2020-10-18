package com.antelopesystem.crudframework.fieldmapper.transformer

import com.antelopesystem.crudframework.fieldmapper.transformer.TransformerUnitTests.TestPojo
import com.antelopesystem.crudframework.fieldmapper.transformer.annotation.EnumType
import org.junit.Assert
import org.junit.Test
import java.lang.reflect.Field
import java.util.*

class TransformerUnitTests {
    @Test
    fun testCommaDelimitedStringToListTransformer() {
        val testString = "var1,var2,var3,var4"
        val expectedOutcome = Arrays.asList("var1", "var2", "var3", "var4")
        val transformer = CommaDelimitedStringToListTransformer()
        val outcome: List<String?> = transformer.transform(TestPojo.getField("testString"), TestPojo.getField("testStringList"), testString) as List<String?>
        Assert.assertEquals(expectedOutcome, outcome)
    }

    @Test
    fun testStringListToCommaDelimitedStringTransformer() {
        val testStringList = Arrays.asList("var1", "var2", "var3", "var4")
        val expectedOutcome = "var1,var2,var3,var4"
        val transformer = StringListToCommaDelimitedStringTransformer()
        val outcome = transformer.transform(TestPojo.getField("testStringList"), TestPojo.getField("testString"), testStringList)
        Assert.assertEquals(expectedOutcome, outcome)
    }

    @Test
    fun testCurrencyDoubleToLongTransformer() {
        val testDouble = 112.2
        val expectedOutcome: Long = 11220
        val transformer = CurrencyDoubleToLongTransformer()
        val outcome = transformer.transform(TestPojo.getField("testDouble"), TestPojo.getField("testLong"), testDouble)
        Assert.assertEquals(expectedOutcome, outcome)
    }

    @Test
    fun testLongToCurrencyDoubleTransformer() {
        val testLong: Long = 11220
        val expectedOutcome = 112.2
        val transformer = LongToCurrencyDoubleTransformer()
        val outcome = transformer.transform(TestPojo.getField("testLong"), TestPojo.getField("testDouble"), testLong)
        Assert.assertEquals(expectedOutcome, outcome, 0.0)
    }

    @Test
    fun testDateToLongTransformer() {
        val testDate = Date(100000)
        val expectedOutcome = testDate.time
        val transformer = DateToLongTransformer()
        val outcome = transformer.transform(TestPojo.getField("testDate"), TestPojo.getField("testLong"), testDate)
        Assert.assertEquals(expectedOutcome, outcome)
    }

    @Test
    fun testLongToDateTransformer() {
        val testLong: Long = 100000
        val expectedOutcome = Date(testLong)
        val transformer = LongToDateTransformer()
        val outcome = transformer.transform(TestPojo.getField("testLong"), TestPojo.getField("testDate"), testLong)
        Assert.assertEquals(expectedOutcome, outcome)
    }

    @Test
    fun testToStringTransformer() {
        val testInt = 130405
        val expectedOutcome = testInt.toString()
        val transformer = ToStringTransformer()
        val outcome = transformer.transform(TestPojo.getField("testInt"), TestPojo.getField("testString"), testInt)
        Assert.assertEquals(expectedOutcome, outcome)
    }

    @Test
    fun testDefaultTransformer() {
        Assert.assertEquals(1L, DefaultTransformer().transform(TestPojo.getField("testLong"), TestPojo.getField("testLong"), 1L))
    }

    @Test
    fun testCommaDelimitedStringToEnumListTransformer() {
        val testString = "First,Third"
        val expectedOutcome = Arrays.asList(TestEnum.First, TestEnum.Third)
        Assert.assertEquals(expectedOutcome, CommaDelimitedStringToEnumListTransformer().transform(TestPojo.getField("testString"), TestPojo.getField("testEnumList"), testString))
    }

    @Test
    fun testEnumListToCommaDelimitedString() {
        val testEnumList = Arrays.asList(TestEnum.Second, TestEnum.First, TestEnum.Second)
        val expectedOutcome = "Second,First,Second"
        Assert.assertEquals(expectedOutcome, EnumListToCommaDelimitedString().transform(TestPojo.getField("testEnumList"), TestPojo.getField("testString"), testEnumList))
    }

    @Test
    fun testEnumToStringTransformer() {
        val testEnum = TestEnum.Third
        val expectedOutcome = "Third"
        val enumToStringTransformer = EnumToStringTransformer()
        Assert.assertEquals(expectedOutcome, enumToStringTransformer.transform(TestPojo.getField("testEnum"), TestPojo.getField("testString"), testEnum))
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

        companion object {
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