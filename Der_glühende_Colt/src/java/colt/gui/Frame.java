package colt.gui;

import java.util.*;

import colt.*;

public class Frame {

  public static void main(String[] args) {

    @SuppressWarnings("unused")
    GuiLogin loginScreen = new GuiLogin();

    Database db = new Database();
    ArrayList<String> user = db.selectUser();

    if (user.isEmpty()) {
      PasswordHashing passHashing = new PasswordHashing();
      String password = passHashing.hash("admin123");
      db.insertUser("admin", password);
    }

  }

}
