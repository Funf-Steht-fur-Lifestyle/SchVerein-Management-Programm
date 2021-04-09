package colt.gui;

import java.awt.*;
import java.awt.event.*;
import java.security.*;
import java.security.spec.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;

import javax.swing.*;

import colt.*;

/**
 * GuiLogin - a class that is responsible for handling GUI
 * login screen.
 *
 * @version 1.0 vom 26.03.2021
 * @author David Stuirbrink, Naglis Vidziunas
 */
@SuppressWarnings({ "unused", "serial" })
public class GuiLogin extends JFrame {
  protected JLabel lbHead = new JLabel();
  protected JLabel lbUsername = new JLabel();
  protected JTextField txtFieldUsername = new JTextField();
  protected JLabel lbPassword = new JLabel();
  protected JPasswordField pwdFieldPassword = new JPasswordField();
  protected JButton btnLogin = new JButton();
  protected JButton btnRegister = new JButton();

  private Database db = new Database();

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

    btnLogin.setBounds(235, 198, 120, 41);
    btnLogin.setText("Anmelden");
    btnLogin.setMargin(new Insets(2, 2, 2, 2));
    btnLogin.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnLogin_ActionPerformed(evt);
      }
    });
    btnLogin.setFont(new Font("Dialog", Font.BOLD, 14));
    cp.add(btnLogin);

    setVisible(true);
  }

  @SuppressWarnings("deprecation")
  public void btnLogin_ActionPerformed(ActionEvent evt) {
    String username = txtFieldUsername.getText();
    String password = pwdFieldPassword.getText();

    MessageDialog msgDialog = new MessageDialog();

    Database db = new Database();
    ArrayList<String> user = db.selectUser();

    String expectedUsername = user.get(1);
    String expectedPassword = user.get(2);

    boolean passwordsEqual = false;

    try {
      PasswordHashing passHashing = new PasswordHashing();
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
      msgDialog.showWarningMsg(this, "Das Benutzername und/oder Passwort ist falsch");
    }
  }
}

