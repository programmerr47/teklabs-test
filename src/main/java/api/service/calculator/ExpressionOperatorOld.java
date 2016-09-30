package api.service.calculator;

import java.util.HashMap;
import java.util.Map;

enum ExpressionOperatorOld implements OperatorOld {
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

    private static Map<String, ExpressionOperatorOld> operatorMap = new HashMap<>();

    static {
        for (ExpressionOperatorOld operator : ExpressionOperatorOld.values()) {
            operatorMap.put(operator.textForm, operator);
        }
    }

    private String textForm;
    private int priority;

    ExpressionOperatorOld(String textForm, int priority) {
        this.textForm = textForm;
        this.priority = priority;
    }

    boolean hasMajorPriorityOver(ExpressionOperatorOld operator) {
        return this.priority < operator.priority;
    }

    static ExpressionOperatorOld fromText(String text) {
        return operatorMap.get(text);
    }
}
