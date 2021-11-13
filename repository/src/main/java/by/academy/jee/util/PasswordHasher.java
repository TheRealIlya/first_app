package by.academy.jee.util;

import by.academy.jee.exception.PasswordHashingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static by.academy.jee.constant.Constant.HASHING_ALGORITHM;
import static by.academy.jee.constant.Constant.SECURE_RANDOM_TYPE;
import static by.academy.jee.constant.Constant.WRONG_ALGORITHM;
import static by.academy.jee.constant.Constant.WRONG_ENCRYPTING_ALGORITHMS;

public class PasswordHasher {

    private static final Logger log = LoggerFactory.getLogger(PasswordHasher.class);


    private PasswordHasher() {
        //util class
    }

    public static byte[] generateSalt() {
        byte[] salt = new byte[8];
        try {
            SecureRandom random = SecureRandom.getInstance(SECURE_RANDOM_TYPE);
            random.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            logAndThrowMyException(WRONG_ALGORITHM, e);
        }
        return salt;
    }

    public static byte[] getEncryptedPassword(String password, byte[] salt) {
        String algorithm = HASHING_ALGORITHM;
        int derivedKeyLength = 160;
        int iterations = 20000;
        byte[] pwd = new byte[8];
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
            SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
            pwd = f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logAndThrowMyException(WRONG_ENCRYPTING_ALGORITHMS, e);
        }
        return pwd;
    }

    public static boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt) {
        byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
        return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
    }

    private static void logAndThrowMyException(String message, Exception e) {
        log.error(message, e);
        throw new PasswordHashingException(message, e);
    }
}
