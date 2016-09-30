package api.service.calculator.lexer;

import api.service.calculator.exception.IllegalTokenException;
import api.service.calculator.token.Token;

import java.util.Collection;

public interface Lexer {
    Collection<Token> parse(String expression);
}
