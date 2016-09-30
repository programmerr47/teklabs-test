package api.service.calculator.token.number;

public final class TokenNumber implements Number {
    private final String numStr;

    public TokenNumber(String numStr) {
        this.numStr = numStr;
    }

    @Override
    public int get() {
        return Integer.parseInt(numStr);
    }

    @Override
    public String toString() {
        return numStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenNumber that = (TokenNumber) o;

        return numStr.equals(that.numStr);
    }

    @Override
    public int hashCode() {
        return numStr.hashCode();
    }
}
