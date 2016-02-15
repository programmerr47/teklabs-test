package api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
class AuthTokenService {
    private SecureRandom secureRandom = new SecureRandom();
    private Map<AuthToken, String> tokenUserMap = new ConcurrentHashMap<>();
    private Map<AuthToken, Instant> tokenExpirationMap = new ConcurrentHashMap<>();

    @Autowired
    private RestUserDetailsService userDetailsService;

    @Value("${auth.token.expirationMinutes}")
    private Integer expirationMinutes;

    public AuthToken registerUserToken(String username) {
        if (tokenUserMap.containsValue(username)) {
            removeUserToken(username);
        }
        AuthToken authToken = generateToken();
        tokenUserMap.put(authToken, username);
        registerTokenExpiration(authToken);
        return authToken;
    }

    private void removeUserToken(String username) {
        for (Map.Entry<AuthToken,String> entry : tokenUserMap.entrySet()) {
            if (entry.getValue().equals(username)) {
                tokenExpirationMap.remove(entry.getKey());
                break;
            }
        }
        tokenUserMap.values().remove(username);
    }

    private AuthToken generateToken() {
        String tokenValue = new BigInteger(130, secureRandom).toString(32);
        return new AuthToken(tokenValue);
    }

    private void registerTokenExpiration(AuthToken authToken) {
        Instant expiration = Instant.now().plusSeconds(expirationMinutes * 60);
        tokenExpirationMap.put(authToken, expiration);
    }

    public UserDetails getUserDetailsByToken(AuthToken authToken) {
        String username = tokenUserMap.get(authToken);
        if (username == null) {
            return null;
        }
        if (tokenIsExpired(authToken)) {
            tokenUserMap.remove(authToken);
            tokenExpirationMap.remove(authToken);
            return null;
        }
        return userDetailsService.loadUserByUsername(username);
    }

    private boolean tokenIsExpired(AuthToken authToken) {
        Instant expiration = tokenExpirationMap.get(authToken);
        return expiration.isBefore(Instant.now());
    }
}
