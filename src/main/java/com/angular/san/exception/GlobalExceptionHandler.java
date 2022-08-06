package com.angular.san.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	//To handle specific exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
		
		Errordetails errordetails = new Errordetails(new Date(), exception.getMessage(), request.getDescription(false));
		
		return new ResponseEntity(errordetails, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(APIException.class)
	public ResponseEntity<?> handleAPIException(APIException exception, WebRequest request){
		
		Errordetails errordetails = new Errordetails(new Date(), exception.getMessage(), request.getDescription(false));
		
		return new ResponseEntity(errordetails, HttpStatus.NOT_FOUND);
		
	}
	
	
	//To handle global exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request){
		
		Errordetails errordetails = new Errordetails(new Date(), exception.getMessage(), request.getDescription(false));
		
		return new ResponseEntity(errordetails, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	//Custom Validation error
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> customValidationErrorhandling(MethodArgumentNotValidException exception){
		
		Errordetails errordetails = new Errordetails(new Date(), "Validation Error", exception.getBindingResult().getFieldError().getDefaultMessage());
		
		return new ResponseEntity(errordetails, HttpStatus.BAD_REQUEST);
		
	}
}
