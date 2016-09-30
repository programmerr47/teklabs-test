package api.service.calculator.expression.builder;

import api.service.calculator.exception.TooManyOperatorsException;
import api.service.calculator.expression.Expression;
import api.service.calculator.token.AbstractTokenVisitor;
import api.service.calculator.token.TokenVisitor;
import api.service.calculator.token.number.Number;
import api.service.calculator.token.operator.BinaryOperator;
import api.service.calculator.token.Token;

import java.util.Collection;
import java.util.Stack;

public final class RndExpressionBuilder implements ExpressionTreeBuilder {
    private final Collection<Token> tokens;
    private ThreadLocal<Stack<Expression>> exprStack;

    public RndExpressionBuilder(Collection<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public Expression buildExpr() {
        exprStack = ThreadLocal.withInitial(Stack::new);
        TokenVisitor tokenVisitor = new InputExpressionVisitor();
        tokens.forEach(token -> token.accept(tokenVisitor));
        return exprStack.get().pop();
    }

    private final class InputExpressionVisitor extends AbstractTokenVisitor {
        @Override
        public void visit(Number number) {
            exprStack.get().push(number.toExpression());
        }

        @Override
        public void visit(BinaryOperator operator) {
            Stack<Expression> stack = exprStack.get();
            if (stack.size() < 2) {
                throw new TooManyOperatorsException();
            } else {
                stack.push(operator.toExpression(stack.pop(), stack.pop()));
            }
        }
    }
}
