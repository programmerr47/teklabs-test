package api.security;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;


@Component
class AuthTokenService {

    @Autowired
    private StandardPBEStringEncryptor encryptor;

    @Value("${auth.token.expirationMinutes}")
    private Integer expirationMinutes;

    public AuthToken createAuthToken(String username) {
        String randomUUID = UUID.randomUUID().toString();
        Instant expirationDate = Instant.now().plusSeconds(expirationMinutes * 60);
        String rawValue = randomUUID + ":" + username + ":" + expirationDate.toEpochMilli();
        return new AuthToken(encryptor.encrypt(rawValue));
    }

    public String extractUsername(AuthToken authToken) {
        String decryptedValue = encryptor.decrypt(authToken.getValue());
        String[] split = decryptedValue.split(":");
        String username = split[1];
        Instant expirationDate = Instant.ofEpochMilli(Long.valueOf(split[2]));
        if (expirationDate.isBefore(Instant.now())) {
            return null;
        }
        return username;
    }
}
