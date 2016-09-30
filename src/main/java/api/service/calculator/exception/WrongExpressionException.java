package api.service.calculator.exception;

public abstract class WrongExpressionException extends RuntimeException {
    public WrongExpressionException(String message) {
        super(message);
    }

    public WrongExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
}
