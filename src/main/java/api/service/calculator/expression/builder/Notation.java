package api.service.calculator.expression.builder;

import api.service.calculator.token.Token;

import java.util.Collection;

public interface Notation {
    Collection<Token> fromInfix(Collection<Token> tokens);
}
