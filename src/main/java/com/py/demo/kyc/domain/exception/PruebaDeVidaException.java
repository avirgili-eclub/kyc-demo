package com.py.demo.kyc.domain.exception;

public class PruebaDeVidaException extends Exception {
    public PruebaDeVidaException(String message) {
        super(message);
    }

    public PruebaDeVidaException(String message, Throwable cause) {
        super(message, cause);
    }
}
