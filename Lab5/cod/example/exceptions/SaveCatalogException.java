package org.example.exceptions;

public class SaveCatalogException extends RuntimeException {
    public SaveCatalogException(String message, Throwable cause) {
        super(message, cause);
    }
}
