package com.github.bagiasn.code4fun.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.bagiasn.code4fun.R;
import com.github.bagiasn.code4fun.activities.SearchActivity;
import com.github.bagiasn.code4fun.activities.VoiceSearchActivity;
import com.github.bagiasn.code4fun.models.app.Option;

import java.util.List;

/**
 * Created by kostas.bagias on 13/05/2017.
 */

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.DocViewHolder> {

    private List<String> documentsList;
    private Context context;

    public DocumentsAdapter(List<String> list, Context context) {
        this.context = context;
        this.documentsList = list;
    }

    class DocViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name;

        DocViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.option_title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public DocViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View optionView = inflater.inflate(R.layout.doc_item, parent, false);
        return new DocumentsAdapter.DocViewHolder(optionView);
    }

    @Override
    public void onBindViewHolder(DocViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
