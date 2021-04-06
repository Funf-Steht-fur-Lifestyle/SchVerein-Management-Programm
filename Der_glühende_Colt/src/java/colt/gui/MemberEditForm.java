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
 * @author Naglis Vidziunas
 */
public class MemberEditForm extends MemberFormMockup {
  private int row;
  private JTable tData;
  private DefaultTableModel tmData;
  private Database db = new Database();

  public MemberEditForm(JTable tData, DefaultTableModel tmData) {
    setTitle("Bearbeiten");
    btnConfirm.setText("Bearbeiten");

    this.tmData = tmData;
    this.tData = tData;

    setMemberDataToTxtFields();
    setAddressDataToTxtFields();
  }

  private void setMemberDataToTxtFields() {
    row = tData.getSelectedRow();
    String firstName = tmData.getValueAt(row, 0).toString();
    String lastName = tmData.getValueAt(row, 1).toString();
    String dateOfBirth = tmData.getValueAt(row, 2).toString();
    String iban = tmData.getValueAt(row, 3).toString();
    String sex = tmData.getValueAt(row, 4).toString();
    String disabilities = tmData.getValueAt(row, 5).toString();
    String boardMember = tmData.getValueAt(row, 6).toString();
    String entranceDate = tmData.getValueAt(row, 7).toString();
    String leavingDate = (tmData.getValueAt(row, 8) == null) ? ""
                       : tmData.getValueAt(row, 8).toString();
    String notes = (tmData.getValueAt(row, 9) == null) ? ""
                 : tmData.getValueAt(row, 9).toString();

    txtFieldFirstName.setText(firstName);
    txtFieldLastName.setText(lastName);
    txtFieldDateOfBirth.setText(dateOfBirth);
    txtFieldIBAN.setText(iban);
    setSex(sex);
    txtAreaDisabilities.setText(disabilities);
    setBoardMember(boardMember);
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

  private void setSex(String sex) {
    if (sex.equals("maennlich")) {
      rBtnMan.setSelected(true);
    } else if (sex.equals("weiblich")) {
      rBtnWoman.setSelected(true);
    } else {
      rBtnDiverse.setSelected(true);
    }
  }

  private void setBoardMember(String boardMember) {
    if (boardMember.equals("Ja")) {
      rBtnIsBoardMember.setSelected(true);
    } else {
      rBtnIsNotBoardMember.setSelected(true);
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
    tmData.setValueAt(member.disabilities, row, 5);
    tmData.setValueAt(isBoardMember, row, 6);
    tmData.setValueAt(member.entranceDate, row, 7);
    tmData.setValueAt(member.leavingDate, row, 8);
    tmData.setValueAt(member.notes, row, 9);
  }

  @Override
  protected void btnConfirm_ActionPerformed(ActionEvent evt) {
    if (!isAllRequiredTxtFieldsFilled()) {
      showWarningMsg("Sie müssen alle erforderliche Felder ausfüllen.");
    } else {
      Member member = getMembersData();
      insertMembersDataToTable(member);

      Pair ids = getMemberAndAddressID();
      String memberID = ids.first;
      String addressID = ids.second;

      db.updateMitglieder(member, Integer.valueOf(memberID));

      Address address = getAddressData();
      db.updateAdresse(address, (int) Integer.valueOf(addressID));
    }
  }
}
