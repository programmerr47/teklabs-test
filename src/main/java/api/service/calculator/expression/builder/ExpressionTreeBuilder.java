package api.service.calculator.expression.builder;

import api.service.calculator.expression.Expression;
import api.service.calculator.token.Token;

import java.util.Collection;

public interface ExpressionTreeBuilder {
    Expression buildExpr();
}
