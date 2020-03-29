package com.reactive.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private LocalDateTime time;
	private HttpStatus status;
	private String message;
	
	public ErrorResponse(HttpStatus status, String message) {
		super();
		this.time = LocalDateTime.now();
		this.status = status;
		this.message = message;
	}
	
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
