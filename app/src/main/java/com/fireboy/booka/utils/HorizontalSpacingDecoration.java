package com.fireboy.booka.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalSpacingDecoration extends RecyclerView.ItemDecoration {
    private final int space;

    public HorizontalSpacingDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter() != null ? parent.getAdapter().getItemCount() : 0;

        outRect.left = space / 2;
        outRect.right = space / 2;

        if (position == 0) {
            outRect.left = space;
        }
        if (position == itemCount - 1) {
            outRect.right = space;
        }
    }
}
