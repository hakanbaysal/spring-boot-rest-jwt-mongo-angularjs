package com.halitkorkmaz.api.util;

import com.halitkorkmaz.api.util.validation.ValidationData;

public class StringUtils {

	public static ValidationData parseDuplicateErrorMessage(String message) {
		 String fieldName = message.substring(message.indexOf("index: ") + 7,
				message.indexOf(" dup key"));
		
		 return new ValidationData(fieldName, fieldName + " already exists.");
	}
}
