package colt.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.time.LocalDate;

import colt.*;

/**
 * GuiDatabase - a class that is responsible for displaying
 * all of the components that make up a home screen for this
 * project.
 *
 * @version 1.0 for 29.03.2021
 * @author David Stuirbrink, Naglis Vidziunas
 */
@SuppressWarnings({ "unused", "serial" })
public class GuiDatabase extends JFrame {
  private JLabel lbHead = new JLabel();
  private JLabel jLabel1 = new JLabel();
  private JLabel lbDatum = new JLabel();
  private JButton btnEdit = new JButton();
  private JButton btnAdd = new JButton();
  private JButton btnDelete = new JButton();
  private JTextField tfSearch = new JTextField();
  private JButton btnSuchen = new JButton();
  protected JTable tData = new JTable(0, 10);
  private DefaultTableModel tmData = (DefaultTableModel) tData.getModel();
  private JScrollPane tspScrollPane = new JScrollPane(tData);
  private JButton btnScale = new JButton();
  private JButton btnAttendance = new JButton();
  private Database db = new Database();

  public GuiDatabase() {
    super();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 1058; 
    int frameHeight = 703;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("Home Schützenverein");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    LocalDate currentDate = LocalDate.now();

    lbHead.setBounds(32, 24, 400, 28);
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
    btnEdit.setBounds(444, 135, 100, 25);
    btnEdit.setText("Bearbeiten");
    btnEdit.setMargin(new Insets(2, 2, 2, 2));
    btnEdit.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btnEdit_ActionPerformed(evt);
      }
    });
    cp.add(btnEdit);
    btnAdd.setBounds(550, 135, 100, 25);
    btnAdd.setText("Hinzufügen");
    btnAdd.setMargin(new Insets(2, 2, 2, 2));
    btnAdd.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
          btnAdd_ActionPerformed(evt);
      }
    });
    cp.add(btnAdd);
    btnDelete.setBounds(654, 135, 100, 25);
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
    btnSuchen.setBounds(912, 135, 100, 25);
    btnSuchen.setText("Suchen");
    btnSuchen.setMargin(new Insets(2, 2, 2, 2));
    btnSuchen.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bSuchen_ActionPerformed(evt);
      }
    });
    cp.add(btnSuchen);
    tspScrollPane.setBounds(39, 167, 972, 446);
    tData.getColumnModel().getColumn(0).setHeaderValue("Vorname");
    tData.getColumnModel().getColumn(1).setHeaderValue("Nachname");
    tData.getColumnModel().getColumn(2).setHeaderValue("Geburtstag");
    tData.getColumnModel().getColumn(3).setHeaderValue("IBAN");
    tData.getColumnModel().getColumn(4).setHeaderValue("Geschlecht");
    tData.getColumnModel().getColumn(5).setHeaderValue("Behinderungen");
    tData.getColumnModel().getColumn(6).setHeaderValue("Vorstandsmitglied");
    tData.getColumnModel().getColumn(7).setHeaderValue("Eintrittsdatum");
    tData.getColumnModel().getColumn(8).setHeaderValue("Austrittsdatum");
    tData.getColumnModel().getColumn(9).setHeaderValue("Vermerke");
    cp.add(tspScrollPane);
    btnScale.setBounds(935, 632, 100, 25);
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

    setTablesData();
    setVisible(true);
  }

  private void setTablesData() {
    ArrayList<String[]> members = db.selectAllMitglieder();
    String isBoardMember = "Nein";

    for (String[] member : members) {
      if (member[7].equals("1")) {
        isBoardMember = "Ja";
      }

      tmData.addRow(new Object[]{
        member[1], member[2], member[3], member[4], member[5],
        member[6], isBoardMember, member[8], member[9], member[10]
      });
    }
  }

  public void btnEdit_ActionPerformed(ActionEvent evt) {
    MemberEditForm form = new MemberEditForm(tData, tmData);
  }

  public void btnAdd_ActionPerformed(ActionEvent evt) {
    MemberAdditionForm form = new MemberAdditionForm(tmData);
  }

  public void btnDelete_ActionPerformed(ActionEvent evt) {
    int row = tData.getSelectedRow();
    String firstName = tmData.getValueAt(row, 0).toString();
    String lastName = tmData.getValueAt(row, 1).toString();

    Database db = new Database();
    ArrayList<String[]> members = db.selectAllMitglieder();

    for (String[] member : members) {
      if (member[1].equals(firstName) && member[2].equals(lastName)) {
        db.deleteAdresse((int) Integer.valueOf(member[11]));
        db.deleteMitglieder((int) Integer.valueOf(member[0]));
        tmData.removeRow(row);
      }
    }
  }

  int searchedTimes = 0;

  public void bSuchen_ActionPerformed(ActionEvent evt) {
    MemberSearcher searcher = new MemberSearcher(tmData);
    searcher.search(tfSearch);

    searchedTimes++;

    if (searchedTimes % 2 == 0) {
      tmData.getDataVector().removeAllElements();

      setTablesData();
      searcher.search(tfSearch);
    }
  }

  public void btnScale_ActionPerformed(ActionEvent evt) {
    // Skalieren der Gui
    
  }

  public void btnAttendance_ActionPerformed(ActionEvent evt) {
      GuiAttendance attendanceScreen = new GuiAttendance(); 
  }
}
