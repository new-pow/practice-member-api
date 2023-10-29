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

    private final String encodeAlgorithm; // 알고리즘 이름
    private final int iterations; // 반복 횟수
    private final int keyLength; // 키 길이

    public Pbkdf2Encoder(
            @Value("${password.encode.algorithm}") String encodeAlgorithm,
            @Value("${password.encode.iterations}") int iterations,
            @Value("${password.encode.keyLength}") int keyLength) {
        this.encodeAlgorithm = encodeAlgorithm;
        this.iterations = iterations;
        this.keyLength = keyLength;
    }

    @Override
    public Password encrypt(String password) {
        byte[] saltBytes = generateSalt();
        return encrypt(password, saltBytes);
    }

    @Override
    public Password encrypt(String password, byte[] saltBytes) {
        byte[] hashBytes;
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, keyLength);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(encodeAlgorithm);
            hashBytes = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Password encryption failed.", e);
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
