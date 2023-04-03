package org.example.exceptions;

public class ReadTemplateException extends RuntimeException {
    public ReadTemplateException(String message, Throwable cause) {
        super(message, cause);
    }
}
