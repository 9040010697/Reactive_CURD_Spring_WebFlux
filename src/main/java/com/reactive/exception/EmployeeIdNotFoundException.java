package com.reactive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="Employee Id Not Found")
public class EmployeeIdNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
