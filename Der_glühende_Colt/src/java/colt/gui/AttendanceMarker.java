package colt.gui;

import java.util.*;
import java.time.*;

import javax.swing.*;
import javax.swing.table.*;

import colt.*;

/**
 * AttendanceMarker - a class that is responsible for
 * marking the attendance of members.
 *
 * @version 1.0 from 02.04.2021
 * @author Naglis Vidziunas, David Stuirbrink
 */
public class AttendanceMarker {
  private DefaultTableModel tmAttendance;
  private JTable tAttendance;
  private Database db = new Database();
  private LocalDate currentDate = LocalDate.now();

  public AttendanceMarker(DefaultTableModel tmAttendance, JTable tAttendance) {
    this.tmAttendance = tmAttendance;
    this.tAttendance = tAttendance;
  }

  private boolean alreadyMarked(String memberID, String currentDate) {
    ArrayList<String[]> presentMembers = db.selectAllPresent();

    for (String[] presentMember : presentMembers) {
      if (memberID.equals(presentMember[0]) && currentDate.equals(presentMember[1])) {
        return true;
      }
    }

    return false;
  }

  public void markAsAttended() {
    int rows = tmAttendance.getRowCount();

    for (int i = 0; i < rows; i++) {
      String fullName = (tmAttendance.getValueAt(i, 0) == null) ? ""
                      : tmAttendance.getValueAt(i, 0).toString();
      boolean isPresent = (boolean) tmAttendance.getValueAt(i, 1);

      ArrayList<String[]> members = db.selectAllMitglieder();

      for (String[] member : members) {
        String memberFullName = member[1] + " " + member[2];
        boolean isMarked = alreadyMarked(member[0], currentDate.toString());

        if (fullName.equals(memberFullName) && isPresent && !isMarked) {
          db.setAsPresent((int) Integer.valueOf(member[0]), currentDate.toString());
        }
      }
    }
  }
}
