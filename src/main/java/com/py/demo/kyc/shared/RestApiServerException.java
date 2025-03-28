package com.py.demo.kyc.shared;

public class RestApiServerException extends Exception {
    private String requestUrl;
    private String message;
    public RestApiServerException(String requestUrl, String message) {
        super(message);
    }
}
