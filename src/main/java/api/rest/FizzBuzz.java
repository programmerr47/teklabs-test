package api.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FizzBuzz {

    @RequestMapping("/hello")
    public String hello() {
        return "Fizz-buzz";
    }
}
