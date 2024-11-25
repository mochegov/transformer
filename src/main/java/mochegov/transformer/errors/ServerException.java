package mochegov.transformer.errors;

public class ServerException extends AppException {
  public ServerException(String message, ErrorType error) {
    super(message, error);
  }

  public ServerException(String message, Throwable cause, ErrorType error) {
    super(message, cause, error);
  }
}
