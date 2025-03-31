package com.py.demo.kyc.shared;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;


@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler  {


    @ExceptionHandler(NullPointerException.class) // exception handled
    public ResponseEntity<ErrorResponse> handleNullPointerExceptions(
            Exception e
    ) {
        // ... potential custom logic

        HttpStatus status = HttpStatus.NOT_FOUND; // 404

        String message = NestedExceptionUtils.getMostSpecificCause(e).getMessage();
        log.error(message);

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()
                ),
                status
        );
    }


    @ExceptionHandler(ValidationException.class) // exception handled
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            Exception e
    ) {
        // ... potential custom logic

        HttpStatus status = HttpStatus.BAD_REQUEST; // 400

        String message = NestedExceptionUtils.getMostSpecificCause(e).getMessage();
        log.error(message);


        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        message
                ),
                status
        );
    }


    // fallback method
    @ExceptionHandler(Exception.class) // exception handled
    public ResponseEntity<ErrorResponse> handleExceptions(
            Exception e
    ) {
        // ... potential custom logic

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500

        // converting the stack trace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        String message = NestedExceptionUtils.getMostSpecificCause(e).getMessage();
        log.error(message);


        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        "Algo salio mal :( : " + message,
                        stackTrace // specifying the stack trace in case of 500s
                ),
                status
        );
    }

    @ExceptionHandler(Error.class) // exception handled
    public ResponseEntity<ErrorResponse> handleErrors(
            Exception e
    ) {
        // ... potential custom logic

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500

        // converting the stack trace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        String message = NestedExceptionUtils.getMostSpecificCause(e).getMessage();
        log.error(message);


        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        "Algo salio mal :( : " + message,
                        stackTrace // specifying the stack trace in case of 500s
                ),
                status
        );
    }
}
