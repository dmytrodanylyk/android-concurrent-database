package com.test.database.utils;

public class ArrayUtils {

    public static String[] build(Object... values) {
		String[] arr = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			if (values[i] instanceof Boolean) {
				boolean value = (boolean) values[i];
				arr[i] = String.valueOf(value ? 1 : 0);
			} else {
				arr[i] = String.valueOf(values[i]);
			}
		}
		return arr;
	}
}
