package com.panpages.bow.service.utils;

public class StringUtils {
	
	public static String nullValue(Object object, String defaultValue) {
		return object == null || object.toString().trim().equals("")? defaultValue : object.toString();
	}
	
}
