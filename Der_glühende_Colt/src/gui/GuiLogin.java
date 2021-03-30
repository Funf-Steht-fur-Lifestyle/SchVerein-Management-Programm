package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 26.03.2021
 * @author David Stuirbrink
 */

@SuppressWarnings({ "unused", "serial" })
public class GuiLogin extends JFrame {
  // Anfang Attribute
  private JLabel lbHead = new JLabel();
  private JLabel lbUsername = new JLabel();
  private JTextField txtFieldUsername = new JTextField();
  private JLabel lbPassword = new JLabel();
  private JPasswordField pwdFieldPassword = new JPasswordField();
  private JButton btnLogin = new JButton();
  // Ende Attribute
  
  public GuiLogin() { 
    // Frame-Initialisierung
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
    
    // Anfang Komponenten
    
    //Header
    lbHead.setBounds(135, 0, 323, 33);
    lbHead.setText("Schützenverein Der glühende Colt");
    lbHead.setFont(new Font("Arial Narrow", Font.BOLD, 24));
    cp.add(lbHead);
    
    //Label für Benutzername
    lbUsername.setBounds(245, 85, 122, 23);
    lbUsername.setText("Benutzername:");
    cp.add(lbUsername);
    
    //Textfeld für den Benutzernamen
    txtFieldUsername.setBounds(202, 104, 174, 28);
    txtFieldUsername.setHorizontalAlignment(SwingConstants.CENTER);
    cp.add(txtFieldUsername);
    
    //Label für Passwort
    lbPassword.setBounds(260, 139, 82, 23);
    lbPassword.setText("Passwort:");
    cp.add(lbPassword);
    
    //Passwortfeld für das Passwort
    pwdFieldPassword.setBounds(202, 160, 174, 28);
    pwdFieldPassword.setHorizontalAlignment(SwingConstants.CENTER);
    cp.add(pwdFieldPassword);
    
    //Anmelde Knopf
    btnLogin.setBounds(245, 198, 91, 41);
    btnLogin.setText("Anmelden");
    btnLogin.setMargin(new Insets(2, 2, 2, 2));
    
    //Methodenaufruf für das drücken des Knopfes
    btnLogin.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        buttonLogin_ActionPerformed(evt);
      }
    });
    
    btnLogin.setFont(new Font("Dialog", Font.BOLD, 14));
    cp.add(btnLogin);
    // Ende Komponenten
    
    setVisible(true);
  } // end of public GuiLogin
  
  // Anfang Methoden
  
  @SuppressWarnings("deprecation")
public void buttonLogin_ActionPerformed(ActionEvent evt) {
    // TODO hier Quelltext einfügen
    String username = txtFieldUsername.getText();
    String password = pwdFieldPassword.getText();
    String expectedUsername = "Reiner";
    String expectedPassword = "Uludag123";
    
    if(username.equalsIgnoreCase(expectedUsername) && password.equals(expectedPassword)) {
    	System.out.println("Anmeldung erfolgt!");
    	this.hide();
    	GuiDatabase homeScreen = new GuiDatabase();	
    }
    else {
    	System.out.println("Du musst schon das richtige Passwort eingeben du kek!");
    }
  } // end of buttonLogin_ActionPerformed

  // Ende Methoden
} // end of class GuiLogin
