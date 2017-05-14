package com.github.bagiasn.code4fun.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.bagiasn.code4fun.R;
import com.github.bagiasn.code4fun.activities.AttributeActivity;
import com.github.bagiasn.code4fun.helpers.HttpHelper;
import com.github.bagiasn.code4fun.helpers.JsonHelper;
import com.github.bagiasn.code4fun.models.database.Attribute;
import com.github.bagiasn.code4fun.models.database.CategoryChild;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Adapter for populating attributes into the search list.
 */

public class CategoryChildAdapter extends RecyclerView.Adapter<CategoryChildAdapter.AttributeViewHolder> {
    private List<CategoryChild> children;
    private Context context;

    public CategoryChildAdapter(List<CategoryChild> list, Context context) {
        this.context = context;
        this.children = list;
    }

    class AttributeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;

        AttributeViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.attribute_title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            CategoryChild selectedChild = children.get(getAdapterPosition());
            saveSearch(selectedChild.getId(), selectedChild.getTitle());
            new GetAttributeById().execute(selectedChild.getId());
        }
    }

    @Override
    public AttributeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View optionView = inflater.inflate(R.layout.attribute_item, parent, false);
        return new AttributeViewHolder(optionView);
    }

    @Override
    public void onBindViewHolder(AttributeViewHolder holder, int position) {
        CategoryChild categoryChild = children.get(position);

        holder.title.setText(categoryChild.getTitle());

    }

    @Override
    public int getItemCount() {
        return children.size();
    }

    private class GetAttributeById extends AsyncTask<String,Void, Boolean> {
        private Attribute attribute;
        @Override
        protected Boolean doInBackground(String... params) {
            String url = "http://46.101.196.53:54870/getServiceById?serviceId=" + params[0];
            String jsonResponse = HttpHelper.makeJsonRequest(url);
            if (jsonResponse != null) {
                attribute = JsonHelper.getAttribute(jsonResponse, true);
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
                Intent intent = new Intent(context, AttributeActivity.class);
                intent.putExtra("documents", listString);
                intent.putExtra("orgs", orgString);
                intent.putExtra("services", servString);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, R.string.error_services, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveSearch(String id, String title) {
        SharedPreferences  prefs = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        String history = prefs.getString("SavedSearches", "");
        // Avoid duplicates.
        if (history.contains(id)) return;

        history+= id + "!" + title + "#";

        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("SavedSearches", history);
        prefsEditor.apply();
    }
}
