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
 * MemberFormMockup - a class that defines the design for
 * member form, for example member addition form and/or
 * member edit form. This class is used with other two
 * classes MemberAdditionForm and MemberEditForm.
 *
 * @version 1.0 from 06.04.2021
 * @author Naglis Vidziunas
 */
public class MemberFormMockup extends JFrame {
  protected DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  protected JTextField txtFieldFirstName = new JTextField();
  protected JTextField txtFieldLastName = new JTextField();
  protected JFormattedTextField txtFieldDateOfBirth = new JFormattedTextField(df);
  protected JTextField txtFieldIBAN = new JTextField();
  protected JFormattedTextField txtFieldEntranceDate = new JFormattedTextField(df);
  protected JFormattedTextField txtFieldLeavingDate = new JFormattedTextField(df);
  protected JTextField txtFieldStreet = new JTextField();
  protected JTextField txtFieldHouseNumber = new JTextField();
  protected JTextField txtFieldHouseNumberAdditional = new JTextField();
  protected JTextField txtFieldPostcode = new JTextField();
  protected JTextField txtFieldLocation = new JTextField();
  protected JTextField txtFieldCountry = new JTextField();
  protected JTextField txtFieldState = new JTextField();
  protected JRadioButton rBtnMan = new JRadioButton("maennlich");
  protected JRadioButton rBtnWoman = new JRadioButton("weiblich");
  protected JRadioButton rBtnDiverse = new JRadioButton("diverse");
  protected JRadioButton rBtnIsBoardMember = new JRadioButton("Ja");
  protected JRadioButton rBtnIsNotBoardMember = new JRadioButton("Nein");
  protected JTextArea txtAreaDisabilities = new JTextArea();
  protected JTextArea txtAreaNotes = new JTextArea();
  protected JButton btnConfirm = new JButton();
  protected JButton btnCancel = new JButton("Schließen");

  private final int gapSize = 10;
  private ButtonGroup btnGroupSex = new ButtonGroup();
  private ButtonGroup btnGroupBoardMember = new ButtonGroup();

  public MemberFormMockup() {
    setTitle("Hinzufügen");
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout(gapSize, gapSize));

    JPanel westPanel = initWestPanel();
    JPanel eastPanel = initEastPanel();
    JPanel southPanel = initSouthPanel();

    btnConfirm.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btnConfirm_ActionPerformed(evt);
      }
    });

    btnCancel.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        dispose();
      }
    });

    allowOnlyNumbers(txtFieldHouseNumber);
    allowOnlyNumbers(txtFieldPostcode);

    add(westPanel, BorderLayout.WEST);
    add(eastPanel, BorderLayout.EAST);
    add(southPanel, BorderLayout.SOUTH);

    pack();
    setVisible(true);
  }

  private JPanel initWestPanel() {
    JPanel westPanel = new JPanel(new GridLayout(0, 3, gapSize, gapSize));

    westPanel.add(new JLabel("Vorname"));
    westPanel.add(txtFieldFirstName);
    westPanel.add(new JLabel());
    westPanel.add(new JLabel("Nachname"));
    westPanel.add(txtFieldLastName);
    westPanel.add(new JLabel());
    westPanel.add(new JLabel("Geburtstag"));
    westPanel.add(txtFieldDateOfBirth);
    westPanel.add(new JLabel());
    westPanel.add(new JLabel("IBAN"));
    westPanel.add(txtFieldIBAN); 
    westPanel.add(new JLabel());
    westPanel.add(rBtnMan);
    westPanel.add(rBtnWoman);
    westPanel.add(rBtnDiverse);
    westPanel.add(new JLabel("Vorstandsmitglied"));
    westPanel.add(rBtnIsBoardMember);
    westPanel.add(rBtnIsNotBoardMember);
    westPanel.add(new JLabel("eintrittsdatum"));
    westPanel.add(txtFieldEntranceDate);
    westPanel.add(new JLabel());
    westPanel.add(new JLabel("austrittsdatum"));
    westPanel.add(txtFieldLeavingDate);
    westPanel.add(new JLabel());
    westPanel.add(new JLabel("behinderungen"));
    westPanel.add(txtAreaDisabilities);
    westPanel.add(new JLabel());
    westPanel.add(new JLabel("Vermerke"));
    westPanel.add(txtAreaNotes);
    westPanel.add(new JLabel());
    westPanel.setBorder(new EmptyBorder(gapSize, gapSize, gapSize, gapSize));

    btnGroupSex.add(rBtnMan);
    btnGroupSex.add(rBtnWoman);
    btnGroupSex.add(rBtnDiverse);

    btnGroupBoardMember.add(rBtnIsBoardMember);
    btnGroupBoardMember.add(rBtnIsNotBoardMember);

    return westPanel;
  }

  private JPanel initEastPanel() {
    JPanel eastPanel = new JPanel(new GridLayout(0, 2, gapSize, gapSize));

    eastPanel.add(new JLabel("Strasse"));
    eastPanel.add(txtFieldStreet);
    eastPanel.add(new JLabel("Hausenummer"));
    eastPanel.add(txtFieldHouseNumber);
    eastPanel.add(new JLabel("Hausnummer Zusatz"));
    eastPanel.add(txtFieldHouseNumberAdditional);
    eastPanel.add(new JLabel("Postleitzahl"));
    eastPanel.add(txtFieldPostcode); 
    eastPanel.add(new JLabel("Ort"));
    eastPanel.add(txtFieldLocation);
    eastPanel.add(new JLabel("Land"));
    eastPanel.add(txtFieldCountry);
    eastPanel.add(new JLabel("Bundesland"));
    eastPanel.add(txtFieldState);
    eastPanel.setBorder(new EmptyBorder(gapSize, gapSize, gapSize, gapSize));

    return eastPanel;
  }

  private JPanel initSouthPanel() {
    JPanel southPanel = new JPanel(new GridLayout(0, 2, gapSize, gapSize));

    southPanel.add(btnConfirm);
    southPanel.add(btnCancel);
    southPanel.setBorder(new EmptyBorder(gapSize, gapSize, gapSize, gapSize));

    return southPanel;
  }

  private String getSex() {
    String sex = "";

    if (rBtnMan.isSelected()) {
      sex = rBtnMan.getText();
    } else if (rBtnWoman.isSelected()) {
      sex = rBtnWoman.getText();
    } else if (rBtnDiverse.isSelected()) {
      sex = rBtnDiverse.getText();
    } else {
      showWarningMsg("Bitte wählen Sie ihre Geschlechte aus.");
    }

    return sex;
  }

  protected void showWarningMsg(String msg) {
    JOptionPane.showMessageDialog(this, msg, "Attention", JOptionPane.WARNING_MESSAGE);
  }

  protected Member getMembersData() {
    String firstName = txtFieldFirstName.getText();
    String lastName = txtFieldLastName.getText();
    String dateOfBirth = txtFieldDateOfBirth.getText();
    String iban = txtFieldIBAN.getText();
    String entranceDate = txtFieldEntranceDate.getText();
    String leavingDate = txtFieldLeavingDate.getText();
    String disabilities = txtAreaDisabilities.getText();
    String notes = txtAreaNotes.getText();
    String sex = getSex();
    int boardMember = rBtnIsBoardMember.isSelected() ? 1 : 0;

    Member member = new Member.Builder()
                  .firstName(firstName)
                  .lastName(lastName)
                  .dateOfBirth(dateOfBirth)
                  .iban(iban)
                  .sex(sex)
                  .disabilities(disabilities)
                  .boardMember(boardMember)
                  .entranceDate(entranceDate)
                  .build();

    return member;
  }

  protected void insertMembersDataToTable(Member member) {}

  protected Address getAddressData() {
    String street = txtFieldStreet.getText();
    int houseNumber = Integer.parseInt(txtFieldHouseNumber.getText());
    String houseNumberAdditional = txtFieldHouseNumberAdditional.getText();
    int postcode = Integer.parseInt(txtFieldPostcode.getText());
    String location = txtFieldLocation.getText();
    String country = txtFieldCountry.getText();
    String state = txtFieldState.getText();

    Address address = new Address.Builder()
                    .street(street)
                    .houseNumber(houseNumber)
                    .houseNumberAdditional(houseNumberAdditional)
                    .postcode(postcode)
                    .location(location)
                    .country(country)
                    .state(state)
                    .build();

    return address;
  }

  protected boolean isAllRequiredTxtFieldsFilled() {
    JTextField[] txtFields = {
      txtFieldFirstName, txtFieldLastName,
      txtFieldDateOfBirth, txtFieldIBAN,
      txtFieldState, txtFieldHouseNumber,
      txtFieldPostcode, txtFieldLocation,
      txtFieldCountry, txtFieldState
    };

    for (JTextField txtField : txtFields) {
      if (txtField.getText().equals("")) {
        return false;
      }
    }

    return true;
  }

  private void allowOnlyNumbers(JTextField txtField) {
    txtField.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ke) {
        if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9'
            || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE
        ) {
          txtField.setEditable(true);
        } else {
          txtField.setEditable(false);
          showWarningMsg("Bitte geben Sie nur Zahlen an.");
          txtField.setEditable(true);
        }
      }
    });
  }

  protected void btnConfirm_ActionPerformed(ActionEvent evt) {}
}
