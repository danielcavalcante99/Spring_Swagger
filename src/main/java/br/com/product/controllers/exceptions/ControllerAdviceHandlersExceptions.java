package br.com.product.controllers.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.product.services.exceptions.ProductBadRequestException;
import br.com.product.services.exceptions.ProductNotFoundException;

@RestControllerAdvice
public class ControllerAdviceHandlersExceptions {
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ProductNotFoundException e, HttpServletRequest request) {
		
		StandardError standardError = new StandardError();
		standardError.setTitleError("Not Found");
		standardError.setStatusHttp(HttpStatus.NOT_FOUND.value());
		standardError.setMessage(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
	}
	
	
	@ExceptionHandler(ProductBadRequestException.class)
	public ResponseEntity<StandardError> resourceBadRequest(ProductBadRequestException e, HttpServletRequest request) {
		
		StandardError standardError = new StandardError();
		standardError.setTitleError("Bad Request");
		standardError.setStatusHttp(HttpStatus.BAD_REQUEST.value());
		standardError.setMessage(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> resourceBadRequest(MethodArgumentNotValidException e, HttpServletRequest request) {

		StandardError standardError = new StandardError();
		standardError.setTitleError("Bad Request");
		standardError.setStatusHttp(HttpStatus.BAD_REQUEST.value());
		standardError.setMessage(e.getFieldError().getField().concat(" ").concat(e.getFieldError().getDefaultMessage()));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}
}
