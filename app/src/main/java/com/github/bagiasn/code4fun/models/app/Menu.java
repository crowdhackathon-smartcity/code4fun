package com.github.bagiasn.code4fun.models.app;

import com.github.bagiasn.code4fun.R;

import java.util.ArrayList;

/**
 * This class is used for populating recycler view with static content.
 */

public class Menu {
    private static ArrayList<Option> optionList;

    public static ArrayList<Option> getOptions () {
        optionList = new ArrayList<>();

        optionList.add(new Option("Ψάξε το", "Αναζήτηση δικαιολογητικών, λήψη πληροφοριών", R.drawable.calendar_date_event));
        optionList.add(new Option("Πες το", "Φωνητική αναζήτηση δικαιολογητικών", R.drawable.upcoming_events));
        optionList.add(new Option("Ιστορικό Αναζητήσεων", "Προβολή ιστορικού αναζήτησης", R.drawable.history_event));

        return optionList;
    }
}
