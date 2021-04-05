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

  public MemberSearcher(DefaultTableModel tmData) {
    this.tmData = tmData;
  }

  public void search(JTextField tfSearch) {
    int rows = tmData.getRowCount();

    if (!tfSearch.getText().equals("")) {
      String searchValue = tfSearch.getText();
      String[] searchedPerson = searchValue.split(" ");
      String firstName = searchedPerson[0];
      String lastName = searchedPerson[1];

      for (int i = 0; i < rows; i++) {
        String rowFirstName = tmData.getValueAt(i, 0).toString();
        String rowLastName = tmData.getValueAt(i, 1).toString();

        if (!rowFirstName.equals(firstName) && !rowLastName.equals(lastName)) {
          tmData.removeRow(i);
          rows = tmData.getRowCount();
          i = 0;
        }
      }
    }
  }
}
