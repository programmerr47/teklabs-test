package api.service.calculator.token.bracket;

import api.service.calculator.token.Token;
import api.service.calculator.token.TokenVisitor;

public enum RoundBracket implements Bracket {
    OPEN("(", true) {
        @Override
        public boolean isPairFor(Token another) {
            return another == CLOSE;
        }
    },
    CLOSE(")", false) {
        @Override
        public boolean isPairFor(Token another) {
            return another == OPEN;
        }
    };

    private final String textForm;
    private final boolean isOpen;

    RoundBracket(String textForm, boolean isOpen) {
        this.textForm = textForm;
        this.isOpen = isOpen;
    }

    @Override
    public String toString() {
        return textForm;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }
}
