package colt.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import java.security.*;
import java.security.spec.*;

import javax.crypto.*;
import javax.crypto.spec.*;

import colt.*;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 26.03.2021
 * @author David Stuirbrink
 */

@SuppressWarnings({ "unused", "serial" })
public class GuiLogin extends JFrame {
  private JLabel lbHead = new JLabel();
  private JLabel lbUsername = new JLabel();
  private JTextField txtFieldUsername = new JTextField();
  private JLabel lbPassword = new JLabel();
  private JPasswordField pwdFieldPassword = new JPasswordField();
  private JButton btnLogin = new JButton();
  private JButton btnRegister = new JButton();

  public GuiLogin() { 
    super();

    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 606; 
    int frameHeight = 328;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("Anmeldung Schützenverein");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);

    lbHead.setBounds(80, 0, 500, 33);
    lbHead.setText("Schützenverein Der glühende Colt");
    lbHead.setFont(new Font("Arial Narrow", Font.BOLD, 24));
    cp.add(lbHead);

    lbUsername.setBounds(245, 85, 122, 23);
    lbUsername.setText("Benutzername:");
    cp.add(lbUsername);

    txtFieldUsername.setBounds(202, 104, 174, 28);
    txtFieldUsername.setHorizontalAlignment(SwingConstants.CENTER);
    cp.add(txtFieldUsername);

    lbPassword.setBounds(260, 139, 82, 23);
    lbPassword.setText("Passwort:");
    cp.add(lbPassword);

    pwdFieldPassword.setBounds(202, 160, 174, 28);
    pwdFieldPassword.setHorizontalAlignment(SwingConstants.CENTER);
    cp.add(pwdFieldPassword);

    btnLogin.setBounds(160, 198, 120, 41);
    btnLogin.setText("Anmelden");
    btnLogin.setMargin(new Insets(2, 2, 2, 2));
    btnLogin.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        buttonLogin_ActionPerformed(evt);
      }
    });
    btnLogin.setFont(new Font("Dialog", Font.BOLD, 14));
    cp.add(btnLogin);

    btnRegister.setBounds(290, 198, 120, 41);
    btnRegister.setText("Registrieren");
    btnRegister.setMargin(new Insets(2, 2, 2, 2));
    btnRegister.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        buttonRegister_ActionPerformed(evt);
      }
    });

    btnRegister.setFont(new Font("Dialog", Font.BOLD, 14));
    cp.add(btnRegister);

    setVisible(true);
  }

  @SuppressWarnings("deprecation")
  public void buttonLogin_ActionPerformed(ActionEvent evt) {
    String username = txtFieldUsername.getText();
    String password = pwdFieldPassword.getText();

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
    }

    if(username.equals(expectedUsername) && passwordsEqual) {
        this.hide();
        GuiDatabase homeScreen = new GuiDatabase(); 
    }
    else {
        JOptionPane.showMessageDialog(this, "Das Benutzername und/oder Passwort ist falsch", "Attention", JOptionPane.WARNING_MESSAGE);
    }
  }

  @SuppressWarnings("deprecation")
  public void buttonRegister_ActionPerformed(ActionEvent evt) {
    this.hide();
    GuiRegister guiRegister = new GuiRegister();
  }
}

