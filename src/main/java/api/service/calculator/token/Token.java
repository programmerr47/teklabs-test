package api.service.calculator.token;

public interface Token {
    void accept(TokenVisitor visitor);
}
