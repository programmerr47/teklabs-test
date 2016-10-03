package api.service.calculator;

import api.service.calculator.exception.IllegalTokenException;
import api.service.calculator.token.operator.MultiplyOperator;
import api.service.calculator.token.operator.SumOperator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
    public void calculate() {
        Calculator calculator = new Calculator.Builder()
                .operators(new SumOperator(), new MultiplyOperator())
                .useDefaultBrackets()
                .build();
        assertEquals(100, calculator.calculate("1*2*3+4+9*10"));
        assertEquals(15, calculator.calculate("15"));
        assertEquals(10, calculator.calculate("12*0+10"));
        assertEquals(1, calculator.calculate("1"));
        assertEquals(1700, calculator.calculate("52+41*34+10*25+4"));
        assertEquals(6, calculator.calculate("2+2*  2"));
    }

    @Test
    public void calculateWithBrackets() {
        Calculator calculator = new Calculator.Builder()
                .operators(new SumOperator(), new MultiplyOperator())
                .useDefaultBrackets()
                .build();
        assertEquals(320, calculator.calculate("1*2*(3+4+9)*10"));
    }

    @Test(expected = IllegalTokenException.class)
    public void calculateBracketExpressionOnNonBracketCalculator() {
        Calculator calculator = new Calculator.Builder()
                .operators(new SumOperator(), new MultiplyOperator())
                .build();
        assertEquals(320, calculator.calculate("1*2*(3+4+9)*10"));
    }
}
