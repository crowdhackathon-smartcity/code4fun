package com.github.bagiasn.code4fun.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.github.bagiasn.code4fun.R;
import com.github.bagiasn.code4fun.adapters.DocumentsAdapter;
import com.github.bagiasn.code4fun.adapters.MenuAdapter;
import com.github.bagiasn.code4fun.models.app.Menu;
import com.github.bagiasn.code4fun.others.OptionItemDecoration;

import java.util.ArrayList;

public class AttributeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute);

        Intent callingIntent = getIntent();
        String documents = callingIntent.getStringExtra("documents");
        String organizations = callingIntent.getStringExtra("orgs");

        String[] documentsList = documents.split("#");
        ListView recyclerDocuments = (ListView) findViewById(R.id.list_documents);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_selectable_list_item, documentsList);
        recyclerDocuments.setAdapter(adapter);
    }
}
