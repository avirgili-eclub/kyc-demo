package com.py.demo.kyc.shared;

import lombok.Data;

@Data
public class GenericResponse <T> {
    private T data;
    private String message;
    private int code;
}
