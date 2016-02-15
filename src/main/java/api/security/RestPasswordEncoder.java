package api.security;

import org.jasypt.exceptions.EncryptionInitializationException;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RestPasswordEncoder implements PasswordEncoder {

    @Autowired
    private StrongPasswordEncryptor sha256Encryptor;

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            return sha256Encryptor.checkPassword(rawPassword.toString(), encodedPassword);
        } catch (EncryptionInitializationException | EncryptionOperationNotPossibleException e) {
            return false;
        }
    }
}