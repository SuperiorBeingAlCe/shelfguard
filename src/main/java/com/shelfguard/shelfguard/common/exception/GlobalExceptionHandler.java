package com.shelfguard.shelfguard.common.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	  @ExceptionHandler(NotFoundException.class)
	    public ResponseEntity<ApiError> handleNotFound(
	            NotFoundException ex,
	            HttpServletRequest request) {

	        ApiError error = new ApiError(
	                LocalDateTime.now(),
	                404,
	                "NOT_FOUND",
	                ex.getMessage(),
	                request.getRequestURI()
	        );

	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ApiError> handleGeneric(
	            Exception ex,
	            HttpServletRequest request) {

	        ApiError error = new ApiError(
	                LocalDateTime.now(),
	                500,
	                "INTERNAL_ERROR",
	                ex.getMessage(),
	                request.getRequestURI()
	        );

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	    }
}
