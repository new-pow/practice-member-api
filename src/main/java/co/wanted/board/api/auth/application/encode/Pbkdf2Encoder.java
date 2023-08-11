package co.wanted.board.api.auth.application.encode;

import co.wanted.board.api.member.domain.Password;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class Pbkdf2Encoder implements PasswordEncoder {

    private final String encodeAlgorithm;

    public Pbkdf2Encoder(@Value(value = "${password.encode.algorithm}") String encodeAlgorithm) {
        this.encodeAlgorithm = encodeAlgorithm;
    }

    @Override
    public Password encrypt(String password) {
        byte[] saltBytes = getSalt();
        return encrypt(password, saltBytes);
    }

    public Password encrypt(String password, byte[] saltBytes) {
        byte[] hashBytes = new byte[128];
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(encodeAlgorithm);
            hashBytes = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        String hash = new String(Base64.getEncoder().encode(hashBytes));
        return Password.of(hash, saltBytes);
    }

    private byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

}
