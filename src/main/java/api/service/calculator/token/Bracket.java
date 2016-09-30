package api.service.calculator.token;

public enum Bracket implements Token {
    OPEN_BRACKET("("),
    CLOSE_BRACKET(")");

    private String textForm;

    Bracket(String textForm) {
        this.textForm = textForm;
    }

    @Override
    public String toString() {
        return textForm;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
