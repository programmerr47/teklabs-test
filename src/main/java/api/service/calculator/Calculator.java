package api.service.calculator;

import api.service.calculator.exception.IllegalTokenException;
import api.service.calculator.exception.TooManyOperatorsException;
import api.service.calculator.exception.WrongParenthesisException;
import api.service.calculator.expression.builder.ReversePolishNotation;
import api.service.calculator.expression.builder.RndExpressionBuilder;
import api.service.calculator.lexer.ExpressionLexer;
import api.service.calculator.lexer.Lexer;
import api.service.calculator.lexer.TrimStringLexer;
import api.service.calculator.token.Token;
import api.service.calculator.token.operator.BinaryOperator;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static api.service.calculator.token.Bracket.CLOSE_BRACKET;
import static api.service.calculator.token.Bracket.OPEN_BRACKET;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public final class Calculator {
    private final Collection<Token> grammarVocabulary;

    private Calculator(Collection<Token> vocabulary) {
        this.grammarVocabulary = vocabulary;
    }

    /**
     * Calculates expression that passed as string using given vocabulary
     * (operators, brackets and etc) and returns it as answer.
     *
     * @throws IllegalTokenException when using not registered tokens in expression
     *      (for example, pass "(1+2)*3" but not registered brackets in {@link Builder builder}
     *      through {@link Builder#useDefaultBrackets()})
     * @throws WrongParenthesisException when using not balanced brackets in expression
     * @throws TooManyOperatorsException when using to many operators in expression
     * (for example "3*+*+3", but not registering "*+*+" operator)
     */
    public int calculate(String exprStr) {
        Lexer lexer = new TrimStringLexer(new ExpressionLexer(grammarVocabulary));
        Collection<Token> tokens = lexer.parse(exprStr);
        Collection<Token> rndTokens = new ReversePolishNotation().fromInfix(tokens);
        return new RndExpressionBuilder(rndTokens).buildExpr().compute();
    }

    public static final class Builder {
        private Collection<BinaryOperator> operators = emptyList();
        private Collection<Token> brackets = emptyList();

        public Builder operators(BinaryOperator... operators) {
            this.operators = asList(operators);
            return this;
        }

        public Builder useDefaultBrackets() {
            this.brackets = asList(OPEN_BRACKET, CLOSE_BRACKET);
            return this;
        }

        public Calculator build() {
            Collection<Token> vocabulary =
                    Stream.concat(operators.stream(), brackets.stream())
                            .collect(Collectors.toSet());
            return new Calculator(vocabulary);
        }
    }
}
