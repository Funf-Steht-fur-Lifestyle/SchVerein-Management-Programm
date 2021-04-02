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
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return conn;
    }

    public void insertUser(String username, String password) {
        String insertUserQuery = "INSERT INTO mitglieder(benutzername,"
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

    public ArrayList<String> selectUser() {
        String selectUserQuery = "SELECT * FROM benutzer";
        ArrayList<String> user = new ArrayList<String>();

        try {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(selectUserQuery);

            while (rs.next()) {
                user.add(rs.getString(2));
                user.add(rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
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

    public void insertMitglieder(Member member) {
        String insertMitgliederQuery = "INSERT INTO mitglieder("
                                     + "vorname, nachname, geburtstag, IBAN,"
                                     + " geschlecht, behinderungen,"
                                     + " vorstandsmitglied, eintrittsdatum,"
                                     + " austrittsdatum, vermerke)"
                                     + " VALUES(?,?,?,?,?,?,?,?,?,?)";

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
            pStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> selectAllMitglieder() {
        String selectMitgliederQuery = "SELECT * FROM mitglieder";
        ArrayList<String> members = new ArrayList<String>();

        try {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(selectMitgliederQuery);

            while (rs.next()) {
                members.add(rs.getString(2));
                members.add(rs.getString(3));
                members.add(rs.getString(4));
                members.add(rs.getString(5));
                members.add(rs.getString(6));
                members.add(rs.getString(7));
                members.add(String.valueOf(rs.getInt(8)));
                members.add(rs.getString(9));
                members.add(rs.getString(10));
                members.add(rs.getString(11));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return members;
    }

    public void updateMitglieder(Member member, int id) {
        String updateMitgliederQuery = "UPDATE mitglieder SET name = ?, nachname = ?,"
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
            pStatement.setString(6, member.disabilities);
            pStatement.setInt(7, member.boardMember);
            pStatement.setString(8, member.entranceDate);
            pStatement.setString(9, member.leavingDate);
            pStatement.setString(10, member.notes);
            pStatement.setInt(11, id);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteMitglieder(Member member, int id) {
        String deleteMitgliederQuery = "DELETE FROM mitglieder WHERE id = ?,"
                                     + " name = ?, nachname = ?";

        try {
            Connection conn = connect();
            PreparedStatement pStatement = conn.prepareStatement(deleteMitgliederQuery);

            pStatement.setInt(1, id);
            pStatement.setString(2, member.firstName);
            pStatement.setString(3, member.lastName);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertAdresse() {
        String insertMitgliederQuery = "INSERT INTO adresse("
                                     + "Strasse, HN, HNZusatz, PLZ,"
                                     + " Ort, Land, Bundesland"
                                     + "VALUES(?,?,?,?,?,?,?)";

        try {
            Connection conn = connect();
            PreparedStatement pStatement = conn.prepareStatement(insertMitgliederQuery);

            pStatement.setString(1, "Derig");
            pStatement.setInt(2, 10);
            pStatement.setString(3, "");
            pStatement.setString(4, "55126");
            pStatement.setString(5, "Mainz");
            pStatement.setString(6, "Deutschland");
            pStatement.setString(7, "RP");
            pStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> selectAllAdresse() {
        String selectMitgliederQuery = "SELECT * FROM adresse";
        ArrayList<String> addresses = new ArrayList<String>();

        try {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(selectMitgliederQuery);

            while (rs.next()) {
                addresses.add(rs.getString(2));
                addresses.add(String.valueOf(rs.getInt(3)));
                addresses.add(rs.getString(4));
                addresses.add(String.valueOf(rs.getInt(5)));
                addresses.add(rs.getString(6));
                addresses.add(rs.getString(7));
                addresses.add(String.valueOf(rs.getInt(8)));
                addresses.add(rs.getString(9));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return addresses;
    }

    public void updateAdresse(Address address, int id) {
        String updateMitgliederQuery = "UPDATE mitglieder SET Strasse = ?, HN = ?,"
                                     + " HNZusatz = ?, PLZ = ?, Ort = ?, Land = ?,"
                                     + " Bundesland = ? WHER adresseID = ?";

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
            pStatement.setInt(8, 1);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAdresse(int id) {
        String deleteMitgliederQuery = "DELETE FROM adresse WHERE addressID = ?";

        try {
            Connection conn = connect();
            PreparedStatement pStatement = conn.prepareStatement(deleteMitgliederQuery);

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
}
