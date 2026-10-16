package com.aurionpro.app.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class UserApiException extends RuntimeException {
	private HttpStatus status;
	private String message;
}
