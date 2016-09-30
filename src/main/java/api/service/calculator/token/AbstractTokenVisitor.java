package api.service.calculator.token;

import api.service.calculator.token.number.Number;
import api.service.calculator.token.operator.BinaryOperator;

public abstract class AbstractTokenVisitor implements TokenVisitor {
    @Override
    public void visit(Number number) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(BinaryOperator operator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(Bracket bracket) {
        throw new UnsupportedOperationException();
    }
}
