package com.newapp.Webapp.exception;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.newapp.Webapp.Dto.Response;

@ControllerAdvice // it is used to handle exception globally for the Entire Application
// it allow to consolidate your exception handling code in one place rather than multiple controller.

public class GlobalExceptionhandler {
	// Response created in dto
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleAllException(Exception ex,WebRequest request){	
		Response errorResponse = Response.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value()) // this is 500 error message 
				.message(ex.getMessage())
				.build();
		return new ResponseEntity<>(errorResponse , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// Intercept kind of exception that present below and it is intercepted by the global exception.
	
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Response> handleNotFoundException (NotFoundException ex,WebRequest request){
		Response errorResponse = Response.builder()
				. status(HttpStatus.NOT_FOUND.value())
				. message(ex.getMessage())
				.build();// build method has to be called at last because, it finalize the method so following status and message would not work 
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<Response> handleInvalidCredentialsException(InvalidCredentialsException ex ,WebRequest request){
		Response errorResponse = Response.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.message(ex.getMessage())
				.build();
		return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
	}
}
