package api.service.calculator;

import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EnhancedStack<T> extends Stack<T> {

    public synchronized void popEach(Consumer<T> action) {
        popUntil(action, t -> false);
    }

    public synchronized void popUntil(Consumer<T> action, Predicate<T> endPredicate) {
        while (!isEmpty() && !endPredicate.test(peek())) {
            T elem = pop();
            action.accept(elem);
        }
    }
}
