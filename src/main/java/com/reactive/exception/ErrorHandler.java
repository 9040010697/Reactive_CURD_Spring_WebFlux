package com.reactive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import reactor.core.publisher.Mono;

@ControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(ClientErrorException.class)
	public Mono<ResponseEntity<ErrorResponse>> handleClientError(ClientErrorException ex) {
		HttpStatus status = ex.getClass().getAnnotation(ResponseStatus.class).value();
		ErrorResponse errorResponse = new ErrorResponse(status, ex.getMessage());
		return Mono.just(new ResponseEntity<>(errorResponse, status));
	}
}
