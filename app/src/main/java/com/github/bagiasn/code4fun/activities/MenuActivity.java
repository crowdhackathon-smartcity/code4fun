package com.github.bagiasn.code4fun.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.github.bagiasn.code4fun.others.OptionItemDecoration;
import com.github.bagiasn.code4fun.R;
import com.github.bagiasn.code4fun.adapters.MenuAdapter;
import com.github.bagiasn.code4fun.models.app.Menu;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        MenuAdapter menuAdapter = new MenuAdapter(Menu.getOptions(), this);

        RecyclerView menu = (RecyclerView) findViewById(R.id.recycler_menu);
        menu.setAdapter(menuAdapter);

        menu.setLayoutManager(new LinearLayoutManager(this));
        menu.addItemDecoration(new OptionItemDecoration(5));
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setSubtitle("O ψηφιακός βοηθός του πολίτη");
        myToolbar.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitle);
    }
}
