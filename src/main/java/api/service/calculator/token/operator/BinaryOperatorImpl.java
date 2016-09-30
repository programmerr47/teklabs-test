package api.service.calculator.token.operator;

abstract class BinaryOperatorImpl implements BinaryOperator {
    private final String textForm;
    private final int priority;

    protected BinaryOperatorImpl(String textForm, int priority) {
        this.textForm = textForm;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return textForm;
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinaryOperatorImpl that = (BinaryOperatorImpl) o;

        if (priority != that.priority) return false;
        return textForm.equals(that.textForm);
    }

    @Override
    public int hashCode() {
        int result = textForm.hashCode();
        result = 31 * result + priority;
        return result;
    }
}
