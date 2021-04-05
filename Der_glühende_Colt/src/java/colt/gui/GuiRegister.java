package colt.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import colt.*;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 26.03.2021
 * @author David Stuirbrink
 */

@SuppressWarnings({ "unused", "serial" })
public class GuiRegister extends JFrame {
  private JLabel lbHead = new JLabel();
  private JLabel lbUsername = new JLabel();
  private JTextField txtFieldUsername = new JTextField();
  private JLabel lbPassword = new JLabel();
  private JPasswordField pwdFieldPassword = new JPasswordField();
  private JButton btnLogin = new JButton();
  private JButton btnRegister = new JButton();
  private Database db = new Database();

  public GuiRegister() { 
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

    btnLogin.setBounds(290, 198, 120, 41);
    btnLogin.setText("Anmelden");
    btnLogin.setMargin(new Insets(2, 2, 2, 2));
    btnLogin.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        buttonLogin_ActionPerformed(evt);
      }
    });
    btnLogin.setFont(new Font("Dialog", Font.BOLD, 14));
    cp.add(btnLogin);

    btnRegister.setBounds(160, 198, 120, 41);
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

  @SuppressWarnings("deprecation")
  public void buttonRegister_ActionPerformed(ActionEvent evt) {
    String username = txtFieldUsername.getText();
    String password = pwdFieldPassword.getText();

    PasswordHashing passHashing = new PasswordHashing();
    String hashedPassword = passHashing.hash(password);

    if (isUsernameAvailable(username)) {
      db.insertUser(username, hashedPassword);
      String msg = String.format("Das Benutzer mit dem Benutzername %s ist registriert", username);
      JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    } else {
      String msg = String.format("Das Benutzername %s ist nicht verfügbar", username);
      JOptionPane.showMessageDialog(this, msg, "Attention", JOptionPane.WARNING_MESSAGE);
    }
  }

  @SuppressWarnings("deprecation")
  public void buttonLogin_ActionPerformed(ActionEvent evt) {
    this.hide();
    GuiLogin guiLogin = new GuiLogin();
  }
}
