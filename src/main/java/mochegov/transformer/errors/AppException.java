package mochegov.transformer.errors;

import lombok.Getter;

@Getter
public abstract class AppException extends RuntimeException {
    protected final ErrorType error;

    public AppException(String message, ErrorType error) {
        super(message);
        this.error = error;
    }

    public AppException(String message, Throwable cause, ErrorType error) {
        super(message, cause);
        this.error = error;
    }
}
