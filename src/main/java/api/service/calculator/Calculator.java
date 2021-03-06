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
import api.service.calculator.token.bracket.Bracket;
import api.service.calculator.token.bracket.RoundBracket;
import api.service.calculator.token.operator.BinaryOperator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     *      through {@link Builder#withDefaultBrackets()}) or {@link Builder#withBrackets(Bracket, Bracket)}
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
        private Collection<Token> brackets = new ArrayList<>();

        public Builder operators(BinaryOperator... operators) {
            this.operators = asList(operators);
            return this;
        }

        public Builder withDefaultBrackets() {
            this.brackets.addAll(asList(RoundBracket.OPEN, RoundBracket.CLOSE));
            return this;
        }

        public Builder withBrackets(Bracket open, Bracket close) {
            this.brackets.addAll(asList(open, close));
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
