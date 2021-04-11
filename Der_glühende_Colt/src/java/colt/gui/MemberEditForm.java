package colt.gui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import colt.*;
import colt.models.*;

/**
 * MemberEditForm - a class that represents a from in
 * GUI, where needed information has to be filled, such as
 * firstName, lastName, dateOfBirth, and so on. This class
 * is used for member's information correction in a GUI
 * form.
 *
 * @version 1.0 from 03.04.2021
 * @author Naglis Vidziunas, David Stuirbrink 
 */
public class MemberEditForm extends MemberFormMockup {
  private int row;
  private JTable tData;
  private DefaultTableModel tmData;
  private Database db = new Database();
  private MessageDialog msgDialog = new MessageDialog();
  private boolean isBowDepartmentSelected = false;
  private boolean isAtmosphericDepartmentSelected = false;
  private boolean isFirearmDepartmentSelected = false;

  public MemberEditForm(JTable tData, DefaultTableModel tmData) {
    setTitle("Bearbeiten");
    btnConfirm.setText("Bearbeiten");

    this.tmData = tmData;
    this.tData = tData;

    setMemberDataToTxtFields();
    setAddressDataToTxtFields();
    setDepartments();

    isBowDepartmentSelected = (chcBoxBowDepartment.isSelected()) ? true : false;
    isAtmosphericDepartmentSelected = (chcBoxAtmosphericDepartment.isSelected()) ? true : false;
    isFirearmDepartmentSelected = (chcBoxFirearmDepartment.isSelected()) ? true : false;
  }

  private void setMemberDataToTxtFields() {
    row = tData.getSelectedRow();
    String firstName = tmData.getValueAt(row, 0).toString();
    String lastName = tmData.getValueAt(row, 1).toString();
    String dateOfBirth = tmData.getValueAt(row, 2).toString();
    String iban = tmData.getValueAt(row, 3).toString();
    String sex = tmData.getValueAt(row, 4).toString();
    String boardMember = tmData.getValueAt(row, 5).toString();
    String entranceDate = (tmData.getValueAt(row, 6) == null) ? ""
                        : tmData.getValueAt(row, 6).toString();
    String leavingDate = (tmData.getValueAt(row, 7) == null) ? ""
                       : tmData.getValueAt(row, 7).toString();

    String notes = "";
    String disabilities = "";
    ArrayList<String[]> members = db.selectAllMitglieder();

    for (String[] member : members) {
      if (member[1].equals(firstName) && member[2].equals(lastName)) {
        notes = member[10];
        disabilities = member[6];
      }
    }

    txtFieldFirstName.setText(firstName);
    txtFieldLastName.setText(lastName);
    txtFieldDateOfBirth.setText(dateOfBirth);
    txtFieldIBAN.setText(iban);
    comBoxSexSelection.setSelectedItem(sex);
    comBoxBoardMember.setSelectedItem(boardMember);
    txtAreaDisabilities.setText(disabilities);
    txtFieldEntranceDate.setText(entranceDate);
    txtFieldLeavingDate.setText(leavingDate);
    txtAreaNotes.setText(notes);
  }

  /**
    * Pair - a simple class that holds two values that are
    * related. In this case this class is only used inside
    * of this class (MemberEditForm), to hold member and
    * address ID.
    *
    * @version 1.0 from 06.04.2021
    * @author Naglis Vidziunas
    */
  public static final class Pair {
    String first;
    String second;

    public Pair(String first, String second) {
      this.first = first;
      this.second = second;
    }
  }

  private Pair getMemberAndAddressID() {
    row = tData.getSelectedRow();
    String firstName = tmData.getValueAt(row, 0).toString();
    String lastName = tmData.getValueAt(row, 1).toString();

    String memberID = "";
    String addressID = "";

    ArrayList<String[]> members = db.selectAllMitglieder();

    for (String[] member : members) {
      if (member[1].equals(firstName) && member[2].equals(lastName)) {
        memberID = member[0];
        addressID = member[11];
      }
    }

    Pair pair = new Pair(memberID, addressID);

    return pair;
  }

  private void setAddressDataToTxtFields() {
    Pair ids = getMemberAndAddressID();
    String addressID = ids.second;
    ArrayList<String[]> addresses = db.selectAllAdresse();

    for (String[] address : addresses) {
      if (address[0].equals(addressID)) {
        txtFieldStreet.setText(address[1]);
        txtFieldHouseNumber.setText(address[2]);
        txtFieldHouseNumberAdditional.setText(address[3]);
        txtFieldPostcode.setText(address[4]);
        txtFieldLocation.setText(address[5]);
        txtFieldCountry.setText(address[6]);
        txtFieldState.setText(address[7]);
      }
    }
  }

  private void setDepartments() {
    Pair ids = getMemberAndAddressID();
    String memberID = ids.first;
    ArrayList<String[]> departments = db.selectAllAbteilung();

    for (String[] department : departments) {
      if (department[5].equals(memberID)) {
        String departmentName = department[1];

        if (departmentName.equals("Bogen") && department[6].equals("0")) {
          chcBoxBowDepartment.setSelected(true);
        } else if (departmentName.equals("Luftdruck") && department[6].equals("0")) {
          chcBoxAtmosphericDepartment.setSelected(true);
        } else if (departmentName.equals("Feuerwaffen") && department[6].equals("0")) {
          chcBoxFirearmDepartment.setSelected(true);
        } else {
          System.out.println("Nichts");
        }

        lDiscounts.setText("rabatte: " + department[4]);
      }
    }
  }

  @Override
  protected void insertMembersDataToTable(Member member) {
    String isBoardMember = "Nein";

    if (member.boardMember == 1) {
      isBoardMember = "Ja";
    }

    row = tData.getSelectedRow();
    tmData.setValueAt(member.firstName, row, 0);
    tmData.setValueAt(member.lastName, row, 1);
    tmData.setValueAt(member.dateOfBirth, row, 2);
    tmData.setValueAt(member.iban, row, 3);
    tmData.setValueAt(member.sex, row, 4);
    tmData.setValueAt(isBoardMember, row, 5);
    tmData.setValueAt(member.entranceDate, row, 6);
    tmData.setValueAt(member.leavingDate, row, 7);
  }

  @Override
  protected void btnConfirm_ActionPerformed(ActionEvent evt) {
    if (!isAllRequiredTxtFieldsFilled()) {
      msgDialog.showWarningMsg(this, "Sie muessen alle erforderlichen Felder ausfuellen.");
    } else {
      Member member = getMembersData();
      insertMembersDataToTable(member);

      Pair ids = getMemberAndAddressID();
      String memberID = ids.first;
      String addressID = ids.second;

      db.updateMitglieder(member, (int) Integer.valueOf(memberID));

      Address address = getAddressData();
      db.updateAdresse(address, (int) Integer.valueOf(addressID));

      JCheckBox[] chcBoxDepartments = {
        chcBoxBowDepartment, chcBoxAtmosphericDepartment,
        chcBoxFirearmDepartment
      };

      int departmentNumber = 0;

      for (JCheckBox chcBoxDepartment : chcBoxDepartments) {
        Department department = getDepartmentData(chcBoxDepartment);

        if (chcBoxDepartment.isSelected()) {
          departmentNumber++;
        }

        department.discount = (departmentNumber > 1) ? 18 : 0;

        if (chcBoxDepartment.isSelected()) {
          db.updateAbteilung(department, (int) Integer.valueOf(memberID), 0);
        } else {
          db.deleteAbteilung(department.name, (int) Integer.valueOf(memberID), 1);
        }
      }
    }
  }
}
