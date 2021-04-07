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
 * GuiLRMockup - a class that defines the design for GUI
 * login und register screen. This class is used with
 * GuiLogin and GuiRegister classes
 *
 * @version 1.0 vom 06.04.2021
 * @author Naglis Vidziunas
 */
@SuppressWarnings({ "unused", "serial" })
public class GuiLRMockup extends JFrame {
  protected JLabel lbHead = new JLabel();
  protected JLabel lbUsername = new JLabel();
  protected JTextField txtFieldUsername = new JTextField();
  protected JLabel lbPassword = new JLabel();
  protected JPasswordField pwdFieldPassword = new JPasswordField();
  protected JButton btnLogin = new JButton();
  protected JButton btnRegister = new JButton();

  public GuiLRMockup() {
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
        btnLogin_ActionPerformed(evt);
      }
    });
    btnLogin.setFont(new Font("Dialog", Font.BOLD, 14));
    cp.add(btnLogin);

    btnRegister.setBounds(290, 198, 120, 41);
    btnRegister.setText("Registrieren");
    btnRegister.setMargin(new Insets(2, 2, 2, 2));
    btnRegister.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnRegister_ActionPerformed(evt);
      }
    });

    btnRegister.setFont(new Font("Dialog", Font.BOLD, 14));
    cp.add(btnRegister);

    setVisible(true);
  }

  @SuppressWarnings("deprecation")
  public void btnLogin_ActionPerformed(ActionEvent evt) {}

  @SuppressWarnings("deprecation")
  public void btnRegister_ActionPerformed(ActionEvent evt) {}
}

