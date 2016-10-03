package api.service.calculator.exception;

public final class TooManyOperatorsException extends WrongExpressionException {
    private static final String MESSAGE = "Too many operators in expression";

    public TooManyOperatorsException() {
        super(MESSAGE);
    }
}
