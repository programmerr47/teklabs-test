package api.fizzbuzz;

import org.junit.Test;

import java.util.Collections;

import static java.util.Collections.emptyList;
import static org.junit.Assert.*;

public class FizzBuzzConverterTest {
    private final FizzBuzzConverter converter = new FizzBuzzConverter();

    @Test
    public void convert() throws Exception {
        assertEquals(emptyList(), converter.convert(Collections.<Integer>emptyList()));
    }
}