package colt;

import java.util.*;

/**
 * MemberSearcher - a simple class that is responsible for
 * searching given members within database.
 *
 * @version 1.0 from 02.04.2021
 * @author Naglis Vidziunas
 */
public class MemberSearcher {
    public ArrayList<String[]> search(String firstName, String lastName) {
        Database db = new Database();
        ArrayList<String[]> result = new ArrayList<String[]>();
        ArrayList<String[]> members = db.selectAllMitglieder();

        for (String[] member : members) {
            if (member[0].equals(firstName) && member[1].equals(lastName)) {
                result.add(member);
            }
        }

        return result;
    }
}
