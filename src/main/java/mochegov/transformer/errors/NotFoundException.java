package mochegov.transformer.errors;

public class NotFoundException extends AppException {
    public NotFoundException(String message, ErrorType error) {
        super(message, error);
    }

    public NotFoundException(String message, Throwable cause, ErrorType error) {
        super(message, cause, error);
    }
}
