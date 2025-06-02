package com.fireboy.booka.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Decoración personalizada para aplicar espaciado vertical entre ítems de un RecyclerView.
 *
 * Agrega espacio arriba y abajo de cada ítem, con un margen adicional en el primero y último elemento.
 */
public class VerticalSpacingDecoration extends RecyclerView.ItemDecoration {

    private final int spacing;

    /**
     * Constructor que define el valor del espaciado vertical.
     *
     * @param spacing Espaciado vertical en píxeles.
     */
    public VerticalSpacingDecoration(int spacing) {
        this.spacing = spacing;
    }

    /**
     * Aplica márgenes verticales entre ítems del RecyclerView.
     *
     * @param outRect Rectángulo donde se establecen los márgenes del ítem.
     * @param view    Vista actual del ítem.
     * @param parent  RecyclerView contenedor.
     * @param state   Estado del RecyclerView.
     */
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
