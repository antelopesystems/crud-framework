package com.antelopesystem.crudframework.modelfilter.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum FilterFieldOperation {
	Equal(0), NotEqual(1), In(2), NotIn(3), GreaterThan(4), GreaterEqual(5), LowerThan(6), LowerEqual(7), Between(8), Contains(9), IsNull(10), IsNotNull(11), And(12), Or(13), Not(14), ContainsIn(15), NotContainsIn(16), RawJunction(17), StartsWith(18), EndsWith(19), Noop(20);

	private static final Map<Integer, FilterFieldOperation> lookup = new HashMap<Integer, FilterFieldOperation>();

	static {
		for(FilterFieldOperation s : EnumSet.allOf(FilterFieldOperation.class)) {
			lookup.put(s.getValue(), s);
		}
	}

	private int value;

	FilterFieldOperation(int v) {
		value = v;
	}

	public static FilterFieldOperation get(int value) {
		return lookup.get(value);
	}

	public int getValue() {
		return value;
	}
}
