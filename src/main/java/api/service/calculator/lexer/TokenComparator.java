package api.service.calculator.lexer;

import api.service.calculator.token.Token;

import java.util.Comparator;

enum TokenComparator implements Comparator<Token> {
    INSTANCE;

    @Override
    public int compare(Token o1, Token o2) {
        return o1.toString().compareTo(o2.toString());
    }
}
