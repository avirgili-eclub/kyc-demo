package com.py.demo.kyc.shared;

public abstract class BaseException extends Exception {
    private String message;

    public BaseException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
