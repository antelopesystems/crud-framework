package com.antelopesystem.crudframework.dsl.function;

import java.text.*;
import java.util.Date;

public class DateFunction implements DslFunction<Date> {
	@Override
	public Date execute(Object... args) {
		String dateString = (String) args[0];
		String format = "dd/MM/yyyy";
		if(dateString == null) {
			throw new IllegalArgumentException("dateString not provided for date function");
		}
		DateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(dateString);
		} catch(ParseException e) {
			throw new RuntimeException("Could not parse date " + dateString + " with format " + format);
		}
	}
}
