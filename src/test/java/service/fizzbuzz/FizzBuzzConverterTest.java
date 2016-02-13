package service.fizzbuzz;

import service.fizzbuzz.FizzBuzzConverter;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

public class FizzBuzzConverterTest {
    private final FizzBuzzConverter converter = new FizzBuzzConverter();

    @Test
    public void convert() throws Exception {
        assertEquals(emptyList(), converter.convert(emptyList()));
        assertEquals(asList("1", "2", "fizz"), converter.convert(asList(1, 2, 3)));
    }
}