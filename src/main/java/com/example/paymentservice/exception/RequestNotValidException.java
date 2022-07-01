package com.example.paymentservice.exception;

public class RequestNotValidException extends RuntimeException{
    public RequestNotValidException() {
        super();
    }

    public RequestNotValidException(String message) {
        super(message);
    }

    public RequestNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestNotValidException(Throwable cause) {
        super(cause);
    }

    protected RequestNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
