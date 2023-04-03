package org.example.exceptions;

public class LoadTemplateException extends RuntimeException {
    public LoadTemplateException(String message, Throwable cause) {
        super(message, cause);
    }
}
