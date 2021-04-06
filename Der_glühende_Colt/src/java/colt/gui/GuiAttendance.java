package colt.gui;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import colt.*;

/**
 * GuiAttendance - a simple class that is responsible for
 * handling GUI attendance screen.
 *
 * @version 1.0 vom 30.03.2021
 * @author David Stuirbrink, Naglis Vidziunas
 */
@SuppressWarnings({ "serial", "unused" })
public class GuiAttendance extends JFrame {
  private JLabel lbAttendance = new JLabel();
  private JPanel pContent = new JPanel(null, true);
  private JLabel lbDepartment = new JLabel();
  private JTable tableAttendance = new JTable(0, 2);
  @SuppressWarnings("unused")
  private DefaultTableModel tableAttendanceModel = (DefaultTableModel) tableAttendance.getModel();
  private JScrollPane tableAttendanceScrollPane = new JScrollPane(tableAttendance);
  private JButton btnControl = new JButton();
  private JButton btnScale = new JButton();
  private JButton btnAddNewRow = new JButton();
  private JButton btnDelete = new JButton();
  private JLabel lbDate = new JLabel();

  public GuiAttendance() { 
    super();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 713; 
    int frameHeight = 424;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("Anwesenheit Schützenverein");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    LocalDate currentDate = LocalDate.now();

    lbAttendance.setBounds(223, 20, 180, 28);
    lbAttendance.setText("Anwesenheit");
    lbAttendance.setHorizontalAlignment(SwingConstants.CENTER);
    lbAttendance.setFont(new Font("Arial", Font.BOLD, 20));
    cp.add(lbAttendance);
    pContent.setBounds(33, 61, 628, 292);
    pContent.setOpaque(false);
    cp.add(pContent);
    lbDepartment.setBounds(36, 12, 110, 28);
    lbDepartment.setText("Abteilung");
    lbDepartment.setFont(new Font("Arial", Font.BOLD, 20));
    pContent.add(lbDepartment);
    tableAttendanceScrollPane.setBounds(146, 10, 444, 246);
    tableAttendance.getColumnModel().getColumn(0).setHeaderValue("Mitgliedsname");
    tableAttendance.getColumnModel().getColumn(1).setHeaderValue("Anwesenheit");
    pContent.add(tableAttendanceScrollPane);
    btnControl.setBounds(177, 329, 107, 25);
    btnControl.setText("Kontrolliert");
    btnControl.setMargin(new Insets(2, 2, 2, 2));
    btnControl.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btnControl_ActionPerformed(evt);
      }
    });
    btnControl.setFont(new Font("Dialog", Font.BOLD, 16));
    cp.add(btnControl);
    btnAddNewRow.setBounds(350, 329, 107, 25);
    btnAddNewRow.setText("Neue Reihe");
    btnAddNewRow.setMargin(new Insets(2, 2, 2, 2));
    btnAddNewRow.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btnAddNewRow_ActionPerformed(evt);
      }
    });
    cp.add(btnAddNewRow);
    btnDelete.setBounds(512, 329, 107, 25);
    btnDelete.setText("Löschen");
    btnDelete.setMargin(new Insets(2, 2, 2, 2));
    btnDelete.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btnDelete_ActionPerformed(evt);
      }
    });
    cp.add(btnDelete);
    btnScale.setBounds(622, 359, 75, 25);
    btnScale.setText("Vergrößern");
    btnScale.setMargin(new Insets(2, 2, 2, 2));
    btnScale.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btnScale_ActionPerformed(evt);
      }
    });
    cp.add(btnScale);
    lbDate.setBounds(572, 5, 110, 23);
    lbDate.setText(currentDate.toString());
    lbDate.setFont(new Font("Dialog", Font.BOLD, 16));
    cp.add(lbDate);

    listPresent();
    setVisible(true);
  }

  private void listPresent() {
    Database db = new Database();
    ArrayList<String[]> presentMembers = db.selectAllPresent();
    ArrayList<String[]> members = db.selectAllMitglieder();

    for (String[] presentMember : presentMembers) {
      for (String[] member : members) {
        if (presentMember[0].equals(member[0])) {
          String fullName = member[1] + " " + member[2];

          tableAttendanceModel.addRow(new Object[]{
            fullName, presentMember[1]
          });
        }
      }
    }
  }

  public void btnControl_ActionPerformed(ActionEvent evt) {
    AttendanceMarker attendanceMarker = new AttendanceMarker(tableAttendanceModel, tableAttendance);
    attendanceMarker.markAsAttended();
  }

  public void btnAddNewRow_ActionPerformed(ActionEvent evt) {
    tableAttendanceModel.addRow(new Object[]{
      null, null
    });
  }

  public void btnDelete_ActionPerformed(ActionEvent evt) {
    AttendanceMarker attendanceMarker = new AttendanceMarker(tableAttendanceModel, tableAttendance);
    attendanceMarker.unmarkAsAttended();
  }

  public void btnScale_ActionPerformed(ActionEvent evt) {
    // TODO hier Quelltext einfügen

  } // end of btnScale_ActionPerformed

}

