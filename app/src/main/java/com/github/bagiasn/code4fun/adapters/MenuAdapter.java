package com.github.bagiasn.code4fun.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
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
 * Adapter for populating the main menu.
 */

public class MenuAdapter extends Adapter<MenuAdapter.MenuViewHolder>{
    private List<Option> menuList;
    private Context context;

    public MenuAdapter(List<Option> list, Context context) {
        this.menuList = list;
        this.context = context;
    }
    class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView icon;
        private TextView title;
        private TextView subTitle;
        MenuViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.option_icon);
            title = (TextView) itemView.findViewById(R.id.option_title);
            subTitle = (TextView) itemView.findViewById(R.id.option_subtitle);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Option selectedOpt = menuList.get(getAdapterPosition());
            Intent intent;
            switch (selectedOpt.title) {
                case "Ψάξε το":
                    intent = new Intent(context, SearchActivity.class);
                    context.startActivity(intent);
                    break;
                case "Πες το":
                    intent = new Intent(context, VoiceSearchActivity.class);
                    context.startActivity(intent);
                    break;
                case "Αποθηκευμένες αναζητήσεις":
                    intent = new Intent(context, SearchActivity.class);
                    context.startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View optionView = inflater.inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(optionView);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        Option option = menuList.get(position);

        holder.title.setText(option.title);
        holder.subTitle.setText(option.subTitle);
        holder.icon.setImageResource(option.iconId);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}
