package com.company.docflow.exception;

public class DocumentNotFoundException extends RuntimeException {

    public DocumentNotFoundException(String message) {
        super(message);
    }

    public DocumentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentNotFoundException(Long id) {
        super("Document with id " + id + " not found");
    }
}
