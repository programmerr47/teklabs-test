package api.service.calculator.token.operator;

import api.service.calculator.expression.Expression;
import api.service.calculator.expression.MultiplyExpression;

public final class MultiplyOperator extends BinaryOperatorImpl {
    public MultiplyOperator() {
        super("*", 1);
    }

    @Override
    public Expression toExpression(Expression left, Expression right) {
        return new MultiplyExpression(left, right);
    }
}
