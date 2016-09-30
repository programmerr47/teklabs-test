package api.service.calculator;

import api.service.Calculator;

import java.util.Stack;

public class OldCalculator implements Calculator {
    private String expression;
    private char[] exprChars;
    private int pointer = 0;

    private Stack<Integer> numberStack = new Stack<>();
    private Stack<ExpressionOperatorOld> operatorStack = new Stack<>();

    public OldCalculator(String expression) {
        if (expression == null) {
            throw new IllegalArgumentException("Expression have to be not null");
        }
        expression = expression.replaceAll("\\s+", "");
        this.expression = expression;
        this.exprChars = expression.toCharArray();
    }

    @Override
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

    private void addOperatorToStack(ExpressionOperatorOld candidate) {
        while (!operatorStack.isEmpty()) {
            ExpressionOperatorOld currentTop = operatorStack.peek();
            if (currentTop.hasMajorPriorityOver(candidate)) {
                applyOperatorToNumbers(operatorStack.pop());
            } else {
                break;
            }
        }
        operatorStack.add(candidate);
    }

    private void applyOperatorToNumbers(ExpressionOperatorOld operator) {
        Integer calculationResult = operator.calculate(numberStack.pop(), numberStack.pop());
        numberStack.push(calculationResult);
    }

    private ExpressionOperatorOld readNextOperator() {
        String textOperator = Character.toString(exprChars[pointer++]);
        ExpressionOperatorOld operator = ExpressionOperatorOld.fromText(textOperator);
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
