package colt.models;

/**
 * Department - a data class that holds values that are
 * grouped together to make a department.
 *
 * @version 1.0 from 02.04.2021
 * @author Naglis Vidziunas
 */
public class Department {
  public String name;
  public int manager;
  public int cost;
  public int discount;

  /**
   * Builder - a class that corresponds to the Department
   * class. It helps to create a Department entry object given
   * required values, such as first name, last name,
   * date of birth, and so on.
   *
   * @version 1.0 from 02.04.2021
   * @author Naglis Vidziunas
   */
  public static class Builder {
    Department department = new Department();

    public Builder() {}

    public Builder name(String name) {
      department.name = name;
      return this;
    }

    public Builder manager(int manager) {
      department.manager = manager;
      return this;
    }

    public Builder cost(int cost) {
      department.cost = cost;
      return this;
    }

    public Builder discount(int discount) {
      department.discount = discount;
      return this;
    }

    public Department build() {
      return department;
    }
  }
}

