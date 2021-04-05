package colt;

/**
 * Member - a data class that holds values that are
 * grouped together to make a member.
 *
 * @version 1.0 from 02.04.2021
 * @author Naglis Vidziunas
 */
public class Member {
  public String firstName;
  public String lastName;
  public String dateOfBirth;
  public String iban;
  public String sex;
  public String disabilities;
  public int boardMember;
  public String entranceDate;
  public String leavingDate;
  public String notes;

  /**
   * Builder - a class that corresponds to the Member
   * class. It helps to create a Member entry object given
   * required values, such as first name, last name,
   * date of birth, and so on.
   *
   * @version 1.0 from 02.04.2021
   * @author Naglis Vidziunas
   */
  public static class Builder {
    Member member = new Member();

    public Builder() {}

    public Builder firstName(String firstName) {
      member.firstName = firstName;
      return this;
    }

    public Builder lastName(String lastName) {
      member.lastName = lastName;
      return this;
    }

    public Builder dateOfBirth(String dateOfBirth) {
      member.dateOfBirth = dateOfBirth;
      return this;
    }

    public Builder iban(String iban) {
      member.iban = iban;
      return this;
    }

    public Builder sex(String sex) {
      member.sex = sex;
      return this;
    }

    public Builder disabilities(String disabilities) {
      member.disabilities = disabilities;
      return this;
    }

    public Builder boardMember(int boardMember) {
      member.boardMember = boardMember;
      return this;
    }

    public Builder entranceDate(String entranceDate) {
      member.entranceDate = entranceDate;
      return this;

    }

    public Builder leavingDate(String leavingDate) {
      member.leavingDate = leavingDate;
      return this;
    }

    public Builder notes(String notes) {
      member.notes = notes;
      return this;
    }

    public Member build() {
      return member;
    }
  }
}

