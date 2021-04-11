package colt.gui;

import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.security.*;
import java.security.spec.*;
import java.util.*;

import javax.crypto.*;
import javax.crypto.spec.*;
import javax.swing.*;
import javax.swing.event.*;

import colt.*;

/**
 * GuiPasswordChanger - a class that is responsible for
 * letting a user to change password using GUI.
 *
 * @version 1.0 vom 09.04.2021
 * @author David Stuirbrink, Naglis Vidziunas
 */
@SuppressWarnings({ "unused", "serial" })
public class GuiPasswordChanger extends JFrame {
  private JLabel lbHead = new JLabel();
  private JLabel lbOldPassword = new JLabel();
  private JTextField pwdFieldOldPassword = new JPasswordField();
  private JLabel lbNewPassword = new JLabel();
  private JPasswordField pwdFieldNewPassword = new JPasswordField();
  private JButton btnChange = new JButton();
  private Database db = new Database();

  public GuiPasswordChanger() { 
    super();

    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 606;
    int frameHeight = 328;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("Passwort ändern Schützenverein");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);

    // create and initiate all components
    lbHead.setBounds(140, 0, 500, 33);
    lbHead.setText("Schützenverein Der glühende Colt");
    lbHead.setFont(new Font("Arial Narrow", Font.BOLD, 24));
    cp.add(lbHead);

    lbOldPassword.setBounds(245, 85, 122, 23);
    lbOldPassword.setText("Altes Passwort:");
    cp.add(lbOldPassword);

    pwdFieldOldPassword.setBounds(202, 104, 174, 28);
    pwdFieldOldPassword.setHorizontalAlignment(SwingConstants.CENTER);
    cp.add(pwdFieldOldPassword);

    lbNewPassword.setBounds(230, 139, 150, 23);
    lbNewPassword.setText("Neues Passwort:");
    cp.add(lbNewPassword);

    pwdFieldNewPassword.setBounds(202, 160, 174, 28);
    pwdFieldNewPassword.setHorizontalAlignment(SwingConstants.CENTER);
    cp.add(pwdFieldNewPassword);

    btnChange.setBounds(200, 198, 180, 41);
    btnChange.setText("Passwort ändern");
    btnChange.setMargin(new Insets(2, 2, 2, 2));
    btnChange.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnChange_ActionPerformed(evt);
      }
    });
    btnChange.setFont(new Font("Dialog", Font.BOLD, 14));
    cp.add(btnChange);

    setVisible(true);
  }

  // track if the Password Change Button was clicked
  @SuppressWarnings("deprecation")
  public void btnChange_ActionPerformed(ActionEvent evt) {
    String oldPassword = pwdFieldOldPassword.getText();
    String newPassword = pwdFieldNewPassword.getText();

    MessageDialog msgDialog = new MessageDialog();
    ArrayList<String> user = db.selectUser();
    String userID = user.get(0);
    String expectedPassword = user.get(2);

    try {
      PasswordHashing passHashing = new PasswordHashing();
      boolean oldPasswordsEqual = passHashing.validate(oldPassword, expectedPassword);

      if (oldPasswordsEqual) {
        String hashedNewPassword = passHashing.hash(newPassword);
        db.updateUserInfo(hashedNewPassword, (int) Integer.valueOf(userID));
        msgDialog.showInfoMsg(this, "Das Passwort wurde geändert.");
      } else {
        msgDialog.showWarningMsg(this, "Das Passwort ist falsch.");
      }
    } catch (NoSuchAlgorithmException e) {
      System.out.println(e.getMessage());
    } catch (InvalidKeySpecException ex) {
      System.out.println(ex.getMessage());
    } catch (NumberFormatException n) {
      System.out.println(n.getMessage());
    }
  }
}
