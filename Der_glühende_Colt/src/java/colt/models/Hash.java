package colt.models;

/**
 * Hash - a data class that holds values that are
 * grouped together to make a hash.
 *
 * @version 1.0 from 05.04.2021
 * @author Naglis Vidziunas
 */
public class Hash {
  public String password;
  public byte[] salt;
  public int iterations;
  public int length;

  /**
   * Builder - a class that corresponds to the Hash
   * class. It helps to create a Hash entry object
   * given required values, such as password, salt,
   * length, and so on.
   *
   * @version 1.0 from 05.04.2021
   * @author Naglis Vidziunas
   */
  public static class Builder {
    Hash hash = new Hash();

    public Builder() {}

    public Builder password(String password) {
      hash.password = password;
      return this;
    }

    public Builder salt(byte[] salt) {
      hash.salt = salt;
      return this;
    }

    public Builder iterations(int iterations) {
      hash.iterations = iterations;
      return this;
    }

    public Builder length(int length) {
      hash.length = length;
      return this;
    }

    public Hash build() {
      return hash;
    }
  }
}

