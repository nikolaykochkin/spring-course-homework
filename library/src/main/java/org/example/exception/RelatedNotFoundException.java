package org.example.exception;

public class RelatedNotFoundException extends RuntimeException {
    public RelatedNotFoundException(String message) {
        super(message);
    }
}
