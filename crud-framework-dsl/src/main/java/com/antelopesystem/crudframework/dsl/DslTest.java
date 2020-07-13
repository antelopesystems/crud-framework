package com.antelopesystem.crudframework.dsl;

import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;

public class DslTest {

	private static final String testParse = "query {\n" +
			"    where {\n" +
			"        id == 1;\n" +
			"        active == true;\n" +
			"        email ~ 'gmail.com';\n" +
			"        test == null;\n" +
			"        dateOfBirth == date('12/08/2020');\n" +
			"        and {\n" +
			"            jox == 'bla';\n" +
			"            bla in [1,2,3,4];\n" +
			"        }\n" +
			"    }\n" +
			"}";

	public static void main(String[] args) {
		DynamicModelFilter filter = (new CrudDsl()).parseInput(testParse);
		System.out.println();
	}

}
