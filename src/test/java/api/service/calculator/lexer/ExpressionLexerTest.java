package api.service.calculator.lexer;

import api.service.calculator.expression.Expression;
import api.service.calculator.token.Token;
import api.service.calculator.token.number.FastNumber;
import api.service.calculator.token.number.TokenNumber;
import api.service.calculator.token.operator.BinaryOperator;
import api.service.calculator.token.operator.DefaultBinaryOperator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

import static java.util.Arrays.asList;

public class ExpressionLexerTest {

    @Test
    public void expressionWithSimilarOperators() {
        Lexer lexer = new ExpressionLexer(asList(sum(), summul(), summulsin()));
        Collection<Token> result = lexer.parse("5+*6+*sin10");
        Collection<Token> expected = asList(num(5), summul(), num(6), summulsin(), num(10));
        Assert.assertEquals(expected, result);
    }

    private Token num(int num) {
        return new FastNumber(new TokenNumber(String.valueOf(num)));
    }

    private Token sum() {
        return DefaultBinaryOperator.SUM;
    }

    private Token summul() {
        return new CustomOperator("+*");
    }

    private Token summulsin() {
        return new CustomOperator("+*sin");
    }

    private static final class CustomOperator implements BinaryOperator {
        private final String textForm;

        public CustomOperator(String textForm) {
            this.textForm = textForm;
        }

        @Override
        public int priority() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Expression toExpression(Expression left, Expression right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return textForm;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CustomOperator that = (CustomOperator) o;

            return textForm.equals(that.textForm);

        }

        @Override
        public int hashCode() {
            return textForm.hashCode();
        }
    }
}
