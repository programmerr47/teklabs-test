package api.service.calculator.exception;

public final class IllegalTokenException extends WrongExpressionException {
    private static final String MESSAGE = "There is no token for toString: ";

    public IllegalTokenException(String token) {
        super(MESSAGE + token);
    }
}
