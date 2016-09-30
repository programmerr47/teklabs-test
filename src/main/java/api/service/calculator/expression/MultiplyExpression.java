package api.service.calculator.expression;

public final class MultiplyExpression implements Expression {
    private final Expression leftOperand;
    private final Expression rightOperand;

    public MultiplyExpression(Expression leftOperand, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public int compute() {
        return leftOperand.compute() * rightOperand.compute();
    }
}
