package com.example.demoawsmysql1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L; //Added based on warning suggestion
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
