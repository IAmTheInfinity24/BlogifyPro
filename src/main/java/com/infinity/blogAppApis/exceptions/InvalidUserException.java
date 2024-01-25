package com.infinity.blogAppApis.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvalidUserException extends Exception {

	private String resourceName;
	private String fieldName;
	private String fieldValue;
	
	public InvalidUserException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s not found %s: %s",  resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	
}
