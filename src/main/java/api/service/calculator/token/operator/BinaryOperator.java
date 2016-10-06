package api.service.calculator.token.operator;

import api.service.calculator.expression.Expression;
import api.service.calculator.token.Token;
import api.service.calculator.token.TokenVisitor;

public interface BinaryOperator extends Token {
    int priority();
    Expression toExpression(Expression left, Expression right);

    @Override
    default void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
