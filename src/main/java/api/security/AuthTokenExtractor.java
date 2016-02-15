package api.security;

import javax.servlet.http.HttpServletRequest;

public final class AuthTokenExtractor {

    private static final String TOKEN_HEADER = "X-Auth-Token";

    private AuthTokenExtractor() {
    }

    public static AuthToken extractFromRequest(HttpServletRequest request) {
        return new AuthToken(request.getHeader(TOKEN_HEADER));
    }
}
