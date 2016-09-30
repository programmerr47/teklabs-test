package api.service.calculator.exception;

public final class WrongParenthesisException extends WrongExpressionException {
    private static final String MESSAGE = "Something with parenthesis. Please, check input expression";

    public WrongParenthesisException() {
        super(MESSAGE);
    }

    public WrongParenthesisException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
