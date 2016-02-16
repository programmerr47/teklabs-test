package api.rest;

import api.model.CalculatorExpression;
import api.service.calculator.RpnCalculator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("calculator")
public class Calculator {

    @RequestMapping(value = "/", consumes = "application/json", method = RequestMethod.POST)
    public Integer calculate(@RequestBody CalculatorExpression calculatorExpression) {
        RpnCalculator rpnCalculator = new RpnCalculator(calculatorExpression.getExpression());
        return rpnCalculator.calculate();
    }
}
