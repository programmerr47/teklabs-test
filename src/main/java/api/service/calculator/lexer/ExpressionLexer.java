package api.service.calculator.lexer;

import api.service.calculator.exception.IllegalTokenException;
import api.service.calculator.token.number.FastNumber;
import api.service.calculator.token.Token;
import api.service.calculator.token.number.TokenNumber;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public final class ExpressionLexer implements Lexer {
    private final SpecialTokens specialTokens;
    private List<Token> parsedTokens;

    public ExpressionLexer(Collection<Token> specialTokens) {
        this.specialTokens = new SpecialTokens(specialTokens);
    }

    @Override
    public Collection<Token> parse(String expression) {
        parsedTokens = new ArrayList<>();
        String localExpression = expression;
        while (!localExpression.isEmpty()) {
            if (startsWithNumber(localExpression)) {
                localExpression = cutOffNumber(localExpression);
            } else {
                localExpression = cutOffNewToken(localExpression);
            }
        }

        return parsedTokens;
    }

    private String cutOffNewToken(String expression) throws IllegalTokenException {
        if (specialTokens.canRecognizeStart(expression)) {
            return cutOff(expression, specialTokens::tokenFromStart);
        } else {
            throw new IllegalTokenException(expression);
        }
    }

    private String cutOffNumber(String expression) throws IllegalTokenException {
        return cutOff(expression,
                innerExpression -> new FastNumber(new TokenNumber(numberScanner(expression).next())));
    }

    private String cutOff(String expression, TokenExtractor extractor) throws IllegalTokenException {
        Token parsedToken = extractor.extract(expression);
        parsedTokens.add(parsedToken);
        return expression.substring(parsedToken.toString().length());
    }

    private boolean startsWithNumber(String expression) {
        Scanner numberScanner = numberScanner(expression);
        return numberScanner.hasNext() && expression.startsWith(numberScanner.next());
    }

    private Scanner numberScanner(String expression) {
        return new Scanner(expression).useDelimiter("[^\\d]+");
    }

    private interface TokenExtractor {
        Token extract(String expression) throws IllegalTokenException;
    }
}
