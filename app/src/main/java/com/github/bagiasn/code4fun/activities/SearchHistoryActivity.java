package com.github.bagiasn.code4fun.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.bagiasn.code4fun.R;
import com.github.bagiasn.code4fun.adapters.CategoryChildAdapter;
import com.github.bagiasn.code4fun.models.database.CategoryChild;
import com.github.bagiasn.code4fun.others.OptionItemDecoration;

import java.util.ArrayList;

public class SearchHistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);

        SharedPreferences prefs = this.getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String history = prefs.getString("SavedSearches", "");
        if (history.isEmpty()) return;

        ArrayList<CategoryChild> previousSearches = new ArrayList<>();
        String[] searches = history.split("#");
        for (String search: searches) {
            String[] parts = search.split("!");
            CategoryChild child =  new CategoryChild();
            child.setId(parts[0]);
            child.setTitle(parts[1]);

            previousSearches.add(child);
        }

        RecyclerView recyclerHistory = (RecyclerView) findViewById(R.id.recycler_history);
        recyclerHistory.setAdapter(new CategoryChildAdapter(previousSearches, this));
        recyclerHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerHistory.addItemDecoration(new OptionItemDecoration(5));
    }

}
