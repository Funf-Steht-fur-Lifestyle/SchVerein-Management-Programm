package colt;

import java.sql.*;
import java.util.*;

import colt.models.*;

/**
 * Database - a class that is responsible for storing data
 * in and getting data from the database.
 *
 * @version 1.0 from 02.04.2021
 * @author Naglis Vidziunas / David Stuirbrink
 */
public class Database {
  public Connection connect() {
    Connection conn = null;

    try {
      Class.forName("org.sqlite.JDBC");
      conn = DriverManager.getConnection("jdbc:sqlite:data.db");
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    return conn;
  }

  // insert a new User
  public void insertUser(String username, String password) {
    String insertUserQuery = "INSERT INTO benutzer(benutzername,"
                           + " passwort) VALUES(?,?)";

    try {
      Connection conn = connect();
      PreparedStatement pStatement = conn.prepareStatement(insertUserQuery);

      pStatement.setString(1, username);
      pStatement.setString(2, password);
      pStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // select the User from the database
  public ArrayList<String> selectUser() {
    String selectUserQuery = "SELECT * FROM benutzer";
    ArrayList<String> user = new ArrayList<String>();

    try {
      Connection conn = connect();
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(selectUserQuery);

      while (rs.next()) {
        user.add(rs.getString(1));
        user.add(rs.getString(2));
        user.add(rs.getString(3));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return user;
  }

  // update the password of the Admin User
  public void updateUserInfo(String password, int id) {
    String updateUserInfoQuery = "UPDATE benutzer SET passwort = ? WHERE id = ?";

    try {
      Connection conn = connect();
      PreparedStatement pStatement = conn.prepareStatement(updateUserInfoQuery);

      pStatement.setString(1, password);
      pStatement.setInt(2, id);
      pStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // insert a new entry of a new member
  public void insertMitglieder(Member member, int addressID) {
    String insertMitgliederQuery = "INSERT INTO mitglieder("
                                 + "vorname, nachname, geburtstag, IBAN,"
                                 + " geschlecht, behinderungen,"
                                 + " vorstandsmitglied, eintrittsdatum,"
                                 + " austrittsdatum, vermerke, adresseID)"
                                 + " VALUES(?,?,?,?,?,?,?,?,?,?,?)";

    try {
      Connection conn = connect();
      PreparedStatement pStatement = conn.prepareStatement(insertMitgliederQuery);

      pStatement.setString(1, member.firstName);
      pStatement.setString(2, member.lastName);
      pStatement.setString(3, member.dateOfBirth);
      pStatement.setString(4, member.iban);
      pStatement.setString(5, member.sex);
      pStatement.setString(6, member.disabilities);
      pStatement.setInt(7, member.boardMember);
      pStatement.setString(8, member.entranceDate);
      pStatement.setString(9, member.leavingDate);
      pStatement.setString(10, member.notes);
      pStatement.setInt(11, addressID);
      pStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // get all members to show them in the Table
  public ArrayList<String[]> selectAllMitglieder() {
    String selectMitgliederQuery = "SELECT * FROM mitglieder";
    ArrayList<String[]> members = new ArrayList<String[]>();

    try {
      Connection conn = connect();
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(selectMitgliederQuery);
      int columnNumber = rs.getMetaData().getColumnCount();

      while (rs.next()) {
        String[] member = new String[columnNumber];

        for (int i = 1; i <= columnNumber; i++) {
          Object obj = rs.getObject(i);
          member[i - 1] = (obj == null) ? null : obj.toString();
        }
        members.add(member);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return members;
  }

  // update a member entry
  public void updateMitglieder(Member member, int id) {
    String updateMitgliederQuery = "UPDATE mitglieder SET vorname = ?, nachname = ?,"
                                 + " geburtstag = ?, IBAN = ?, behinderungen = ?,"
                                 + " vorstandsmitglied = ?, eintrittsdatum = ?,"
                                 + " austrittsdatum = ?, vermerke = ?"
                                 + " WHERE mitgliedernummer = ?";

    try {
      Connection conn = connect();
      PreparedStatement pStatement = conn.prepareStatement(updateMitgliederQuery);

      pStatement.setString(1, member.firstName);
      pStatement.setString(2, member.lastName);
      pStatement.setString(3, member.dateOfBirth);
      pStatement.setString(4, member.iban);
      pStatement.setString(5, member.disabilities);
      pStatement.setInt(6, member.boardMember);
      pStatement.setString(7, member.entranceDate);
      pStatement.setString(8, member.leavingDate);
      pStatement.setString(9, member.notes);
      pStatement.setInt(10, id);
      pStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // delete a member from the database
  public void deleteMitglieder(int id) {
    String deleteMitgliederQuery = "DELETE FROM mitglieder WHERE mitgliedernummer = ?";

    try {
      Connection conn = connect();
      PreparedStatement pStatement = conn.prepareStatement(deleteMitgliederQuery);

      pStatement.setInt(1, id);
      pStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // insert a new department
  public void insertAbteilung(Department department, int memberID, int delete) {
    String insertAbteilungQuery = "INSERT INTO abteilung("
                                 + "abteilungsname, abteilungsleiter, gebuehren,"
                                 + " rabatte, mitgliedernummer, geloescht)"
                                 + " VALUES(?,?,?,?,?,?)";

    try {
      Connection conn = connect();
      PreparedStatement pStatement = conn.prepareStatement(insertAbteilungQuery);

      pStatement.setString(1, department.name);
      pStatement.setInt(2, department.manager);
      pStatement.setInt(3, department.cost);
      pStatement.setInt(4, department.discount);
      pStatement.setInt(5, memberID);
      pStatement.setInt(6, delete);
      pStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // select all departments
  public ArrayList<String[]> selectAllAbteilung() {
    String selectAbteilungQuery = "SELECT * FROM abteilung";
    ArrayList<String[]> departments = new ArrayList<String[]>();

    try {
      Connection conn = connect();
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(selectAbteilungQuery);
      int columnNumber = rs.getMetaData().getColumnCount();

      while (rs.next()) {
        String[] department = new String[columnNumber];

        for (int i = 1; i <= columnNumber; i++) {
          Object obj = rs.getObject(i);
          department[i - 1] = (obj == null) ? null : obj.toString();
        }
        departments.add(department);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return departments;
  }

  // update a department in the database
  public void updateAbteilung(Department department, int memberID, int delete) {
    String updateAbteilungQuery = "UPDATE abteilung SET rabatte = ?, geloescht = ?"
                                 + " WHERE mitgliedernummer = ? AND abteilungsname = ?";

    try {
      Connection conn = connect();
      PreparedStatement pStatement = conn.prepareStatement(updateAbteilungQuery);

      pStatement.setInt(1, department.discount);
      pStatement.setInt(2, delete);
      pStatement.setInt(3, memberID);
      pStatement.setString(4, department.name);
      pStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // delete a department from the database
  public void deleteAbteilung(String department, int memberID, int delete) {
    String deleteAbteilungQuery = "UPDATE abteilung SET geloescht = ?"
                                + " WHERE abteilungsname = ? AND mitgliedernummer = ?";

    try {
      Connection conn = connect();
      PreparedStatement pStatement = conn.prepareStatement(deleteAbteilungQuery);

      pStatement.setInt(1, delete);
      pStatement.setString(2, department);
      pStatement.setInt(3, memberID);
      pStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // insert a new address into the database
  public void insertAdresse(Address address) {
    String insertAdresseQuery = "INSERT INTO adresse("
                                 + "Strasse, HN, HNZusatz, PLZ,"
                                 + " Ort, Land, Bundesland)"
                                 + " VALUES(?,?,?,?,?,?,?)";

    try {
      Connection conn = connect();
      PreparedStatement pStatement = conn.prepareStatement(insertAdresseQuery);

      pStatement.setString(1, address.street);
      pStatement.setInt(2, address.houseNumber);
      pStatement.setString(3, address.houseNumberAdditional);
      pStatement.setInt(4, address.postcode);
      pStatement.setString(5, address.location);
      pStatement.setString(6, address.country);
      pStatement.setString(7, address.state);
      pStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // select all addresses from the database
  public ArrayList<String[]> selectAllAdresse() {
    String selectAdresseQuery = "SELECT * FROM adresse";
    ArrayList<String[]> addresses = new ArrayList<String[]>();

    try {
      Connection conn = connect();
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(selectAdresseQuery);

      int columnNumber = rs.getMetaData().getColumnCount();

      while (rs.next()) {
        String[] address = new String[columnNumber];

        for (int i = 1; i <= columnNumber; i++) {
          Object obj = rs.getObject(i);
          address[i - 1] = (obj == null) ? null : obj.toString();
        }

        addresses.add(address);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return addresses;
  }

  // update an adress entry
  public void updateAdresse(Address address, int addressID) {
    String updateAdresseQuery = "UPDATE adresse SET Strasse = ?, HN = ?,"
                                 + " HNZusatz = ?, PLZ = ?, Ort = ?, Land = ?,"
                                 + " Bundesland = ? WHERE adresseID = ?";

    try {
      Connection conn = connect();
      PreparedStatement pStatement = conn.prepareStatement(updateAdresseQuery);

      pStatement.setString(1, address.street);
      pStatement.setInt(2, address.houseNumber);
      pStatement.setString(3, address.houseNumberAdditional);
      pStatement.setInt(4, address.postcode);
      pStatement.setString(5, address.location);
      pStatement.setString(6, address.country);
      pStatement.setString(7, address.state);
      pStatement.setInt(8, addressID);
      pStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // delete an adress from the database
  public void deleteAdresse(int id) {
    String deleteAdresseQuery = "DELETE FROM adresse WHERE adresseID = ?";

    try {
      Connection conn = connect();
      PreparedStatement pStatement = conn.prepareStatement(deleteAdresseQuery);

      pStatement.setInt(1, id);
      pStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void setAsPresent(int memberID, String date) {
    String presentQuery = "INSERT INTO anwesenheit(mitgliedernummer, andatum)"
                        + " VALUES (?,?)";

    try {
      Connection conn = connect();
      PreparedStatement pStatement = conn.prepareStatement(presentQuery);

      pStatement.setInt(1, memberID);
      pStatement.setString(2, date);
      pStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // select all members to mark their attendance
  public ArrayList<String[]> selectAllPresent() {
    String selectPresentQuery = "SELECT * FROM anwesenheit";
    ArrayList<String[]> presentMembers = new ArrayList<String[]>();

    try {
      Connection conn = connect();
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(selectPresentQuery);
      int columnNumber = rs.getMetaData().getColumnCount();

      while (rs.next()) {
        String[] presentMember = new String[columnNumber];

        for (int i = 1; i <= columnNumber; i++) {
          Object obj = rs.getObject(i);
          presentMember[i - 1] = (obj == null) ? null : obj.toString();
        }
        presentMembers.add(presentMember);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return presentMembers;
  }

  public void deletePresent(int memberID, String date) {
    String deletePresentQuery = "DELETE FROM anwesenheit WHERE mitgliedernummer = ? AND andatum = ?";

    try {
      Connection conn = connect();
      PreparedStatement pStatement = conn.prepareStatement(deletePresentQuery);

      pStatement.setInt(1, memberID);
      pStatement.setString(2, date);
      pStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }


  /**
   * selectAttendanceTimes
   * Selects all the attendance times from a specific member
   * (lets pray this works)
   *
   * @version 1.0 from 10.04.2021
   * @author Eric Becker
   */
  public ArrayList<String> selectAttendanceTimes(int memberID) {
    String selectMitgliederQuery = String.format("SELECT andatum FROM anwesenheit WHERE mitgliedernummer = %d", memberID);
    ArrayList<String> members = new ArrayList<String>();

    try {
      Connection conn = connect();

      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(selectMitgliederQuery);

      while (rs.next()) {
        String member = "";
        Object obj = rs.getObject(1);
        member = (obj == null) ? null : obj.toString();
        members.add(member);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return members;
  }

  public String selectEintrittsdatum(int memberID) {
    String selectEintrittsdatumQuery = String.format("SELECT eintrittsdatum FROM mitglieder WHERE mitgliedernummer = %d;", memberID);
    String date = "placeholder";
    String result = "placeholder";

    try {
      Connection conn = connect();
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(selectEintrittsdatumQuery);

      rs.next();
      Object obj = rs.getObject(1);
      result = (obj == null) ? null : obj.toString();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return result;
  }

  /**
  * selectAttendanceTimes 
  * Selects all the attendance times from a specific member
  * (lets pray this works)
  *
  * @version 1.0 from 10.04.2021
  * @author Eric Becker
  */
  public ArrayList<String> selectAttendanceTimes(int memberID) {
    String selectMitgliederQuery = String.format("SELECT andatum FROM anwesenheit WHERE mitgliedernummer = %d", memberID);
    ArrayList<String> members = new ArrayList<String>();

    try {
      Connection conn = connect();
      //PreparedStatement pStatement = conn.prepareStatement(selectMitgliederQuery);

      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(selectMitgliederQuery);  
      //int columnNumber = rs.getMetaData().getColumnCount();

      while (rs.next()) {
          String member = "";
          Object obj = rs.getObject(1);
          member = (obj == null) ? null : obj.toString();
          members.add(member);
        }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return members;
  }

  public String selectEintrittsdatum(int memberID) {
    String selectEintrittsdatumQuery = String.format("SELECT eintrittsdatum FROM mitglieder WHERE mitgliedernummer = %d;", memberID);
    String date = "placeholder";
    String result = "placeholder";

    try {
      Connection conn = connect();
      //PreparedStatement pStatement = conn.prepareStatement(selectMitgliederQuery);

      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(selectEintrittsdatumQuery);  

      rs.next();
      Object obj = rs.getObject(1);
      result = (obj == null) ? null : obj.toString();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return result;
  }
}
