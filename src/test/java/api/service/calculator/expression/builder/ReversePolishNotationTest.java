package api.service.calculator.expression.builder;

import api.service.calculator.exception.WrongParenthesisException;
import api.service.calculator.expression.builder.Notation;
import api.service.calculator.expression.builder.ReversePolishNotation;
import api.service.calculator.token.Bracket;
import api.service.calculator.token.Token;
import api.service.calculator.token.number.TokenNumber;
import api.service.calculator.token.operator.MultiplyOperator;
import api.service.calculator.token.operator.SumOperator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by m.spitsin on 30.09.2016.
 */
public class ReversePolishNotationTest {
    private final Notation notation = new ReversePolishNotation();

    @Test
    public void simpleSum() {
        List<Token> tokens = Arrays.asList(number(5), sum(), number(6));
        Collection<Token> expectedResult = Arrays.asList(number(5), number(6), sum());
        Collection<Token> actualResult = notation.fromInfix(tokens);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void simpleMultiplication() {
        List<Token> tokens = Arrays.asList(number(5), mul(), number(6));
        Collection<Token> expectedResult = Arrays.asList(number(5), number(6), mul());
        Collection<Token> actualResult = notation.fromInfix(tokens);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void twoSameOperators() {
        List<Token> tokens = Arrays.asList(number(5), mul(), number(6), mul(), number(8));
        Collection<Token> expectedResult = Arrays.asList(number(5), number(6), mul(), number(8), mul());
        Collection<Token> actualResult = notation.fromInfix(tokens);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void twoLowHighPrioritiesOperators() {
        List<Token> tokens = Arrays.asList(number(5), sum(), number(6), mul(), number(8));
        Collection<Token> expectedResult = Arrays.asList(number(5), number(6), number(8), mul(), sum());
        Collection<Token> actualResult = notation.fromInfix(tokens);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void twoHighLowPrioritiesOperators() {
        List<Token> tokens = Arrays.asList(number(5), mul(), number(6), sum(), number(8));
        Collection<Token> expectedResult = Arrays.asList(number(5), number(6), mul(), number(8), sum());
        Collection<Token> actualResult = notation.fromInfix(tokens);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = WrongParenthesisException.class)
    public void throwForNoLeftParenthesis() {
        List<Token> tokens = Arrays.asList(number(5), mul(), number(6), sum(), number(8), rb());
        notation.fromInfix(tokens);
    }

    @Test(expected = WrongParenthesisException.class)
    public void throwForNoRightParenthesis() {
        List<Token> tokens = Arrays.asList(lb(), number(5), mul(), number(6), sum(), number(8));
        notation.fromInfix(tokens);
    }

    @Test
    public void simpleBracket() {
        List<Token> tokens = Arrays.asList(lb(), lb(), number(5), rb(), sum(), number(6), rb());
        Collection<Token> expectedResult = Arrays.asList(number(5), number(6), sum());
        Collection<Token> actualResult = notation.fromInfix(tokens);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void complexBracket() {
        List<Token> tokens = Arrays.asList(
                lb(), lb(), number(1), sum(), number(5), rb(),
                mul(), lb(), lb(), number(5), rb(), sum(), number(6), rb(),
                sum(), number(3), rb(), mul(), number(10));
        Collection<Token> expectedResult = Arrays.asList(
                number(1), number(5), sum(), number(5), number(6), sum(),
                mul(), number(3), sum(), number(10), mul());
        Collection<Token> actualResult = notation.fromInfix(tokens);
        Assert.assertEquals(expectedResult, actualResult);
    }

    private Token number(int n) {
        return new TokenNumber(String.valueOf(n));
    }

    private Token sum() {
        return new SumOperator();
    }

    private Token mul() {
        return new MultiplyOperator();
    }

    private Token lb() {
        return Bracket.OPEN_BRACKET;
    }

    private Token rb() {
        return Bracket.CLOSE_BRACKET;
    }
}
