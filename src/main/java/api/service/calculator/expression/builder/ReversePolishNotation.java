package api.service.calculator.expression.builder;

import api.service.calculator.EnhancedStack;
import api.service.calculator.exception.WrongParenthesisException;
import api.service.calculator.token.*;
import api.service.calculator.token.number.Number;
import api.service.calculator.token.operator.BinaryOperator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static api.service.calculator.token.Bracket.OPEN_BRACKET;

public final class ReversePolishNotation implements Notation {
    private List<Token> outputQueue;
    private EnhancedStack<Token> operatorStack;

    @Override
    public Collection<Token> fromInfix(Collection<Token> tokens) {
        outputQueue = new ArrayList<>();
        operatorStack = new EnhancedStack<>();

        TokenVisitor inputQueueVisitor = new InputQueueVisitor();
        tokens.forEach(token -> token.accept(inputQueueVisitor));

        TokenVisitor operatorStackVisitor = new CleanUpStackVisitor();
        operatorStack.popEach(token -> token.accept(operatorStackVisitor));

        return outputQueue;
    }

    private final class InputQueueVisitor implements TokenVisitor, OperationStackObserver {
        @Override
        public void visit(Number number) {
            outputQueue.add(number);
        }

        @Override
        public void visit(BinaryOperator operator) {
            if (operatorStack.empty()) {
                onPushToStack(operator);
            } else {
                operatorStack.peek().accept(new PopLowPriorityOperatorStackVisitor(operator, this));
            }
        }

        @Override
        public void visit(Bracket bracket) {
            if (bracket == OPEN_BRACKET) {
                operatorStack.push(bracket);
            } else {
                operatorStack.popUntil(
                        token -> outputQueue.add(token),
                        token -> token == OPEN_BRACKET);

                if (operatorStack.isEmpty()) {
                    throw new WrongParenthesisException();
                } else {
                    operatorStack.pop(); //remove open bracket corresponding close bracket
                }
            }
        }

        @Override
        public void onPopFromStack(BinaryOperator operator) {
            outputQueue.add(operatorStack.pop());
            visit(operator);
        }

        @Override
        public void onPushToStack(BinaryOperator operator) {
            operatorStack.push(operator);
        }
    }

    private abstract class OperatorStackVisitor extends AbstractTokenVisitor {
        @Override
        public void visit(Number number) {
            throw new IllegalStateException("This can not be true. Trust me! Visit number for operator stack. Ridiculous!");
        }
    }

    private final class CleanUpStackVisitor extends OperatorStackVisitor {
        @Override
        public void visit(BinaryOperator operator) {
            outputQueue.add(operator);
        }

        @Override
        public void visit(Bracket bracket) {
            throw new WrongParenthesisException();
        }
    }

    private final class PopLowPriorityOperatorStackVisitor extends OperatorStackVisitor {
        private final BinaryOperator newOperator;
        private final OperationStackObserver listener;

        public PopLowPriorityOperatorStackVisitor(BinaryOperator newOperator, OperationStackObserver listener) {
            this.newOperator = newOperator;
            this.listener = listener;
        }

        @Override
        public void visit(BinaryOperator operator) {
            if (newOperator.priority() <= operator.priority()) {
                listener.onPopFromStack(newOperator);
            } else {
                listener.onPushToStack(newOperator);
            }
        }

        @Override
        public void visit(Bracket bracket) {
            listener.onPushToStack(newOperator);
        }
    }

    interface OperationStackObserver {
        void onPopFromStack(BinaryOperator operator);
        void onPushToStack(BinaryOperator operator);
    }
}
