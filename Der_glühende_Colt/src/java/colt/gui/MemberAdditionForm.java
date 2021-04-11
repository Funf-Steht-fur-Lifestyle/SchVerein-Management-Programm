package colt.gui;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import colt.*;
import colt.models.*;

/**
 * MemberAdditionForm - a class that represents a form in
 * GUI, where needed information has to be filled, such as
 * firstName, lastName, dateOfBirth, and so on. This class
 * is used as member addition in a GUI form.
 *
 * @version 1.0 from 03.04.2021
 * @author Naglis Vidziunas
 */
public class MemberAdditionForm extends MemberFormMockup {
  private final int gapSize = 10;
  private DefaultTableModel tmData;
  private Database db = new Database();

  public MemberAdditionForm(DefaultTableModel tmData) {
    setTitle("Hinzufuegen");
    btnConfirm.setText("Hinzufuegen");
    this.tmData = tmData;
  }

  @Override
  protected void insertMembersDataToTable(Member member) {
    String isBoardMember = "Nein";
    String eligibleForGunLicense = "Nein";

    if (member.boardMember == 1) {
      isBoardMember = "Ja";
    }

    tmData.addRow(new Object[]{
      member.firstName, member.lastName, member.dateOfBirth,
      member.iban, member.sex, isBoardMember, member.entranceDate,
      member.leavingDate, eligibleForGunLicense
    });
  }

  private int getAddressID() {
    ArrayList<String[]> addresses = db.selectAllAdresse();
    String[] address = addresses.get(addresses.size() - 1);
    int addressID = Integer.valueOf(address[0]);

    return addressID;
  }

  private int getMemberID() {
    ArrayList<String[]> members = db.selectAllMitglieder();
    String[] member = members.get(members.size() - 1);
    int memberID = Integer.valueOf(member[0]);

    return memberID;
  }

  @Override
  protected void btnConfirm_ActionPerformed(ActionEvent evt) {
    if (!isAllRequiredTxtFieldsFilled()) {
        msgDialog.showWarningMsg(this, "Sie muessen alle erforderliche Felder ausfuellen.");
    } else {
      Member member = getMembersData();
      insertMembersDataToTable(member);

      Address address = getAddressData();
      db.insertAdresse(address);

      int addressID = getAddressID();
      db.insertMitglieder(member, addressID);

      int memberID = getMemberID();
      JCheckBox[] chcBoxDepartments = {
        chcBoxBowDepartment, chcBoxAtmosphericDepartment,
        chcBoxFirearmDepartment
      };

      for (JCheckBox chcBoxDepartment : chcBoxDepartments) {
        Department department = getDepartmentData(chcBoxDepartment);

        if (chcBoxDepartment.isSelected())  {
          db.insertAbteilung(department, memberID, 0);
        } else {
          db.insertAbteilung(department, memberID, 1);
        }
      }
    }
  }
}
