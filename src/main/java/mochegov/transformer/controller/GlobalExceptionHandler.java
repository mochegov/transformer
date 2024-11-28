package mochegov.transformer.controller;

import static mochegov.transformer.controller.MainControllerApi.CORRELATION_ID_HEADER;
import static mochegov.transformer.controller.MainControllerApi.REQUEST_ID_HEADER;
import static mochegov.transformer.errors.ErrorType.VALIDATION_ERRORS;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import mochegov.transformer.errors.AppException;
import mochegov.transformer.errors.ErrorMessage;
import mochegov.transformer.errors.FieldValidationException;
import mochegov.transformer.errors.NotFoundException;
import mochegov.transformer.errors.ServerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleBodyValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        String validationErrors = ex.getBindingResult().getAllErrors().stream()
            .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
            .sorted()
            .collect(Collectors.joining("; "));

        ErrorMessage errorMessage = ErrorMessage.builder()
            .errorCode(VALIDATION_ERRORS.code)
            .errorDescription(validationErrors)
            .build();
        return new ResponseEntity<>(errorMessage, createCorrelationIdHeader(request), BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleBodyArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
            .errorCode(VALIDATION_ERRORS.code)
            .errorDescription(ex.getMessage())
            .build();
        return new ResponseEntity<>(errorMessage, createCorrelationIdHeader(request), BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<ErrorMessage> handleRequestParamValidationException(ConstraintViolationException ex, WebRequest request) {
        String validationErrors = ex.getConstraintViolations().stream()
            .map(error -> error.getPropertyPath().toString() + ": " + error.getMessage())
            .sorted()
            .collect(Collectors.joining("; "));

        ErrorMessage errorMessage = ErrorMessage.builder()
            .errorCode(VALIDATION_ERRORS.code)
            .errorDescription(ex.getMessage())
            .build();
        return new ResponseEntity<>(errorMessage, createCorrelationIdHeader(request), BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
            .errorCode(VALIDATION_ERRORS.code)
            .errorDescription(ex.getMessage())
            .build();
        return new ResponseEntity<>(errorMessage, createCorrelationIdHeader(request), BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorMessage errorMessage = mapToMessage(ex);
        return new ResponseEntity<>(errorMessage, createCorrelationIdHeader(request), NOT_FOUND);
    }

    @ExceptionHandler(FieldValidationException.class)
    public ResponseEntity<ErrorMessage> handleFieldValidationException(FieldValidationException ex, WebRequest request) {
        ErrorMessage errorMessage = mapToMessage(ex);
        return new ResponseEntity<>(errorMessage, createCorrelationIdHeader(request), BAD_REQUEST);
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ErrorMessage> handleServerException(ServerException ex, WebRequest request) {
        ErrorMessage errorMessage = mapToMessage(ex);
        return new ResponseEntity<>(errorMessage, createCorrelationIdHeader(request), INTERNAL_SERVER_ERROR);
    }

    private ErrorMessage mapToMessage(AppException exception) {
        return ErrorMessage.builder()
            .errorCode(exception.getError().code)
            .errorDescription(exception.getMessage())
            .build();
    }

    private HttpHeaders createCorrelationIdHeader(WebRequest request) {
        var requestId = request.getHeader(REQUEST_ID_HEADER);
        var headers = new HttpHeaders();
        headers.add(CORRELATION_ID_HEADER, requestId);
        return headers;
    }
}
