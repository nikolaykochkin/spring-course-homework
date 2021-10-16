package org.example.exception;

public class LibraryDataAccessException extends RuntimeException {
    public LibraryDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }


    public LibraryDataAccessException(String message) {
        super(message);
    }
}
