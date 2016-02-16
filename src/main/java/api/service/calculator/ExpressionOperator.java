package api.service.calculator;

import java.util.HashMap;
import java.util.Map;

enum ExpressionOperator implements Operator {
    MULTIPLICATION("*", 0) {
        public int calculate(int a, int b) {
            return a * b;
        }
    },
    PLUS("+", 1) {
        public int calculate(int a, int b) {
            return a + b;
        }
    };

    private static Map<String, ExpressionOperator> operatorMap = new HashMap<>();

    static {
        for (ExpressionOperator operator : ExpressionOperator.values()) {
            operatorMap.put(operator.textForm, operator);
        }
    }

    private String textForm;
    private int priority;

    ExpressionOperator(String textForm, int priority) {
        this.textForm = textForm;
        this.priority = priority;
    }

    boolean hasMajorPriorityOver(ExpressionOperator operator) {
        return this.priority < operator.priority;
    }

    static ExpressionOperator fromText(String text) {
        return operatorMap.get(text);
    }
}
