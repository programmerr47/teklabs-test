package api.security;

public class AuthToken {
    private String value;

    AuthToken(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthToken authToken = (AuthToken) o;
        return value != null ? value.equals(authToken.value) : authToken.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
