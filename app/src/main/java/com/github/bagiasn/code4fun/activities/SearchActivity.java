package com.github.bagiasn.code4fun.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.github.bagiasn.code4fun.R;
import com.github.bagiasn.code4fun.adapters.AttributeAdapter;
import com.github.bagiasn.code4fun.helpers.HttpHelper;
import com.github.bagiasn.code4fun.helpers.JsonHelper;
import com.github.bagiasn.code4fun.models.database.Attribute;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private ArrayList<Attribute> attributesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        attributesList = new ArrayList<>();

        new GetAttributes().execute();
    }


    private class GetAttributes extends AsyncTask<String,Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String url = "SERVER_IP_HERE";
            String jsonResponse = HttpHelper.makeJsonRequest(url);
            if (jsonResponse != null) {
                attributesList = JsonHelper.getAttributes(jsonResponse);
                if (attributesList != null)
                    return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result) {
                RecyclerView recyclerAttr = (RecyclerView) findViewById(R.id.attribute_list);
                AttributeAdapter attributeAdapter = new AttributeAdapter(attributesList, SearchActivity.this);
                recyclerAttr.setAdapter(attributeAdapter);
            } else {
                Toast.makeText(SearchActivity.this, R.string.error_services, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
