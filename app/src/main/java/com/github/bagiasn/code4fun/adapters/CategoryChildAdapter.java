package com.github.bagiasn.code4fun.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.bagiasn.code4fun.R;
import com.github.bagiasn.code4fun.models.database.Attribute;
import com.github.bagiasn.code4fun.models.database.CategoryChild;
import com.github.bagiasn.code4fun.models.database.Organization;

import java.util.List;

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
        public void onClick(View v) {}
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
}
