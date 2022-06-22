package com.rest.webservices.restful_webservices.exeption;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rest.webservices.restful_webservices.user.UserNotFoundException;
@ControllerAdvice
@RestController
public class ConstomizedExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex,WebRequest request) {
 ExceptionDetails exceptionetails =new ExceptionDetails( new Date(),ex.getMessage(),request.getDescription(false));
	return new ResponseEntity<Object>(exceptionetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex,WebRequest request) {
 ExceptionDetails exceptionDetails =new ExceptionDetails( new Date(),ex.getMessage(),
		                             request.getDescription(false));
	return new ResponseEntity<Object>(exceptionDetails,HttpStatus.NOT_FOUND);
	}
	@Override
	public final ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionDetails exceptionDetails =new ExceptionDetails( new Date(),ex.getMessage(),
                ex.getBindingResult().getObjectName().stripIndent());
		return new ResponseEntity<Object>(exceptionDetails,HttpStatus.BAD_REQUEST);
	}

}
