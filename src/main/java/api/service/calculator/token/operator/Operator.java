package api.service.calculator.token.operator;

import api.service.calculator.token.Token;

public interface Operator extends Token {
    int priority();
}
