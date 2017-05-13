package com.github.bagiasn.code4fun.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import com.github.bagiasn.code4fun.R;
import com.github.bagiasn.code4fun.adapters.CategoryChildAdapter;
import com.github.bagiasn.code4fun.models.database.CategoryChild;
import com.github.bagiasn.code4fun.others.OptionItemDecoration;

import java.util.ArrayList;

public class AttributeActivity extends Activity implements View.OnClickListener {
    private String organizations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute);

        Intent callingIntent = getIntent();
        String documents = callingIntent.getStringExtra("documents");
        organizations = callingIntent.getStringExtra("orgs");
        String childAttributes = callingIntent.getStringExtra("services");
        if (!childAttributes.isEmpty()) {
            ArrayList<CategoryChild> categoryChildren = new ArrayList<>();
            String[] servicesList = childAttributes.split("#");
            for (String service : servicesList) {
                String[] serviceParts = service.split("!");
                CategoryChild categoryChild = new CategoryChild();
                categoryChild.setId(serviceParts[0]);
                categoryChild.setTitle(serviceParts[1]);
                categoryChildren.add(categoryChild);
            }

            RecyclerView recyclerServices = (RecyclerView) findViewById(R.id.recycler_services);
            recyclerServices.setAdapter(new CategoryChildAdapter(categoryChildren, this));
            recyclerServices.setLayoutManager(new LinearLayoutManager(this));
            recyclerServices.addItemDecoration(new OptionItemDecoration(5));
        }
        String[] documentsList = documents.split("#");
        ListView listDocuments = (ListView) findViewById(R.id.list_documents);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_selectable_list_item, documentsList);
        listDocuments.setAdapter(adapter);

        Button btnMaps = (Button) findViewById(R.id.button_maps);
        btnMaps.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(AttributeActivity.this, MapsActivity.class);
        intent.putExtra("keyword", organizations);
        startActivity(intent);
    }
}
