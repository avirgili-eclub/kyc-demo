package com.py.demo.kyc.shared;

public class RestApiClientException extends Exception {
    private String requestUrl;
    private String message;
    public RestApiClientException(String requestUrl, String message) {
        super(message);
    }
}
