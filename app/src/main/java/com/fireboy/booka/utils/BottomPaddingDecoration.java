package com.fireboy.booka.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BottomPaddingDecoration extends RecyclerView.ItemDecoration {
    private final int bottomPadding;

    public BottomPaddingDecoration(int bottomPadding) {
        this.bottomPadding = bottomPadding;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter() != null ? parent.getAdapter().getItemCount() : 0;

        if (position == itemCount - 1) {
            outRect.bottom = bottomPadding;
        }
    }
}
