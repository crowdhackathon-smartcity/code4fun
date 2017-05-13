package com.github.bagiasn.code4fun.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.bagiasn.code4fun.R;
import com.github.bagiasn.code4fun.models.database.Attribute;
import com.github.bagiasn.code4fun.models.database.Organization;

import java.util.List;

/**
 * Adapter for populating attributes into the search list.
 */

public class AttributeAdapter extends RecyclerView.Adapter<AttributeAdapter.AttributeViewHolder> {
    private List<Attribute> attributes;
    private Context context;

    public AttributeAdapter(List<Attribute> list, Context context) {
        this.context = context;
        this.attributes = list;
    }

    class AttributeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView subTitle;

        AttributeViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.attribute_title);
            subTitle = (TextView) itemView.findViewById(R.id.attribute_subtitle);

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
        Attribute attribute = attributes.get(position);
        Organization org = attribute.getOwner();

        holder.title.setText(attribute.getName());
        holder.subTitle.setText(org.getName());
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }
}
