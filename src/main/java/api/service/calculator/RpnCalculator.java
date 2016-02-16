package api.service.calculator;

import java.util.Stack;

public class RpnCalculator {
    private String expression;
    private char[] exprChars;
    private int pointer = 0;

    private Stack<Integer> numberStack = new Stack<>();
    private Stack<ExpressionOperator> operatorStack = new Stack<>();

    public RpnCalculator(String expression) {
        if (expression == null) {
            throw new IllegalArgumentException("Expression have to be not null");
        }
        expression = expression.replaceAll("\\s+", "");
        this.expression = expression;
        this.exprChars = expression.toCharArray();
    }

    public int calculate() {
        numberStack.add(readNextNumber());
        while (!isEndOfExpression()) {
            addOperatorToStack(readNextOperator());
            numberStack.add(readNextNumber());
        }
        return applyRemainingOperators();
    }

    private boolean isEndOfExpression() {
        return pointer >= expression.length();
    }

    private Integer readNextNumber() {
        StringBuilder sb = new StringBuilder();
        while (!isEndOfExpression() && Character.isDigit(exprChars[pointer])) {
            sb.append(exprChars[pointer++]);
        }
        return tryToExtractInteger(sb.toString());
    }

    private Integer tryToExtractInteger(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Incorrect expression format", e);
        }
    }

    private void addOperatorToStack(ExpressionOperator candidate) {
        while (!operatorStack.isEmpty()) {
            ExpressionOperator currentTop = operatorStack.peek();
            if (currentTop.hasMajorPriorityOver(candidate)) {
                applyOperatorToNumbers(operatorStack.pop());
            } else {
                break;
            }
        }
        operatorStack.add(candidate);
    }

    private void applyOperatorToNumbers(ExpressionOperator operator) {
        Integer calculationResult = operator.calculate(numberStack.pop(), numberStack.pop());
        numberStack.push(calculationResult);
    }

    private ExpressionOperator readNextOperator() {
        String textOperator = Character.toString(exprChars[pointer++]);
        ExpressionOperator operator = ExpressionOperator.fromText(textOperator);
        if (operator == null) {
            throw new IllegalArgumentException(String.format("Incorrect expression format: " +
                    " character '%s' is not supported", textOperator));
        }
        return operator;
    }

    private int applyRemainingOperators() {
        while (!operatorStack.isEmpty()) {
            applyOperatorToNumbers(operatorStack.pop());
        }
        return numberStack.pop();
    }
}
