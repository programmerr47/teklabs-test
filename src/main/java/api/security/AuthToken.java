package api.security;

class AuthToken {
    private String value;

    public AuthToken(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
