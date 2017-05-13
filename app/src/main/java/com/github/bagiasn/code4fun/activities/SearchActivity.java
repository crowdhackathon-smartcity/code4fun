package com.github.bagiasn.code4fun.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.bagiasn.code4fun.R;
import com.github.bagiasn.code4fun.adapters.CategoryChildAdapter;
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
    private ArrayList<String> autoCompleteList;
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
                    // Construct the autocomplete list.
                    autoCompleteList = new ArrayList<>();
                    for (Attribute attr : attributesList) {
                        autoCompleteList.add(attr.getName());
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
                        android.R.layout.simple_dropdown_item_1line, autoCompleteList.toArray()));
                searchBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedTitle = autoCompleteList.get(position);
                        String selectedId = getIdFromList(selectedTitle);
                        new GetAttributeById().execute(selectedId);
                    }
                });
                RecyclerView recyclerAttr = (RecyclerView) findViewById(R.id.attribute_list);
                recyclerAttr.setAdapter(sectionAdapter);
            } else {
                Toast.makeText(SearchActivity.this, R.string.error_services, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getIdFromList(String selectedTitle) {
        for (Attribute attr : attributesList) {
            String name = attr.getName();
            if (selectedTitle.equals(name)) {
                return attr.getId();
            }
        }
        return "";
    }

    private class GetAttributeById extends AsyncTask<String,Void, Boolean> {
        private Attribute attribute;
        @Override
        protected Boolean doInBackground(String... params) {
            String url = "http://46.101.196.53:54870/getServiceById?serviceId=" + params[0];
            String jsonResponse = HttpHelper.makeJsonRequest(url);
            if (jsonResponse != null) {
                attribute = JsonHelper.getAttribute(jsonResponse);
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result) {
                String listString = "";
                for (String s : attribute.getDocsList()) {
                    listString += s + "#";
                }
                String servString = "";
                if (attribute.getChildrenList() != null) {
                    for (CategoryChild c : attribute.getChildrenList()) {
                        servString += c.getId() + "!" + c.getTitle() + "#";
                    }
                }
                String orgString = attribute.getOwner().getName();
                Intent intent = new Intent(SearchActivity.this, AttributeActivity.class);
                intent.putExtra("documents", listString);
                intent.putExtra("orgs", orgString);
                intent.putExtra("services", servString);
                SearchActivity.this.startActivity(intent);
            } else {
                Toast.makeText(SearchActivity.this, R.string.error_services, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class BasicCategory extends StatelessSection {

        ArrayList<Attribute> list = new ArrayList<>();
        String title;

        BasicCategory(ArrayList<Attribute> list, String title) {
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
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;

            // bind your view here
            itemHolder.tvItem.setText(list.get(position).getName());
            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Attribute selectedAttr = list.get(position);
                    String listString = "";
                    for (String s : selectedAttr.getDocsList()) {
                        listString += s + "#";
                    }
                    String servString = "";
                    if (selectedAttr.getChildrenList() != null) {
                        for (CategoryChild c : selectedAttr.getChildrenList()) {
                            servString += c.getId() + "!" + c.getTitle() + "#";
                        }
                    }
                    String orgString = selectedAttr.getOwner().getName();
                    Intent intent = new Intent(SearchActivity.this, AttributeActivity.class);
                    intent.putExtra("documents", listString);
                    intent.putExtra("orgs", orgString);
                    intent.putExtra("services", servString);
                    SearchActivity.this.startActivity(intent);

                }
            });
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

        HeaderViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.header_section);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvItem;
        private final View rootView;

        ItemViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            tvItem = (TextView) itemView.findViewById(R.id.header_item);
        }
    }
}
