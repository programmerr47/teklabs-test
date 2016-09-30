package api.service.calculator.lexer;

import api.service.calculator.token.Token;

import java.util.Collection;

public final class TrimStringLexer implements Lexer {
    private final Lexer origin;

    public TrimStringLexer(Lexer origin) {
        this.origin = origin;
    }

    @Override
    public Collection<Token> parse(String expression) {
        String trimmedExpr = expression.replaceAll(" ", "");
        return origin.parse(trimmedExpr);
    }
}
