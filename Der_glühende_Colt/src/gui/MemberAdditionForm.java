package colt.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import colt.*;

/**
 * MemberAdditionForm - a class that represents a from in
 * GUI, where needed information has to be filled, such as
 * firstName, lastName, dateOfBirth, and so on. This class
 * is used for member addition in a GUI form.
 *
 * @version 1.0 from 03.04.2021
 * @author Naglis Vidziunas
 */
public class MemberAdditionForm extends JFrame {
  protected JTextField txtFieldFirstName = new JTextField();
  protected JTextField txtFieldLastName = new JTextField();
  protected JTextField txtFieldDateOfBirth = new JTextField();
  protected JTextField txtFieldIBAN = new JTextField();
  protected JTextField txtFieldEntranceDate = new JTextField();
  protected JTextField txtFieldLeavingDate = new JTextField();
  protected JTextField txtFieldStreet = new JTextField();
  protected JTextField txtFieldHouseNumber = new JTextField();
  protected JTextField txtFieldHouseNumberAdditional = new JTextField();
  protected JTextField txtFieldPostcode = new JTextField();
  protected JTextField txtFieldLocation = new JTextField();
  protected JTextField txtFieldCountry = new JTextField();
  protected JTextField txtFieldState = new JTextField();
  protected JCheckBox checkBoxMan = new JCheckBox("maennlich");
  protected JCheckBox checkBoxWoman = new JCheckBox("weiblich");
  protected JCheckBox checkBoxDiverse = new JCheckBox("diverse");
  protected JCheckBox checkBoxIsBoardMember = new JCheckBox("Ja");
  protected JCheckBox checkBoxIsNotBoardMember = new JCheckBox("Nein");
  protected JTextArea txtAreaDisabilities = new JTextArea();
  protected JTextArea txtAreaNotes = new JTextArea();
  protected JButton btnAdd = new JButton("Hinzufügen");
  protected JButton btnCancel = new JButton("Schliessen");

  private final int gapSize = 10;
  private DefaultTableModel tmData;
  private Database db = new Database();

  public MemberAdditionForm() {
    setTitle("Hinzufügen");
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout(gapSize, gapSize));

    JPanel westPanel = initWestPanel();
    JPanel eastPanel = initEastPanel();
    JPanel southPanel = initSouthPanel();

    btnAdd.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btnAdd_ActionPerformed(evt);
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

  public MemberAdditionForm(DefaultTableModel tmData) {
    setTitle("Hinzufügen");
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout(gapSize, gapSize));

    this.tmData = tmData;

    JPanel westPanel = initWestPanel();
    JPanel eastPanel = initEastPanel();
    JPanel southPanel = initSouthPanel();

    btnAdd.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btnAdd_ActionPerformed(evt);
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
    westPanel.add(checkBoxMan);
    westPanel.add(checkBoxWoman);
    westPanel.add(checkBoxDiverse);
    westPanel.add(new JLabel("Vorstandsmitglied"));
    westPanel.add(checkBoxIsBoardMember);
    westPanel.add(checkBoxIsNotBoardMember);
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

    southPanel.add(btnAdd);
    southPanel.add(btnCancel);
    southPanel.setBorder(new EmptyBorder(gapSize, gapSize, gapSize, gapSize));

    return southPanel;
  }

  private String getSex() {
    String sex = "";

    if (checkBoxMan.isSelected()) {
      sex = checkBoxMan.getText();
    } else if (checkBoxWoman.isSelected()) {
      sex = checkBoxWoman.getText();
    } else if (checkBoxDiverse.isSelected()) {
      sex = checkBoxDiverse.getText();
    } else {
      showWarningMsg("Bitte wählen Sie ihre Geschlechte aus.");
    }

    return sex;
  }

  private int isBoardMember() {
    if (checkBoxIsBoardMember.isSelected()) {
      return 1;
    }

    return 0;
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
    int boardMember = isBoardMember();

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

  protected void insertMembersDataToTable(Member member) {
    String isBoardMember = "Nein";

    if (member.boardMember == 1) {
      isBoardMember = "Ja";
    }

    tmData.addRow(new Object[]{
      member.firstName, member.lastName, member.dateOfBirth,
      member.iban, member.sex, member.disabilities, isBoardMember,
      member.entranceDate, member.leavingDate, member.notes
    });
  }

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

  private int getAddressID() {
    ArrayList<String[]> addresses = db.selectAllAdresse();
    String[] address = addresses.get(addresses.size() - 1);
    int addressID = Integer.valueOf(address[0]);

    return addressID;
  }

  protected void btnAdd_ActionPerformed(ActionEvent evt) {
    if (!isAllRequiredTxtFieldsFilled()) {
        showWarningMsg("Sie müssen alle erforderliche Felder ausfüllen.");
    } else {
      Member member = getMembersData();
      insertMembersDataToTable(member);

      Address address = getAddressData();
      db.insertAdresse(address);

      int addressID = getAddressID();
      db.insertMitglieder(member, addressID);
    }
  }
}
