package com.shelfguard.shelfguard.common.exception;

public class BusinessException extends RuntimeException{
	 private final BusinessErrorCode errorCode;

	    public BusinessException(BusinessErrorCode errorCode) {
	        super(errorCode.getMessage());
	        this.errorCode = errorCode;
	    }

	    public BusinessErrorCode getErrorCode() {
	        return errorCode;
	    }
	    
}
