package org.example.exceptions;

public class RaportException extends RuntimeException {
    public RaportException(String message, Throwable cause) {
        super(message, cause);
    }
}
