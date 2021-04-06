package colt.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import colt.*;

/**
 * GuiRegister - a class that is responsible for handling
 * GUI register screen.
 *
 * @version 1.0 vom 26.03.2021
 * @author David Stuirbrink, Naglis Vidziunas
 */
@SuppressWarnings({ "unused", "serial" })
public class GuiRegister extends GuiLRMockup {
  private Database db = new Database();

  public GuiRegister() { 
    super();
    setTitle("Schützenverein | Registrierung");
    setResizable(false);

    btnRegister.setBounds(160, 198, 120, 41);
    btnLogin.setBounds(290, 198, 120, 41);
  }

  private boolean isUsernameAvailable(String username) {
    ArrayList<String[]> users = db.selectUser();

    for (String[] user : users) {
      String dbUsername = user[1];

      if (dbUsername.equals(username)) {
        return false;
      }
    }

    return true;
  }

  @Override
  @SuppressWarnings("deprecation")
  public void btnRegister_ActionPerformed(ActionEvent evt) {
    String username = txtFieldUsername.getText();
    String password = pwdFieldPassword.getText();

    PasswordHashing passHashing = new PasswordHashing();
    String hashedPassword = passHashing.hash(password);

    MessageDialog msgDialog = new MessageDialog();

    if (isUsernameAvailable(username)) {
      db.insertUser(username, hashedPassword);
      String msg = String.format("Das Benutzer mit dem Benutzername %s ist registriert", username);
      msgDialog.showInfoMsg(this, msg);
    } else {
      String msg = String.format("Das Benutzername %s ist nicht verfügbar", username);
      msgDialog.showWarningMsg(this, msg);
    }
  }

  @Override
  @SuppressWarnings("deprecation")
  public void btnLogin_ActionPerformed(ActionEvent evt) {
    this.hide();
    GuiLogin loginScreen = new GuiLogin();
  }
}
