package api.service.calculator;

import api.service.Calculator;
import api.service.calculator.expression.builder.ReversePolishNotation;
import api.service.calculator.expression.builder.RndExpressionBuilder;
import api.service.calculator.lexer.ExpressionLexer;
import api.service.calculator.lexer.Lexer;
import api.service.calculator.lexer.TrimStringLexer;
import api.service.calculator.token.Token;
import api.service.calculator.token.operator.BinaryOperator;
import api.service.calculator.token.operator.MultiplyOperator;
import api.service.calculator.token.operator.SumOperator;

import java.util.Arrays;
import java.util.Collection;

import static api.service.calculator.token.bracket.RoundBracket.CLOSE_BRACKET;
import static api.service.calculator.token.bracket.RoundBracket.OPEN_BRACKET;

public class ExpressionCalculator implements Calculator {
    private final String expression;

    public ExpressionCalculator(String expression) {
        this.expression = expression;
    }

    @Override
    public int calculate() {
        BinaryOperator sum = new SumOperator();
        BinaryOperator mul = new MultiplyOperator();
        Lexer lexer = new TrimStringLexer(new ExpressionLexer(Arrays.asList(sum, mul, OPEN_BRACKET, CLOSE_BRACKET)));
        Collection<Token> tokens = lexer.parse(expression);
        Collection<Token> rndTokens = new ReversePolishNotation().fromInfix(tokens);
        return new RndExpressionBuilder(rndTokens).buildExpr().compute();
    }
}
