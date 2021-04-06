package colt.gui;

import java.awt.*;

import javax.swing.*;

/**
 * MessageDialog - a simple class that uses JOPtionPane to
 * display information for the user.
 *
 * @version 1.0 from 06.04.2021
 * @author Naglis Vidziunas
 */
public class MessageDialog extends JOptionPane {
  public void showWarningMsg(Component comp, String msg) {
    showMessageDialog(comp, msg, "Achtung", JOptionPane.WARNING_MESSAGE);
  }

  public void showInfoMsg(Component comp, String msg) {
    showMessageDialog(comp, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
  }
}
