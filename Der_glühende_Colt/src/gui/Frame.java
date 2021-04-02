package colt.gui;

import java.util.*;


import java.time.format.*;
import java.time.*;

import colt.*;

public class Frame {

    public static void main(String[] args) {

        @SuppressWarnings("unused")
        GuiLogin loginScreen = new GuiLogin();

        // This code will be gone in the future.
        // It is here only for testing purposes.
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime currentDate = LocalDateTime.now();
        String date = dtf.format(currentDate);
        Database db = new Database();
        MemberSearcher searcher = new MemberSearcher();
        Member member = new Member.Builder()
                                  .firstName("john")
                                  .lastName("Jobs")
                                  .dateOfBirth("1996/02/13")
                                  .iban("DE34546622345")
                                  .sex("maennlich")
                                  .disabilities("some")
                                  .boardMember(1)
                                  .entranceDate(date)
                                  .notes("SOEM AHJKLJFALDJSLF KJALS")
                                  .build();

        db.insertMitglieder(member);
        ArrayList<String> result = searcher.search("john");

        for (String r : result) {
            System.out.println(result);
        }

    }

}
