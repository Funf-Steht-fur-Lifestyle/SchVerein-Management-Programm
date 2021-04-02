package colt;

import java.security.*;
import java.security.spec.*;

import javax.crypto.*;
import javax.crypto.spec.*;

/**
 * PasswordHashing - a simple class that is responsible for
 * creating a hashed version of the given password.
 *
 * @version 1.0 from 02.04.2021
 * @author Naglis Vidziunas
 */
public class PasswordHashing {
    private byte[] createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return salt;
    }

    public byte[] hash(String password) {
        byte[] salt = createSalt();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        byte[] hashedPassword = null;

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hashedPassword = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        } catch (InvalidKeySpecException ex) {
            System.out.println(ex.getMessage());
        }

        return hashedPassword;
    }
}
