package com.infinity.blogAppApis.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.infinity.blogAppApis.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false,new Date());
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String , String>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {

		Map<String , String> map=new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((errors)->{
			String getField=((FieldError)errors).getField();
			String message=errors.getDefaultMessage();
			map.put(getField, message);
		});
		return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidUserException.class)
	public ResponseEntity<ApiResponse> invalidUserException(InvalidUserException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message,false,new Date());
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
}
