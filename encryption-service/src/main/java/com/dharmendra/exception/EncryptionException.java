package com.dharmendra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class EncryptionException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public EncryptionException() {
        super();
    }
    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
    public EncryptionException(String message) {
        super(message);
    }
    public EncryptionException(Throwable cause) {
        super(cause);
    }
	
}
