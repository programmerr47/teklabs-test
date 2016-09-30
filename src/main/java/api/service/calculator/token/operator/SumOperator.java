package api.service.calculator.token.operator;

import api.service.calculator.expression.Expression;
import api.service.calculator.expression.SumExpression;

public final class SumOperator extends BinaryOperatorImpl {
    public SumOperator() {
        super("+", 0);
    }

    @Override
    public Expression toExpression(Expression left, Expression right) {
        return new SumExpression(left, right);
    }
}
