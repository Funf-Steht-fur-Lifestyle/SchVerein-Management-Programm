package colt;

import java.sql.*;
import java.util.*;

/**
 * Database - a class that is responsible for storing data
 * in and getting data from database.
 *
 * @version 1.0 from 02.04.2021
 * @author Naglis Vidziunas
 */
public class Database {
  public Connection connect() {
    Connection conn = null;

    try {
      Class.forName("org.sqlite.JDBC");
      conn = DriverManager.getConnection("jdbc:sqlite:data.db");
      conn.createStatement().execute("PRAGMA foreign_keys = ON");
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    return conn;
  }

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

  public ArrayList<String[]> selectUser() {
    String selectUserQuery = "SELECT * FROM benutzer JOIN mitglieder";
    ArrayList<String[]> users = new ArrayList<String[]>();

    try {
      Connection conn = connect();
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(selectUserQuery);
      int columnNumber = rs.getMetaData().getColumnCount();

      while (rs.next()) {
        String[] user = new String[columnNumber];

        for (int i = 1; i < columnNumber; i++) {
          Object obj = rs.getObject(i);
          user[i - 1] = (obj == null) ? null : obj.toString();
        }
        users.add(user);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return users;
  }

  public void updateUserInfo(String username, String password, int id) {
    String updateUserInfoQuery = "UPDATE benutzer SET benutzername = ?,"
                               + " passwort = ? WHERE id = ?";

    try {
      Connection conn = connect();
      PreparedStatement pStatement = conn.prepareStatement(updateUserInfoQuery);

      pStatement.setString(1, username);
      pStatement.setString(2, password);
      pStatement.setInt(3, id);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

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

  public ArrayList<String[]> selectAllMitglieder() {
    String selectMitgliederQuery = "SELECT * FROM mitglieder JOIN adresse USING (adresseID)";
    ArrayList<String[]> members = new ArrayList<String[]>();

    try {
      Connection conn = connect();
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(selectMitgliederQuery);
      int columnNumber = rs.getMetaData().getColumnCount();

      while (rs.next()) {
        String[] member = new String[columnNumber];

        for (int i = 1; i < columnNumber; i++) {
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

  public void insertAdresse(Address address) {
    String insertMitgliederQuery = "INSERT INTO adresse("
                                 + "Strasse, HN, HNZusatz, PLZ,"
                                 + " Ort, Land, Bundesland)"
                                 + " VALUES(?,?,?,?,?,?,?)";

    try {
      Connection conn = connect();
      PreparedStatement pStatement = conn.prepareStatement(insertMitgliederQuery);

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

  public ArrayList<String[]> selectAllAdresse() {
    String selectMitgliederQuery = "SELECT * FROM adresse JOIN mitglieder USING (adresseID)";
    ArrayList<String[]> addresses = new ArrayList<String[]>();

    try {
      Connection conn = connect();
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(selectMitgliederQuery);

      int columnNumber = rs.getMetaData().getColumnCount();

      while (rs.next()) {
        String[] address = new String[columnNumber];

        for (int i = 1; i < columnNumber; i++) {
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

  public void updateAdresse(Address address, int addressID) {
    String updateMitgliederQuery = "UPDATE adresse SET Strasse = ?, HN = ?,"
                                 + " HNZusatz = ?, PLZ = ?, Ort = ?, Land = ?,"
                                 + " Bundesland = ? WHERE adresseID = ?";

    try {
      Connection conn = connect();
      PreparedStatement pStatement = conn.prepareStatement(updateMitgliederQuery);

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

  public void deleteAdresse(int id) {
    String deleteAdresseQuery = "DELETE FROM adresse WHERE addressID = ?";

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

  public ArrayList<String[]> selectAllPresent() {
    String selectPresentQuery = "SELECT * FROM anwesenheit JOIN mitglieder USING (mitgliedernummer)";
    ArrayList<String[]> presentMembers = new ArrayList<String[]>();

    try {
      Connection conn = connect();
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(selectPresentQuery);
      int columnNumber = rs.getMetaData().getColumnCount();

      while (rs.next()) {
        String[] presentMember = new String[columnNumber];

        for (int i = 1; i < columnNumber; i++) {
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
}
