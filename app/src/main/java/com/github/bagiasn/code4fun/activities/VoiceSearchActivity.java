package com.github.bagiasn.code4fun.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.github.bagiasn.code4fun.R;
import com.github.bagiasn.code4fun.models.database.CategoryChild;
import com.github.bagiasn.code4fun.others.GVoiceRecog;
import com.github.bagiasn.code4fun.others.OptionItemDecoration;
import com.google.gson.Gson;

public class VoiceSearchActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_search);

        RecyclerView recyclerAttr = (RecyclerView) findViewById(R.id.attribute_list_voice);
        recyclerAttr.setLayoutManager(new LinearLayoutManager(this));
        recyclerAttr.addItemDecoration(new OptionItemDecoration(5));

        ImageView btnVoiceSearch = (ImageView) findViewById(R.id.button_voice_search);
        btnVoiceSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                mainHandler.post(new GVoiceRecog(VoiceSearchActivity.this));
            }
        });
    }
}
