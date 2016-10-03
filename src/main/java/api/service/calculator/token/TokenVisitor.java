package api.service.calculator.token;

import api.service.calculator.token.bracket.Bracket;
import api.service.calculator.token.number.Number;
import api.service.calculator.token.operator.BinaryOperator;

public interface TokenVisitor {
    void visit(Number number);
    void visit(BinaryOperator operator);
    void visit(Bracket bracket);
}
