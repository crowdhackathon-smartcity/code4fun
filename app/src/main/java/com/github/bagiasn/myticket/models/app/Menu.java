package com.github.bagiasn.myticket.models.app;

import com.github.bagiasn.myticket.R;

import java.util.ArrayList;

/**
 * This class is used for populating recycler view with static content.
 */

public class Menu {
    private static ArrayList<Option> optionList;

    public static ArrayList<Option> getOptions () {
        optionList = new ArrayList<>();

        optionList.add(new Option("Κλείσε ραντεβού", "Επιλογή οργανισμού, υπηρεσίας, ώρας", R.drawable.calendar_date_event));
        optionList.add(new Option("Προσεχή ραντεβού", "Προβολή επερχόμενων ραντεβού", R.drawable.upcoming_events));
        optionList.add(new Option("Παλαιότερα ραντεβού", "Προβολή ιστορικού", R.drawable.history_event));

        return optionList;
    }
}
