package com.fireboy.booka.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpacingDecoration extends RecyclerView.ItemDecoration {
    private final int spacing;

    public VerticalSpacingDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter() != null ? parent.getAdapter().getItemCount() : 0;

        outRect.top = spacing / 2;
        outRect.bottom = spacing / 2;

        if (position == 0) {
            outRect.top = spacing;
        }
        if (position == itemCount - 1) {
            outRect.bottom = spacing;
        }
    }
}
