package com.github.bagiasn.code4fun.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


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
        String title = callingIntent.getStringExtra("title");
        String documents = callingIntent.getStringExtra("documents");
        final String link = callingIntent.getStringExtra("link");
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

        if (!link.isEmpty()) {
            Button btnLink = (Button) findViewById(R.id.button_link);
            btnLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
            btnLink.setVisibility(View.VISIBLE);

//            TextView textView = (TextView) findViewById(R.id.label_documents);
//            textView.setClickable(true);
//            textView.setMovementMethod(LinkMovementMethod.getInstance());
//            String text = "<a href='" + link + "'>" + getString(R.string.required_documents) + "</a>";
//            textView.setText(Html.fromHtml(text));
        }

        TextView header = (TextView) findViewById(R.id.header_attr);
        header.setText(title);

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
