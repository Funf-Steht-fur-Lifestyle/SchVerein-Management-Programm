package colt.gui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import colt.*;

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
public class MemberEditForm extends MemberAdditionForm {
    private String memberID;
    private DefaultTableModel tmData;
    private JTable tData;

    public MemberEditForm(JTable tData, DefaultTableModel tmData) {
        setTitle("Bearbeiten");

        this.tmData = tmData;
        this.tData = tData;

        int row = tData.getSelectedRow();
        String firstName = tData.getModel().getValueAt(row, 0).toString();
        String lastName = tData.getModel().getValueAt(row, 1).toString();
        String dateOfBirth = tData.getModel().getValueAt(row, 2).toString();
        String iban = tData.getModel().getValueAt(row, 3).toString();
        String sex = tData.getModel().getValueAt(row, 4).toString();
        String disabilities = tData.getModel().getValueAt(row, 5).toString();
        String boardMember = tData.getModel().getValueAt(row, 6).toString();
        String entranceDate = tData.getModel().getValueAt(row, 7).toString();
        String leavingDate = (tData.getModel().getValueAt(row, 8) == null) ? ""
                           : tData.getModel().getValueAt(row, 8).toString();
        String notes = (tData.getModel().getValueAt(row, 9) == null) ? ""
                     : tData.getModel().getValueAt(row, 9).toString();

        Database db = new Database();
        ArrayList<String[]> members = db.selectAllMitglieder();
        ArrayList<String[]> addresses = db.selectAllAdresse();

        for (String[] member : members) {
            if (member[1].equals(firstName) && member[2].equals(lastName)) {
                memberID = member[0];

                String addressID = member[11];
                System.out.println("ADDRESS: " + addressID);

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
        }

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

    private void setSex(String sex) {
        if (sex.equals("maennlich")) {
            checkBoxMan.setSelected(true);
        } else if (sex.equals("weiblich")) {
            checkBoxWoman.setSelected(true);
        } else {
            checkBoxDiverse.setSelected(true);
        }
    }

    private void setBoardMember(String boardMember) {
        if (boardMember.equals("Ja")) {
            checkBoxIsBoardMember.setSelected(true);
        } else {
            checkBoxIsNotBoardMember.setSelected(true);
        }
    }

    @Override
    protected void insertMembersDataToTable(Member member) {
        String isBoardMember = "Nein";

        if (member.boardMember == 1) {
            isBoardMember = "Ja";
        }

        int row = tData.getSelectedRow();
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
    protected void btnAdd_ActionPerformed(ActionEvent evt) {
        Database db = new Database();

        if (!isAllRequiredTxtFieldsFilled()) {
            showWarningMsg("Sie müssen alle erforderliche Felder ausfüllen.");
        } else {
            Member member = getMembersData();
            insertMembersDataToTable(member);
            db.updateMitglieder(member, Integer.valueOf(memberID));

            // Address address = getAddressData();
            // db.updateAdresse(address);
        }
    }
}
