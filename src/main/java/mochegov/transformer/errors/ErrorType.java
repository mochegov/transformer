package mochegov.transformer.errors;

public enum ErrorType {
    TRANSFORMER_NOT_FOUND(100001),
    HISTORY_NOT_FOUND(100002),
    VALIDATION_ERRORS(1000003),
    DATABASE_ERROR(1000004),
    UNKNOWN(-1);

    public final int code;

    ErrorType(int code) {
        this.code = code;
    }
}
