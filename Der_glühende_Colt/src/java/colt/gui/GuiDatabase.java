package colt.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

//import javax.lang.model.util.ElementScanner14;
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
  protected JTable tData = new JTable(0, 11);
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
    setTitle("Home Sch�tzenverein");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    LocalDate currentDate = LocalDate.now();
    
    lbHead.setBounds(32, 24, 400, 28);
    lbHead.setText("Sch�tzenverein der gl�hende Colt");
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
    btnAdd.setText("Hinzuf�gen");
    btnAdd.setMargin(new Insets(2, 2, 2, 2));
    btnAdd.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
          btnAdd_ActionPerformed(evt);
      }
    });
    cp.add(btnAdd);
    btnDelete.setBounds(654, 135, 100, 25);
    btnDelete.setText("L�schen");
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
    tData.getColumnModel().getColumn(10).setHeaderValue("WS berechtigt");


    cp.add(tspScrollPane);
    btnScale.setBounds(935, 632, 100, 25);
    btnScale.setText("Vergr��ern");
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

    ColtSystray coltSystray = new ColtSystray();
    coltSystray.notifyOfMembersBirthday();

    setVisible(true);
  }

  private void setTablesData() {
    ArrayList<String[]> members = db.selectAllMitglieder();
    String isBoardMember = "Nein";

    for (String[] member : members) {
      if (member[7].equals("1")) {
        isBoardMember = "Ja";
      }
      boolean eliglible = eligibleForGunLicense((int)Integer.parseInt(member[0]));
      String question = eliglible ? "Ja" : "Nein";
      
      tmData.addRow(new Object[]{
        member[1], member[2], member[3], member[4], member[5],
        member[6], isBoardMember, member[8], member[9], member[10], question
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

  public boolean eligibleForGunLicense(int mitgliederID) // Waffenschein berechtigt?
  {    
    //Seit mindestens einem Jahr Mitglied?
    String eintrittsdatum = db.selectEintrittsdatum(mitgliederID);
    
    LocalDate currentDate = LocalDate.now();
    
    List<Integer> edSplit = Arrays.stream(eintrittsdatum.split("-"))
        .map(Integer::parseInt)
        .collect(Collectors.toList());

    List<Integer> cdSplit = Arrays.stream(currentDate.toString().split("-"))
        .map(Integer::parseInt)
        .collect(Collectors.toList());

    List<Integer> markerSplit = new ArrayList<Integer>(); //heute minus ein Jahr
    markerSplit.add(cdSplit.get(0)-1);
    markerSplit.add(cdSplit.get(1));
    markerSplit.add(cdSplit.get(2));
    
    if((int)edSplit.get(0)<(int)markerSplit.get(0))
    {
      System.out.println("Jahre älter");
    }
    else if(((int)edSplit.get(0) == (int)markerSplit.get(0))
              &&((int)edSplit.get(1) < (int)markerSplit.get(1)) )
    {
      System.out.println("Monate Älter");
    }
    else if(((int)edSplit.get(0) == (int)markerSplit.get(0))
              &&((int)edSplit.get(1) == (int)markerSplit.get(1)) 
              &&((int)edSplit.get(2) <= (int)markerSplit.get(2) ))
    {
      System.out.println("tage älter");
    }
    else{
      System.out.println("FALSE");
      return false;
    }

    System.out.println("Trainingsnachweis Start");
    // Trainingsnachweis: Hat 18 mal insgesamt oder 12 mal im Monat geschossen? 
    ArrayList<String> attendance = db.selectAttendanceTimes(mitgliederID);
    if(attendance.size()<12)
    {
      System.out.println("Size < 12");
      return false;
    }
    if(attendance.size()>=18)
    {
      System.out.println("size > 18");
      return true;
    }
    int counter = 0;
    int month = (int)cdSplit.get(1);

    for(String time : attendance )
    {
        String[] timeSplit = time.split("-");
        
        if((int) Integer.valueOf(timeSplit[1]) == month){
          counter++;
        }

        if(month!=1)
        {
          month--;
        }
        else{
          month=12;
        }
    }

    if(counter>=12)
    {
      System.out.println("12 Monate (oder mehr) regelmäßig");
      return true;
    }

    System.out.println("Zwischen 12 und 18 Aber nicht in jedem Monat min. einmal");
    return false;
  }
  
}
