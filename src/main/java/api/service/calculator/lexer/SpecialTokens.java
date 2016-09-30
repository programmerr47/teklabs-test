package api.service.calculator.lexer;

import api.service.calculator.exception.IllegalTokenException;
import api.service.calculator.token.Token;

import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

final class SpecialTokens {
    private final TreeSet<Token> tokens;

    public SpecialTokens(Token... tokens) {
        this.tokens = new TreeSet<>(TokenComparator.INSTANCE);
        this.tokens.addAll(asList(tokens));
    }

    public boolean canRecognizeStart(String expression) {
        return appropriateTokens(expression).count() != 0;
    }

    public Token tokenFromStart(String expression) throws IllegalTokenException {
        Optional<Token> optional = appropriateTokens(expression).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new IllegalTokenException(expression);
        }
    }

    private Stream<Token> appropriateTokens(String expression) {
        return tokens.stream().filter(token -> expression.startsWith(token.toString()));
    }
}
