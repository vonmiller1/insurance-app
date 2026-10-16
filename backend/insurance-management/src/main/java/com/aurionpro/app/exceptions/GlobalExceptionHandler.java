package com.aurionpro.app.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.aurionpro.app.error.UserErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(UserApiException.class)
	public ResponseEntity<UserErrorResponse> handleAccountApiException(UserApiException e) {
		UserErrorResponse response = new UserErrorResponse();
		response.setMessage(e.getMessage());
		response.setStatus(e.getStatus() != null ? e.getStatus().value() : HttpStatus.BAD_REQUEST.value());
		response.setTime(System.currentTimeMillis());
		return new ResponseEntity<>(response, e.getStatus() != null ? e.getStatus() : HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();
		e.getBindingResult().getFieldErrors().forEach((error) -> errors.put(error.getField(), error.getDefaultMessage()));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<UserErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
		UserErrorResponse response = new UserErrorResponse();
		response.setMessage(e.getMessage());
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setTime(System.currentTimeMillis());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EmailExistsException.class)
	public ResponseEntity<UserErrorResponse> handleEmailExistsException(EmailExistsException e) {
		UserErrorResponse response = new UserErrorResponse();
		response.setMessage("This Email is already registered with our application");
		response.setStatus(HttpStatus.CONFLICT.value());
		response.setTime(System.currentTimeMillis());
		response.setField("email");
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(UsernameExistsException.class)
	public ResponseEntity<UserErrorResponse> handleUsernameExistsException(UsernameExistsException e) {
		UserErrorResponse response = new UserErrorResponse();
		response.setMessage("Username is already taken.");
		response.setStatus(HttpStatus.CONFLICT.value());
		response.setTime(System.currentTimeMillis());
		response.setField("username");
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
}
