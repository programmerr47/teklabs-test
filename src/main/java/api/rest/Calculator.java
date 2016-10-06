package api.rest;

import api.model.CalculatorExpression;
import api.service.calculator.token.operator.DefaultBinaryOperator;
import org.springframework.web.bind.annotation.*;

import static api.service.calculator.token.operator.DefaultBinaryOperator.MULT;
import static api.service.calculator.token.operator.DefaultBinaryOperator.SUM;

@RestController
@RequestMapping("calculator")
public class Calculator {
    private api.service.calculator.Calculator calculator;

    @RequestMapping(value = "/", consumes = "application/json", method = RequestMethod.POST)
    public Integer calculate(@RequestBody CalculatorExpression calculatorExpression) {
        return getOrInitCalculator().calculate(calculatorExpression.getExpression());
    }

    private api.service.calculator.Calculator getOrInitCalculator() {
        if (calculator == null) {
            calculator = new api.service.calculator.Calculator.Builder()
                    .operators(SUM, MULT)
                    .build();
        }

        return calculator;
    }
}
