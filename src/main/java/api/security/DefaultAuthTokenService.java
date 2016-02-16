package api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class DefaultAuthTokenService implements AuthTokenService {
    private SecureRandom secureRandom = new SecureRandom();
    private Map<AuthToken, String> tokenUserMap = new ConcurrentHashMap<>();
    private Map<AuthToken, Instant> tokenExpirationMap = new ConcurrentHashMap<>();

    @Autowired
    UserDetailsService userDetailsService;

    @Value("${auth.token.expirationMinutes}")
    Integer expirationMinutes;

    @Override
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
        AuthToken token = tokenUserMap.entrySet().stream()
                .filter(e -> e.getValue().equals(username))
                .map(Map.Entry::getKey)
                .findFirst().orElse(null);

        tokenUserMap.values().remove(username);
        tokenExpirationMap.remove(token);
    }

    private AuthToken generateToken() {
        String tokenValue = new BigInteger(130, secureRandom).toString(32);
        return new AuthToken(tokenValue);
    }

    private void registerTokenExpiration(AuthToken authToken) {
        Instant expiration = Instant.now().plusSeconds(expirationMinutes * 60);
        tokenExpirationMap.put(authToken, expiration);
    }

    @Override
    public UserDetails getUserDetailsByToken(AuthToken authToken) {
        String username = tokenUserMap.get(authToken);
        if (username == null) {
            return null;
        }
        if (tokenIsExpired(authToken)) {
            unregisterToken(authToken);
            return null;
        }
        return userDetailsService.loadUserByUsername(username);
    }

    private boolean tokenIsExpired(AuthToken authToken) {
        Instant expiration = tokenExpirationMap.get(authToken);
        return expiration.isBefore(Instant.now());
    }

    @Override
    public void unregisterToken(AuthToken authToken) {
        tokenUserMap.remove(authToken);
        tokenExpirationMap.remove(authToken);
    }
}
