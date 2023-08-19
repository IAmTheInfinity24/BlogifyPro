package com.infinity.blogAppApis.payloads;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

	private String message;
	private boolean sucess;
	private Date date;
	
}
