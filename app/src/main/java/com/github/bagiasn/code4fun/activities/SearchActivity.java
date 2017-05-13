package com.github.bagiasn.code4fun.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.bagiasn.code4fun.R;
import com.github.bagiasn.code4fun.helpers.HttpHelper;
import com.github.bagiasn.code4fun.helpers.JsonHelper;
import com.github.bagiasn.code4fun.models.database.Attribute;
import com.github.bagiasn.code4fun.models.database.CategoryChild;
import com.github.bagiasn.code4fun.others.OptionItemDecoration;

import java.util.ArrayList;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class SearchActivity extends Activity {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private ArrayList<Attribute> attributesList;
    private SectionedRecyclerViewAdapter sectionAdapter;
    private static final String[] CATEGORIES = new String[] {
            "Οικογένεια", "Κατοικία", "Δημόσια Τάξη", "Επιδόματα"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        attributesList = new ArrayList<>();
        sectionAdapter = new SectionedRecyclerViewAdapter();

        RecyclerView recyclerAttr = (RecyclerView) findViewById(R.id.attribute_list);
        recyclerAttr.setLayoutManager(new LinearLayoutManager(this));
        recyclerAttr.addItemDecoration(new OptionItemDecoration(5));

        new GetAttributes().execute();
    }

    private class GetAttributes extends AsyncTask<String,Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String url = "http://46.101.196.53:54870/getListOfServices";
            String jsonResponse = HttpHelper.makeJsonRequest(url);
            if (jsonResponse != null) {
                attributesList = JsonHelper.getAttributes(jsonResponse);
                if (attributesList != null) {
                    for (String category: CATEGORIES){
                        ArrayList<Attribute> subList = new ArrayList<>();
                        for (Attribute attr : attributesList) {
                            String attrCategory = attr.getCategory();
                            if (category.equals(attrCategory)) {
                                subList.add(attr);
                            }
                        }
                        sectionAdapter.addSection(new BasicCategory(subList, category));
                    }
                    return true;
                }
            }
            Log.d(TAG, "jsonResponse EMPTY");
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result) {
                AutoCompleteTextView searchBox = (AutoCompleteTextView) findViewById(R.id.search_box);
                searchBox.setAdapter(new ArrayAdapter<>(SearchActivity.this,
                        android.R.layout.simple_dropdown_item_1line, CATEGORIES));
                RecyclerView recyclerAttr = (RecyclerView) findViewById(R.id.attribute_list);
                recyclerAttr.setAdapter(sectionAdapter);
            } else {
                Toast.makeText(SearchActivity.this, R.string.error_services, Toast.LENGTH_SHORT).show();
            }
        }


    }
    class BasicCategory extends StatelessSection {

        ArrayList<Attribute> list = new ArrayList<>();
        String title;

        public BasicCategory(ArrayList<Attribute> list, String title) {
            // call constructor with layout resources for this Section header and items
            super(R.layout.section_header, R.layout.section_item);
            this.list = list;
            this.title = title;
        }

        @Override
        public int getContentItemsTotal() {
            return list.size(); // number of items of this section
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            // return a custom instance of ViewHolder for the items of this section
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;

            // bind your view here
            itemHolder.tvItem.setText(list.get(position).getName());
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.tvTitle.setText(title);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        public HeaderViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.header_section);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvItem;

        public ItemViewHolder(View itemView) {
            super(itemView);

            tvItem = (TextView) itemView.findViewById(R.id.header_item);
        }
    }
}
