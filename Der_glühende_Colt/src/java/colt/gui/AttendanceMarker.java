package colt.gui;

import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import colt.*;

/**
 * AttendanceMarker - a class that is responsible for
 * marking the attendance of members.
 *
 * @version 1.0 from 02.04.2021
 * @author Naglis Vidziunas
 */
public class AttendanceMarker {
  private DefaultTableModel tmAttendance;
  private JTable tAttendance;
  private Database db = new Database();

  public AttendanceMarker(DefaultTableModel tmAttendance, JTable tAttendance) {
    this.tmAttendance = tmAttendance;
    this.tAttendance = tAttendance;
  }

  public ArrayList<String[]> getOverallAttendance() {
    ArrayList<String[]> membersAttendance = new ArrayList<String[]>();
    int row = tmAttendance.getRowCount();
    int columnNumber = tmAttendance.getColumnCount();

    for (int i = 0; i < row; i++) {
      String fullName = tmAttendance.getValueAt(i, 0).toString();
      ArrayList<String[]> members = db.selectAllMitglieder();
      String[] attendedTimes = new String[columnNumber];

      for (String[] member : members) {
        String membersFullName = member[1] + " " + member[2];

        if (membersFullName.equals(fullName)) {
          for (int in = 0; in < columnNumber; in++) {
            attendedTimes[in - 1] = tmAttendance.getValueAt(i, in).toString();
          }
        }
      }

      membersAttendance.add(attendedTimes);
    }

    return membersAttendance;
  }

  public void unmarkAsAttended() {
    int row = tAttendance.getSelectedRow();
    String fullName = (tmAttendance.getValueAt(row, 0) == null) ? ""
                    : tmAttendance.getValueAt(row, 0).toString();
    String date = (tmAttendance.getValueAt(row, 1) == null) ? ""
                : tmAttendance.getValueAt(row, 1).toString();

    if (fullName == "" || date == "") {
      tmAttendance.removeRow(row);
    } else {
      ArrayList<String[]> presentMembers = db.selectAllPresent();
      ArrayList<String[]> members = db.selectAllMitglieder();

      for (String[] presentMember : presentMembers) {
        for (String[] member : members) {
          String memberFullName = member[1] + " " + member[2];
          if (memberFullName.equals(fullName) && presentMember[1].equals(date)) {
            db.deletePresent((int) Integer.valueOf(member[0]), presentMember[1]);
          }
        }
      }

      tmAttendance.removeRow(row);
    }
  }

  public void markAsAttended() {
    int row = tmAttendance.getRowCount();

    for (int i = 0; i < row; i++) {
      String fullName = (tmAttendance.getValueAt(i, 0) == null) ? ""
                      : tmAttendance.getValueAt(i, 0).toString();
      String date = (tmAttendance.getValueAt(i, 1) == null) ? ""
                  : tmAttendance.getValueAt(i, 1).toString();

      if (!date.matches("\\d{2}\\/\\d{2}\\/\\d{4}")) {
        JOptionPane.showMessageDialog(null, "Bitte geben Sie datum in dieses Format: dd/MM/yyyy", "Attention", JOptionPane.WARNING_MESSAGE);
        break;
      }

      ArrayList<String[]> members = db.selectAllMitglieder();

      if (fullName == "" || date == "") {
        continue;
      }

      for (String[] member : members) {
        String memberFullName = member[1] + " " + member[2];

        if (fullName.equals(memberFullName)) {
          db.setAsPresent((int) Integer.valueOf(member[0]), date);
        }
      }
    }
  }
}
