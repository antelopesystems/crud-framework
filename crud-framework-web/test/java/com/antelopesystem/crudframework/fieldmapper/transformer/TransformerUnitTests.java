package com.antelopesystem.crudframework.fieldmapper.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.annotation.EnumType;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TransformerUnitTests {

	@Test
	public void testCommaDelimitedStringToListTransformer() {
		String testString = "var1,var2,var3,var4";
		List<String> expectedOutcome = Arrays.asList("var1", "var2", "var3", "var4");
		CommaDelimitedStringToListTransformer transformer = new CommaDelimitedStringToListTransformer();
		List<String> outcome = transformer.transform(TestPojo.getField("testString"), TestPojo.getField("testStringList"), testString);
		assertEquals(expectedOutcome, outcome);
	}

	@Test
	public void testStringListToCommaDelimitedStringTransformer() {
		List<String> testStringList = Arrays.asList("var1", "var2", "var3", "var4");
		String expectedOutcome = "var1,var2,var3,var4";
		StringListToCommaDelimitedStringTransformer transformer = new StringListToCommaDelimitedStringTransformer();
		String outcome = transformer.transform(TestPojo.getField("testStringList"), TestPojo.getField("testString"), testStringList);
		assertEquals(expectedOutcome, outcome);
	}

	@Test
	public void testCurrencyDoubleToLongTransformer() {
		double testDouble = 112.2;
		long expectedOutcome = 11220;
		CurrencyDoubleToLongTransformer transformer = new CurrencyDoubleToLongTransformer();
		long outcome = transformer.transform(TestPojo.getField("testDouble"), TestPojo.getField("testLong"), testDouble);
		assertEquals(expectedOutcome, outcome);
	}

	@Test
	public void testLongToCurrencyDoubleTransformer() {
		long testLong = 11220;
		double expectedOutcome = 112.2;
		LongToCurrencyDoubleTransformer transformer = new LongToCurrencyDoubleTransformer();
		double outcome = transformer.transform(TestPojo.getField("testLong"), TestPojo.getField("testDouble"), testLong);
		assertEquals(expectedOutcome, outcome, 0.0);
	}

	@Test
	public void testDateToLongTransformer() {
		Date testDate = new Date(100000);
		long expectedOutcome = testDate.getTime();
		DateToLongTransformer transformer = new DateToLongTransformer();
		long outcome = transformer.transform(TestPojo.getField("testDate"), TestPojo.getField("testLong"), testDate);
		assertEquals(expectedOutcome, outcome);
	}

	@Test
	public void testLongToDateTransformer() {
		long testLong = 100000;
		Date expectedOutcome = new Date(testLong);
		LongToDateTransformer transformer = new LongToDateTransformer();
		Date outcome = transformer.transform(TestPojo.getField("testLong"), TestPojo.getField("testDate"), testLong);
		assertEquals(expectedOutcome, outcome);
	}

	@Test
	public void testToStringTransformer() {
		Integer testInt = 130405;
		String expectedOutcome = testInt.toString();
		ToStringTransformer transformer = new ToStringTransformer();
		String outcome = transformer.transform(TestPojo.getField("testInt"), TestPojo.getField("testString"), testInt);
		assertEquals(expectedOutcome, outcome);
	}

	@Test
	public void testDefaultTransformer() {
		assertEquals(1L, new DefaultTransformer().transform(TestPojo.getField("testLong"), TestPojo.getField("testLong"), 1L));
	}

//	@Test
//	public void testBaseEntityToIdTransformer() {
//		BaseEntity testBaseEntity = new BaseEntity() {
//			@Override
//			public Long getId() {
//				return 55L;
//			}
//		};
//
//		assertEquals(55L, (long) new BaseEntityToIdTransformer().transform(TestPojo.getField("testBaseEntity"), TestPojo.getField("testLong"), testBaseEntity));
//	}

	@Test
	public void testCommaDelimitedStringToEnumListTransformer() {
		String testString = "First,Third";
		List<TestEnum> expectedOutcome = Arrays.asList(TestEnum.First, TestEnum.Third);

		assertEquals(expectedOutcome, new CommaDelimitedStringToEnumListTransformer().transform(TestPojo.getField("testString"), TestPojo.getField("testEnumList"), testString));
	}

	@Test
	public void testEnumListToCommaDelimitedString() {
		List<TestEnum> testEnumList = Arrays.asList(TestEnum.Second, TestEnum.First, TestEnum.Second);
		String expectedOutcome = "Second,First,Second";
		assertEquals(expectedOutcome, new EnumListToCommaDelimitedString().transform(TestPojo.getField("testEnumList"), TestPojo.getField("testString"), testEnumList));
	}

	@Test
	public void testEnumToStringTransformer() {
		TestEnum testEnum = TestEnum.Third;
		String expectedOutcome = "Third";
		EnumToStringTransformer enumToStringTransformer = new EnumToStringTransformer();
		assertEquals(expectedOutcome, enumToStringTransformer.transform(TestPojo.getField("testEnum"), TestPojo.getField("testString"), testEnum));
	}

	static class TestPojo {

		private String testString;

		private List<String> testStringList;

		private Long testLong;

		private Double testDouble;

		private Integer testInt;

		private Date testDate;

		private TestEnum testEnum = TestEnum.Third;

		@EnumType(TestEnum.class)
		private List<TestEnum> testEnumList;

		public static Field getField(String name) {
			try {
				return TestPojo.class.getDeclaredField(name);
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private enum TestEnum {
		First, Second, Third
	}


}