package api.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthTokenService {

    AuthToken registerUserToken(String username);

    UserDetails getUserDetailsByToken(AuthToken authToken);

    void unregisterToken(AuthToken authToken);

}
