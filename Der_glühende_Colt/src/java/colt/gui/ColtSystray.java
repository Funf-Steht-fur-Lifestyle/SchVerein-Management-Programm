package colt.gui;

import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;

import javax.swing.*;

import colt.*;

/**
 * ColtSystray - a simple class that is responsible for
 * handling this programs system tray.
 *
 * @version 1.0 vom 07.04.2021
 * @author Naglis Vidziunas
 */
public class ColtSystray {
  private TrayIcon trayIcon;

  public ColtSystray() {
    SystemTray tray = SystemTray.getSystemTray();
    ImageIcon icon = new ImageIcon(getClass().getResource("keyboard.png"));
    Image image = icon.getImage();
    PopupMenu popup = createPopup();

    try {
      trayIcon = new TrayIcon(image, "Schützenverein", popup);
      trayIcon.setImageAutoSize(true);
      trayIcon.setToolTip("Erinnerung Schützenverein");
      tray.add(trayIcon);
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }

  private PopupMenu createPopup() {
    PopupMenu popup = new PopupMenu();
    MenuItem menuItemExit = new MenuItem("Exit");
    menuItemExit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    MenuItem menuItemPasswordChanger = new MenuItem("Passwort ändern");
    menuItemPasswordChanger.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        GuiPasswordChanger passwordChangerScreen = new GuiPasswordChanger();
      }
    });
    popup.add(menuItemPasswordChanger);
    popup.add(menuItemExit);

    return popup;
  }

  /**
   * BirthdayReminder - a simple class that is responsible
   * for reminding a user, when it is members, which are
   * stored in database, birthdays.
   *
   * @version 1.0 vom 07.04.2021
   * @author Naglis Vidziunas
   */
  public class BirthdayReminder extends TimerTask {
    @Override
    public void run() {
      Database db = new Database();
      ArrayList<String[]> members = db.selectAllMitglieder();

      for (String[] member : members) {
        LocalDate membersBirthday = LocalDate.parse(member[3]);
        LocalDate currentDate = LocalDate.now();
        long dayDifference = Period.between(membersBirthday, currentDate).getDays();

        if (dayDifference <= 7) {
          String fullName = member[1] + " " + member[2];
          String msg = String.format("In %d Tagen %s wird ein Geburtstag haben!", dayDifference, fullName);
          trayIcon.displayMessage("Geburtstag Erinnerung", msg, TrayIcon.MessageType.INFO);
        }
      }
    }
  }

  public void notifyOfMembersBirthday() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 8);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    BirthdayReminder task = new BirthdayReminder();
    java.util.Timer timer = new java.util.Timer();
    timer.schedule(task, calendar.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
  }
}

