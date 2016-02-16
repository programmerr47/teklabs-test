package api.rest;

import api.model.CalculatorExpression;
import api.service.calculator.RpnCalculator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("calculator")
public class Calculator {

    @RequestMapping(value = "/", consumes = "application/json", method = RequestMethod.POST)
    public Integer calculate(@RequestBody CalculatorExpression calculatorExpression) {
        RpnCalculator rpnCalculator = new RpnCalculator(calculatorExpression.getExpression());
        return rpnCalculator.calculate();
    }
}
