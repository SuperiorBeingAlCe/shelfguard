package com.shelfguard.shelfguard.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessErrorCode {
	  USER_NOT_FOUND(1001, "User not found"),
	    BRANCH_NOT_FOUND(1002, "Branch not found"),
	    INVALID_REQUEST(2001, "Invalid request"),
	    VALIDATION_ERROR(2002, "Validation error");
	
	 private final int code;
	    private final String message;
}
