package com.infinity.blogAppApis.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException
{

	String ResorceName;
	String fieldName;
	long fieldId;
	String field;
	
	public ResourceNotFoundException(String resorceName, String fieldName, Long fieldId) {
		super(String.format("%s not found with %s : %s", resorceName,fieldName,fieldId));
		this.ResorceName = resorceName;
		this.fieldName = fieldName;
		this.fieldId = fieldId;
	}
	
	public ResourceNotFoundException(String resorceName, String fieldName, String field) {
		super(String.format("%s not found with %s : %s", resorceName,fieldName,field));
		this.ResorceName = resorceName;
		this.fieldName = fieldName;
		this.field = field;
	}
	
	
	
	
}
