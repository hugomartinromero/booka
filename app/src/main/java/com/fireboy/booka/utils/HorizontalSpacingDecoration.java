package com.fireboy.booka.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Decoración personalizada para aplicar espaciado horizontal entre ítems de un RecyclerView.
 *
 * Agrega espacio a la izquierda y derecha de cada ítem, con un margen adicional
 * al primer y último ítem de la lista.
 */
public class HorizontalSpacingDecoration extends RecyclerView.ItemDecoration {

    private final int space;

    /**
     * Constructor que define el valor del espaciado horizontal.
     *
     * @param space Valor en píxeles del espacio horizontal entre ítems.
     */
    public HorizontalSpacingDecoration(int space) {
        this.space = space;
    }

    /**
     * Calcula los offsets horizontales para cada ítem del RecyclerView.
     *
     * @param outRect Rectángulo donde se aplican los márgenes.
     * @param view    Vista del ítem.
     * @param parent  RecyclerView contenedor.
     * @param state   Estado actual del RecyclerView.
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter() != null ? parent.getAdapter().getItemCount() : 0;

        outRect.left = space / 2;
        outRect.right = space / 2;

        // Margen extra al primer y último ítem
        if (position == 0) {
            outRect.left = space;
        }
        if (position == itemCount - 1) {
            outRect.right = space;
        }
    }
}
