package com.github.bagiasn.code4fun.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.github.bagiasn.code4fun.R;
import com.github.bagiasn.code4fun.others.GVoiceRecog;

public class VoiceSearchActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_search);

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

    public void showProgress() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.speaking_progress);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.speaking_progress);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
