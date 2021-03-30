package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.time.LocalDate;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 29.03.2021
 * @author 
 */

@SuppressWarnings({ "unused", "serial" })
public class GuiDatabase extends JFrame {
  // Anfang Attribute
  private JLabel lbHead = new JLabel();
  private JLabel jLabel1 = new JLabel();
  private JLabel lbDatum = new JLabel();
  private JButton btnEdit = new JButton();
  private JButton btnAdd = new JButton();
  private JButton btnDelete = new JButton();
  private JTextField tfSearch = new JTextField();
  private JButton btnSuchen = new JButton();
  private JTable tData = new JTable(5, 5);
    private DefaultTableModel tmData = (DefaultTableModel) tData.getModel();
    private JScrollPane tspScrollPane = new JScrollPane(tData);
  private JButton btnScale = new JButton();
  private JButton btnAttendance = new JButton();
  // Ende Attribute
  
  public GuiDatabase() { 
    // Frame-Initialisierung
    super();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 1058; 
    int frameHeight = 703;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("GuiSchützenvereinHome");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    LocalDate currentDate = LocalDate.now();
    // Anfang Komponenten
    
    lbHead.setBounds(32, 24, 324, 28);
    lbHead.setText("Schützenverein der glühende Colt");
    lbHead.setFont(new Font("Arial", Font.BOLD, 20));
    cp.add(lbHead);
    jLabel1.setBounds(-88, -104, 110, 20);
    jLabel1.setText("text");
    cp.add(jLabel1);
    lbDatum.setBounds(864, 25, 110, 20);
    lbDatum.setText(currentDate.toString());
    lbDatum.setFont(new Font("Dialog", Font.BOLD, 14));
    cp.add(lbDatum);
    btnEdit.setBounds(529, 135, 75, 25);
    btnEdit.setText("Speichern");
    btnEdit.setMargin(new Insets(2, 2, 2, 2));
    btnEdit.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btnEdit_ActionPerformed(evt);
      }
    });
    cp.add(btnEdit);
    btnAdd.setBounds(606, 135, 75, 25);
    btnAdd.setText("Hinzufügen");
    btnAdd.setMargin(new Insets(2, 2, 2, 2));
    btnAdd.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
    	  btnAdd_ActionPerformed(evt);
      }
    });
    cp.add(btnAdd);
    btnDelete.setBounds(684, 135, 75, 25);
    btnDelete.setText("Löschen");
    btnDelete.setMargin(new Insets(2, 2, 2, 2));
    btnDelete.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
    	  btnDelete_ActionPerformed(evt);
      }
    });
    cp.add(btnDelete);
    tfSearch.setBounds(761, 134, 150, 28);
    cp.add(tfSearch);
    btnSuchen.setBounds(912, 135, 59, 25);
    btnSuchen.setText("Suchen");
    btnSuchen.setMargin(new Insets(2, 2, 2, 2));
    btnSuchen.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bSuchen_ActionPerformed(evt);
      }
    });
    cp.add(btnSuchen);
    tspScrollPane.setBounds(39, 167, 972, 446);
    tData.getColumnModel().getColumn(0).setHeaderValue("Title 1");
    tData.getColumnModel().getColumn(1).setHeaderValue("Title 2");
    tData.getColumnModel().getColumn(2).setHeaderValue("Title 3");
    tData.getColumnModel().getColumn(3).setHeaderValue("Title 4");
    tData.getColumnModel().getColumn(4).setHeaderValue("Title 5");
    cp.add(tspScrollPane);
    btnScale.setBounds(960, 632, 75, 25);
    btnScale.setText("Vergrößern");
    btnScale.setMargin(new Insets(2, 2, 2, 2));
    btnScale.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
    	  btnScale_ActionPerformed(evt);
      }
    });
    cp.add(btnScale);
    btnAttendance.setBounds(41, 119, 155, 41);
    btnAttendance.setText("Anwesenheit");
    btnAttendance.setMargin(new Insets(2, 2, 2, 2));
    btnAttendance.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
    	  btnAttendance_ActionPerformed(evt);
      }
    });
    cp.add(btnAttendance);
    // Ende Komponenten
    
    setVisible(true);
  } // end of public GuiSchützenvereinHome
  
  // Anfang Methoden
  
  public void btnEdit_ActionPerformed(ActionEvent evt) {
    // Hier muss eine Funktion rein, welche die Änderungen in der Datenbank speichert.
    
  } // end of btnEdit_ActionPerformed

  public void btnAdd_ActionPerformed(ActionEvent evt) {
	  // Hier muss eine Funktion rein, welche eine neue Zeile in der Tabelle hinzufügt.
  }

  public void btnDelete_ActionPerformed(ActionEvent evt) {
	// Hier muss eine Funktion rein, zum Löschen einer Zeile
	  
  } // end of bLoeschen_ActionPerformed

  public void bSuchen_ActionPerformed(ActionEvent evt) {
    // Hier muss eine Funktion rein, welche das Suchen eines Begriffes in der Datenbank erlaubt.
    
  } 

  public void btnScale_ActionPerformed(ActionEvent evt) {
    // Skalieren der Gui
    
  } 

  public void btnAttendance_ActionPerformed(ActionEvent evt) {
	  //Anwesenheit öffnen
	  GuiAttendance attendanceScreen = new GuiAttendance();	
  }

  // Ende Methoden
} 
