package api.service.calculator.token.number;

import api.service.calculator.expression.Expression;
import api.service.calculator.token.Token;
import api.service.calculator.token.TokenVisitor;

public interface Number extends Token {
    int get();

    @Override
    default void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    default Expression toExpression() {
        return this::get;
    }
}
