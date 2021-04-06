package colt.gui;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import java.security.*;
import java.security.spec.*;

import javax.crypto.*;
import javax.crypto.spec.*;

import colt.*;

/**
 * GuiLogin - a class that is responsible for handling GUI
 * login screen.
 *
 * @version 1.0 vom 26.03.2021
 * @author David Stuirbrink, Naglis Vidziunas
 */
@SuppressWarnings({ "unused", "serial" })
public class GuiLogin extends GuiLRMockup {
  private Database db = new Database();

  public GuiLogin() { 
    super();
    setTitle("Anmeldung Schützenverein");

    btnLogin.setBounds(160, 198, 120, 41);
    btnRegister.setBounds(290, 198, 120, 41);
  }

  @Override
  @SuppressWarnings("deprecation")
  public void btnLogin_ActionPerformed(ActionEvent evt) {
    String username = txtFieldUsername.getText();
    String password = pwdFieldPassword.getText();

    MessageDialog msgDialog = new MessageDialog();

    Database db = new Database();
    ArrayList<String[]> users = db.selectUser();
    String expectedUsername = "";
    String expectedPassword = "";

    for (String[] user : users) {
      String usersUsername = user[1];

      if (usersUsername.equals(username)) {
        expectedUsername = user[1];
        expectedPassword = user[2];
      }
    }

    PasswordHashing passHashing = new PasswordHashing();
    String hashedPassword = passHashing.hash(password);
    boolean passwordsEqual = false;

    try {
      passwordsEqual = passHashing.validate(password, expectedPassword);
    } catch (NoSuchAlgorithmException e) {
      System.out.println(e.getMessage());
    } catch (InvalidKeySpecException ex) {
      System.out.println(ex.getMessage());
    } catch (NumberFormatException n) {
      System.out.println(n.getMessage());
    }

    if(username.equals(expectedUsername) && passwordsEqual) {
      this.hide();
      GuiDatabase homeScreen = new GuiDatabase(); 
    }
    else {
      msgDialog.showWarningMsg(this, "Das Benutzername und/oder Passwort ist falsch");
    }
  }

  @Override
  @SuppressWarnings("deprecation")
  public void btnRegister_ActionPerformed(ActionEvent evt) {
    this.hide();
    GuiRegister registerScreen = new GuiRegister();
  }
}

