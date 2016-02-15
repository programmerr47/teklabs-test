package api.security.handler;

import api.security.AuthToken;
import api.security.AuthTokenExtractor;
import api.security.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private AuthTokenService authTokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        AuthToken authToken = AuthTokenExtractor.extractFromRequest(request);
        authTokenService.unregisterToken(authToken);
    }
}
