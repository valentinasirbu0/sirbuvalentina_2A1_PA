package org.example.exceptions;

public class CatalogLoadException extends RuntimeException {

    public CatalogLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}