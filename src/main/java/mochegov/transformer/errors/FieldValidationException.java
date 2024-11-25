package mochegov.transformer.errors;

public class FieldValidationException extends AppException {
    public FieldValidationException(String message, ErrorType error) {
        super(message, error);
    }

    public FieldValidationException(String message, Throwable cause, ErrorType error) {
        super(message, cause, error);
    }
}
