package api.service.calculator.token.operator;

import api.service.calculator.expression.Expression;
import api.service.calculator.expression.MultiplyExpression;
import api.service.calculator.expression.SumExpression;

public enum DefaultBinaryOperator implements BinaryOperator {
    SUM("+", 0) {
        @Override
        public Expression toExpression(Expression left, Expression right) {
            return new SumExpression(left, right);
        }
    }, MULT("*", 1) {
        @Override
        public Expression toExpression(Expression left, Expression right) {
            return new MultiplyExpression(left, right);
        }
    };

    private final String textForm;
    private final int priority;

    DefaultBinaryOperator(String textForm, int priority) {
        this.textForm = textForm;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return textForm;
    }

    @Override
    public int priority() {
        return priority;
    }
}
