package api.rest;

import api.model.CalculatorExpression;
import api.service.calculator.OldCalculator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("calculator")
public class Calculator {

    @RequestMapping(value = "/", consumes = "application/json", method = RequestMethod.POST)
    public Integer calculate(@RequestBody CalculatorExpression calculatorExpression) {
        OldCalculator oldCalculator = new OldCalculator(calculatorExpression.getExpression());
        return oldCalculator.calculate();
    }
}
