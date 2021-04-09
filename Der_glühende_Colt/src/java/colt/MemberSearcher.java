package colt;

import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import colt.*;

/**
 * MemberSearcher - a simple class that is responsible for
 * searching given members within database.
 *
 * @version 1.0 from 02.04.2021
 * @author Naglis Vidziunas
 */
public class MemberSearcher {
  private DefaultTableModel tmData;
  private Database db = new Database();

  public MemberSearcher(DefaultTableModel tmData) {
    this.tmData = tmData;
  }

  private boolean tableContainsMember(String firstName, String lastName) {
    int rows = tmData.getRowCount();
    int columns = tmData.getColumnCount();

    for (int row = 0; row < rows; row++) {
        String rowFirstName = (tmData.getValueAt(row, 0) == null) ? ""
                            : tmData.getValueAt(row, 0).toString().toLowerCase();
        String rowLastName = (tmData.getValueAt(row, 1) == null) ? ""
                           : tmData.getValueAt(row, 1).toString().toLowerCase();

        if(rowFirstName.contains(firstName.toLowerCase())
            && rowLastName.contains(lastName.toLowerCase())
        ) {
          return true;
        }
    }

    return false;
  }

  public void search(JTextField tfSearch) {
    int rows = tmData.getRowCount();

    if (!tfSearch.getText().equals("")) {
      String searchValue = tfSearch.getText() + " ";
      String[] search = searchValue.split(" ");
      ArrayList<String[]> members = db.selectAllMitglieder();

      tmData.getDataVector().removeAllElements();
      boolean equal = false;

      for (String[] member : members) {
        for (int i = 0; i < member.length; i++) {
          String value = (member[i] == null) ? "" : member[i].toLowerCase();
          String firstName = (member[1] == null) ? "" : member[1];
          String lastName = (member[2] == null) ? "" : member[2];
          boolean contains = tableContainsMember(firstName, lastName);

          for (int j = 0; j < search.length; j++) {
            String data = (search[j] == null) ? "" : search[j];
            String keyword = search[j].toLowerCase();

            if (value == null) {
              continue;
            }

            if (value.equals(keyword) && !contains) {
              String isBoardMember = (value.equals("1")) ? "Ja" : "Nein";

              tmData.addRow(new Object[]{
                member[1], member[2], member[3], member[4], member[5],
                member[6], isBoardMember, member[8], member[9], member[10]
              });
            }
          }
        }
      }
    }
  }
}
