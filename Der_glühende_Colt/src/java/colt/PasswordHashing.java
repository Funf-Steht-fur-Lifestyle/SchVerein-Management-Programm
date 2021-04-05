package colt;

import java.math.*;
import java.security.*;
import java.security.spec.*;

import javax.crypto.*;
import javax.crypto.spec.*;

/**
 * PasswordHashing - a simple class that is responsible for
 * creating a hashed version with PBKDF2 Hashing algorithm
 * of the given password. Also it is responsible to
 * validate given password with the one stored in database.
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

  private String toHex(byte[] array) throws NoSuchAlgorithmException {
    BigInteger bi = new BigInteger(1, array);
    String hex = bi.toString(16);
    int paddingLength = (array.length * 2) - hex.length();

    if (paddingLength > 0) {
      return String.format("%0" + paddingLength + "d", 0) + hex;
    }

    return hex;
  }

  private byte[] fromHex(String hex) throws NoSuchAlgorithmException
  {
    byte[] bytes = new byte[hex.length() / 2];

    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
    }

    return bytes;
  }

  private byte[] createHash(Hash hash)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    PBEKeySpec spec = new PBEKeySpec(hash.password.toCharArray(), hash.salt, hash.iterations, hash.length * 8);
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] newHash = factory.generateSecret(spec).getEncoded();

    return newHash;
  }

  public String hash(String password) {
    int iterations = 65536;
    int length = 64;
    byte[] salt = createSalt();
    String hashedPassword = "";

    try {
      Hash hash = new Hash.Builder()
                          .password(password)
                          .salt(salt)
                          .iterations(iterations)
                          .length(length)
                          .build();

      byte[] createdHash = createHash(hash);
      hashedPassword = iterations + ":" + toHex(salt) + ":" + toHex(createdHash);
    } catch (NoSuchAlgorithmException e) {
      System.out.println(e.getMessage());
    } catch (InvalidKeySpecException ex) {
      System.out.println(ex.getMessage());
    }

    return hashedPassword;
  }

  public boolean validate(String originalPassword, String storedPassword)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    String[] passwordParts = storedPassword.split(":");
    int iterations = Integer.parseInt(passwordParts[0]);
    byte[] salt = fromHex(passwordParts[1]);
    byte[] realHash = fromHex(passwordParts[2]);

    Hash hash = new Hash.Builder()
                        .password(originalPassword)
                        .salt(salt)
                        .iterations(iterations)
                        .length(realHash.length)
                        .build();

    byte[] testHash = createHash(hash);
    int diff = realHash.length ^ testHash.length;

    for(int i = 0; i < realHash.length && i < testHash.length; i++) {
      diff |= realHash[i] ^ testHash[i];
    }

    return diff == 0;
  }
}
