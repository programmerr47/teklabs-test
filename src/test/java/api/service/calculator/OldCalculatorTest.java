package api.service.calculator;

import api.service.calculator.token.operator.MultiplyOperator;
import api.service.calculator.token.operator.SumOperator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OldCalculatorTest {

    @Test
    public void calculate() throws Exception {
        assertEquals(100, new OldCalculator("1*2*3+4+9*10").calculate());
        assertEquals(15, new OldCalculator("15").calculate());
        assertEquals(10, new OldCalculator("12*0+10").calculate());
        assertEquals(1, new OldCalculator("1").calculate());
        assertEquals(1700, new OldCalculator("52+41*34+10*25+4").calculate());
        assertEquals(6, new OldCalculator("2+2*  2").calculate());
    }

    @Test
    public void calculate2() {
        assertEquals(100, new ExpressionCalculator("1*2*3+4+9*10").calculate());
        assertEquals(15, new ExpressionCalculator("15").calculate());
        assertEquals(10, new ExpressionCalculator("12*0+10").calculate());
        assertEquals(1, new ExpressionCalculator("1").calculate());
        assertEquals(1700, new ExpressionCalculator("52+41*34+10*25+4").calculate());
        assertEquals(6, new ExpressionCalculator("2+2*  2").calculate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyStringTest() throws Exception {
        new OldCalculator("").calculate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidExpressionTest() throws Exception {
        new OldCalculator("1 * asda - 5").calculate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void unsupportedOperationTest() throws Exception {
        new OldCalculator("1-2").calculate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void expressionEndWithOperator() throws Exception {
        new OldCalculator("5+").calculate();
    }
}