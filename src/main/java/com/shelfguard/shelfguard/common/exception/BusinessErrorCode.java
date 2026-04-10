package com.shelfguard.shelfguard.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessErrorCode {
	PRODUCT_NOT_FOUND(1001, "Product bulunamadi!"),
	PRODUCT_ALREADY_EXISTS(1002, "bu Product zaten var!"),
    INVALID_REQUEST(2001, "Gecersiz istek"),
    VALIDATION_ERROR(2002, "Dogrulama hatasi!");
	
	
	 private final int code;
	    private final String message;
}
