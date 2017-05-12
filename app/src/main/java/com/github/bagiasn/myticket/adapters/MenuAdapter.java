package com.github.bagiasn.myticket.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.bagiasn.myticket.R;
import com.github.bagiasn.myticket.models.app.Option;

import java.util.List;

/**
 * Adapter for populating the main menu.
 */

public class MenuAdapter extends Adapter<MenuAdapter.MenuViewHolder>{
    private List<Option> menuList;

    public MenuAdapter(List<Option> list) {
        this.menuList = list;
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
        public void onClick(View v) {}
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
