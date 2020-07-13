package com.antelopesystem.crudframework.exception.dto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ErrorField {

	private final String fieldName;

	private final String message;

	private final Map<String, Object> attributes;

	private static List<String> forbiddenKeys = Arrays.asList(
			"message",
			"groups",
			"payload"
	);

	public ErrorField(String fieldName, String message, Map<String, Object> attributes) {
		this.fieldName = fieldName;
		this.message = message;
		this.attributes = attributes.entrySet()
				.stream()
				.filter(map -> !forbiddenKeys.contains(map.getKey()))
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getMessage() {
		return message;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String toString() {
		return "ErrorField{" +
				"fieldName='" + fieldName + '\'' +
				", message='" + message + '\'' +
				", attributes=" + attributes +
				'}';
	}
}
