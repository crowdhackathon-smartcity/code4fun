package com.github.bagiasn.myticket.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.github.bagiasn.myticket.others.OptionItemDecoration;
import com.github.bagiasn.myticket.R;
import com.github.bagiasn.myticket.adapters.MenuAdapter;
import com.github.bagiasn.myticket.models.app.Menu;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        MenuAdapter menuAdapter = new MenuAdapter(Menu.getOptions());

        RecyclerView menu = (RecyclerView) findViewById(R.id.recycler_menu);
        menu.setAdapter(menuAdapter);

        menu.setLayoutManager(new LinearLayoutManager(this));
        menu.addItemDecoration(new OptionItemDecoration(15));
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setSubtitle("Κάτι κλισέ...");
        myToolbar.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitle);
    }
}
