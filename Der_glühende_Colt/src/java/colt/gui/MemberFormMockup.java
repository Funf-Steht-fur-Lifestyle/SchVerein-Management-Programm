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
 * @author Naglis Vidziunas, David Stuirbrink
 */
public class MemberFormMockup extends JFrame {
  // create and initiate all components
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
  protected String[] sexType = {"maennlich", "weiblich", "divers"};
  protected String[] yesNoOption = {"Ja", "Nein"};
  protected JComboBox<String> comBoxSexSelection = new JComboBox<String>(sexType);
  protected JComboBox<String> comBoxBoardMember = new JComboBox<String>(yesNoOption);
  // protected JComboBox<String> comBoxEligibleForGunLicense = new JComboBox<String>(yesNoOption);
  protected JCheckBox chcBoxBowDepartment = new JCheckBox("Bogen");
  protected JCheckBox chcBoxAtmosphericDepartment = new JCheckBox("Luftdruck");
  protected JCheckBox chcBoxFirearmDepartment = new JCheckBox("Feuerwaffen");
  protected JTextArea txtAreaDisabilities = new JTextArea();
  protected JTextArea txtAreaNotes = new JTextArea();
  protected JButton btnConfirm = new JButton();
  protected JButton btnCancel = new JButton("Schließen");
  protected JLabel lDiscounts = new JLabel("rabatte: 0");
  protected MessageDialog msgDialog = new MessageDialog();

  private final int gapSize = 10;
  private ButtonGroup btnGroupSex = new ButtonGroup();
  private ButtonGroup btnGroupBoardMember = new ButtonGroup();
  private Database db = new Database();

  public MemberFormMockup() {
    setTitle("Hinzufügen");
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout(gapSize, gapSize));

    JPanel westPanel = initWestPanel();
    JPanel centerPanel = initCenterPanel();
    JPanel eastPanel = initEastPanel();
    JPanel southPanel = initSouthPanel();

    // tracks if the Confirm button is clicked
    btnConfirm.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnConfirm_ActionPerformed(evt);
      }
    });

    // tracks if the Cancel button is clicked
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        dispose();
      }
    });

    allowOnlyNumbers(txtFieldHouseNumber);
    allowOnlyNumbers(txtFieldPostcode);

    // configures the Panel
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new BorderLayout(gapSize, gapSize));

    topPanel.add(westPanel, BorderLayout.WEST);
    topPanel.add(centerPanel, BorderLayout.CENTER);
    topPanel.add(eastPanel, BorderLayout.EAST);
    topPanel.add(southPanel, BorderLayout.SOUTH);
    topPanel.setBorder(new EmptyBorder(gapSize, gapSize, gapSize, gapSize));

    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new GridLayout(0, 2, gapSize, gapSize));

    bottomPanel.add(btnConfirm);
    bottomPanel.add(btnCancel);
    bottomPanel.setBorder(new EmptyBorder(gapSize, gapSize, gapSize, gapSize));

    add(topPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);

    pack();
    setVisible(true);
  }

  // initiates the West Panel
  private JPanel initWestPanel() {
    JPanel westPanel = new JPanel(new GridLayout(0, 2, gapSize, gapSize));

    westPanel.add(new JLabel("Vorname"));
    westPanel.add(txtFieldFirstName);
    westPanel.add(new JLabel("Nachname"));
    westPanel.add(txtFieldLastName);
    westPanel.add(new JLabel("Geburtstag"));
    westPanel.add(txtFieldDateOfBirth);
    westPanel.add(new JLabel("IBAN"));
    westPanel.add(txtFieldIBAN);
    westPanel.add(new JLabel("Geschlecht"));
    westPanel.add(comBoxSexSelection);
    westPanel.add(new JLabel("Vorstandsmitglied"));
    westPanel.add(comBoxBoardMember);
    // westPanel.add(new JLabel("Waffenschein berechtigt"));
    // westPanel.add(comBoxEligibleForGunLicense);
    westPanel.add(new JLabel("eintrittsdatum"));
    westPanel.add(txtFieldEntranceDate);
    westPanel.add(new JLabel("austrittsdatum"));
    westPanel.add(txtFieldLeavingDate);
    TitledBorder border = new TitledBorder("Mitgliederinfo");
    westPanel.setBorder(border);

    return westPanel;
  }

  // initiates the Center Panel
  private JPanel initCenterPanel() {
    JPanel centerPanel = new JPanel(new GridLayout(0, 2, gapSize, gapSize));

    centerPanel.add(chcBoxBowDepartment);
    centerPanel.add(new JLabel("gebühren: 5"));
    centerPanel.add(chcBoxAtmosphericDepartment);
    centerPanel.add(new JLabel("gebühren: 10"));
    centerPanel.add(chcBoxFirearmDepartment);
    centerPanel.add(new JLabel("gebühren: 15"));

    centerPanel.add(lDiscounts);
    TitledBorder border = new TitledBorder("Abteilungen");
    centerPanel.setBorder(border);

    return centerPanel;
  }

  // initiates the East Panel
  private JPanel initEastPanel() {
    JPanel eastPanel = new JPanel(new GridLayout(0, 2, gapSize, gapSize));

    eastPanel.add(new JLabel("Strasse"));
    eastPanel.add(txtFieldStreet);
    eastPanel.add(new JLabel("Hausnummer"));
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
    TitledBorder border = new TitledBorder("Adresse");
    eastPanel.setBorder(border);

    return eastPanel;
  }

  // initiates the South Panel
  private JPanel initSouthPanel() {
    JPanel southPanel = new JPanel(new GridLayout(0, 2, gapSize, gapSize));
    JPanel disabilitiesPanel = new JPanel(new GridLayout(0, 1, gapSize, gapSize));
    JPanel notesPanel = new JPanel(new GridLayout(0, 1, gapSize, gapSize));

    txtAreaDisabilities.setRows(10);
    txtAreaDisabilities.setLineWrap(true);
    txtAreaDisabilities.setWrapStyleWord(true);
    JScrollPane sPaneDisabilities = new JScrollPane(txtAreaDisabilities);

    disabilitiesPanel.add(sPaneDisabilities);
    TitledBorder disabilitiesBorder = new TitledBorder("Behinderungen");
    disabilitiesPanel.setBorder(disabilitiesBorder);

    txtAreaNotes.setRows(10);
    txtAreaNotes.setLineWrap(true);
    txtAreaNotes.setWrapStyleWord(true);
    JScrollPane sPaneNotes = new JScrollPane(txtAreaNotes);

    notesPanel.add(sPaneNotes);
    TitledBorder notesBorder = new TitledBorder("Vermerke");
    notesPanel.setBorder(notesBorder);

    southPanel.add(disabilitiesPanel);
    southPanel.add(notesPanel);
    southPanel.setBorder(new EmptyBorder(gapSize, gapSize, gapSize, gapSize));

    return southPanel;
  }

  // determines the Cost for a department
  private int determineCost(JCheckBox chcBox) {
    int cost = 0;
    String departmentName = chcBox.getText();

    if (departmentName.equals("Bogen")) {
      cost = 8;
    } else if (departmentName.equals("Luftdruck")) {
      cost = 10;
    } else if (departmentName.equals("Feuerwaffen")) {
      cost = 15;
    } else {
      System.out.println("Nichts");
    }

    return cost;
  }

  int departmentsNumber = 0;

  // get all the data for the departments
  protected Department getDepartmentData(JCheckBox chcBox) {
    if (chcBox.isSelected()) {
      departmentsNumber++;
    }

    int discount = (departmentsNumber > 1) ? 18 : 0;
    int cost = determineCost(chcBox);

    Department department = new Department.Builder()
                                          .name(chcBox.getText())
                                          .cost(cost)
                                          .discount(discount)
                                          .build();

    return department;
  }

  // get the member data from the database
  protected Member getMembersData() {
    String firstName = txtFieldFirstName.getText();
    String lastName = txtFieldLastName.getText();
    String dateOfBirth = txtFieldDateOfBirth.getText();
    String iban = txtFieldIBAN.getText();
    String entranceDate = txtFieldEntranceDate.getText();
    String leavingDate = txtFieldLeavingDate.getText();
    String disabilities = txtAreaDisabilities.getText();
    String notes = txtAreaNotes.getText();
    String sex = comBoxSexSelection.getItemAt(comBoxSexSelection.getSelectedIndex());
    int boardMember = (comBoxBoardMember.getItemAt(comBoxBoardMember.getSelectedIndex()) == "Ja") ? 1 : 0;

    Member member = new Member.Builder()
                              .firstName(firstName)
                              .lastName(lastName)
                              .dateOfBirth(dateOfBirth)
                              .iban(iban)
                              .sex(sex)
                              .disabilities(disabilities)
                              .boardMember(boardMember)
                              .entranceDate(entranceDate)
                              .leavingDate(leavingDate)
                              .notes(notes)
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

  // ensure that the user has filled all Fields
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

  // this method ensures that the user can only enter numbers
  private void allowOnlyNumbers(JTextField txtField) {
    txtField.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ke) {
        if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9'
            || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE
        ) {
          txtField.setEditable(true);
        } else {
          txtField.setEditable(false);
          msgDialog.showWarningMsg(null, "Bitte geben Sie nur Zahlen an.");
          txtField.setEditable(true);
        }
      }
    });
  }

  protected void btnConfirm_ActionPerformed(ActionEvent evt) {}
}
