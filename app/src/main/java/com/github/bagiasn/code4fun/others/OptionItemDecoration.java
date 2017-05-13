package com.github.bagiasn.code4fun.others;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 *  Class for setting spacing between the menu items.
 */

public class OptionItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public OptionItemDecoration(int space) {
        this.space = space;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add space only for the first child, to avoid double spacing
        if(parent.getChildAdapterPosition(view) == 0)
            outRect.top = space;
    }
}
