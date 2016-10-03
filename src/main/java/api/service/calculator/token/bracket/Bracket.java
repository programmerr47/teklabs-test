package api.service.calculator.token.bracket;

import api.service.calculator.token.Token;

public interface Bracket extends Token {
    boolean isOpen();
    boolean isPairFor(Token another);
}
