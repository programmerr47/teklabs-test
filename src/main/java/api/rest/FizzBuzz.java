package api.rest;

import api.service.ListConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("fizz-buzz")
public class FizzBuzz {

    @Autowired
    private ListConverter<Integer, String> fizzBuzzConverter;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public List<String> fizzBuzzConversion(@RequestParam(value = "numbers") List<Integer> numbers) {
        return fizzBuzzConverter.convert(numbers);
    }
}
