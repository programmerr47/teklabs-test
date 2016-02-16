package api.service.calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RpnCalculatorTest {

    @Test
    public void calculate() throws Exception {
        assertEquals(100, new RpnCalculator("1*2*3+4+9*10").calculate());
        assertEquals(15, new RpnCalculator("15").calculate());
        assertEquals(10, new RpnCalculator("12*0+10").calculate());
        assertEquals(1, new RpnCalculator("1").calculate());
        assertEquals(1700, new RpnCalculator("52+41*34+10*25+4").calculate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyStringTest() throws Exception {
        new RpnCalculator("").calculate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidExpressionTest() throws Exception {
        new RpnCalculator("1 * asda - 5").calculate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void unsupportedOperationTest() throws Exception {
        new RpnCalculator("1-2").calculate();
    }
}