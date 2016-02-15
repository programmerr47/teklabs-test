package api.rest;

import api.service.ListConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("fizz-buzz")
public class FizzBuzz {

    @Autowired
    private ListConverter<Integer, String> fizzBuzzConverter;

    @RequestMapping(value = "/", consumes = "application/json", method = RequestMethod.POST)
    public List<String> fizzBuzzConversion(@RequestBody List<Integer> numbers) {
        return fizzBuzzConverter.convert(numbers);
    }
}
