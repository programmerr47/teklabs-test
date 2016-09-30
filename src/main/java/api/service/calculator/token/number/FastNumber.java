package api.service.calculator.token.number;

public final class FastNumber implements Number {
    private final Number origin;
    private int cachedResult;
    private boolean calculated;

    public FastNumber(Number origin) {
        this.origin = origin;
    }

    @Override
    public int get() {
        if (!calculated) {
            cachedResult = origin.get();
            calculated = true;
        }

        return cachedResult;
    }

    @Override
    public String toString() {
        return origin.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FastNumber that = (FastNumber) o;

        return origin.equals(that.origin);

    }

    @Override
    public int hashCode() {
        return origin.hashCode();
    }
}
