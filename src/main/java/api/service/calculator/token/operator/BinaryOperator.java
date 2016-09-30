package api.service.calculator.token.operator;

import api.service.calculator.expression.Expression;
import api.service.calculator.token.TokenVisitor;

public interface BinaryOperator extends Operator {
    Expression toExpression(Expression left, Expression right);

    @Override
    default void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
