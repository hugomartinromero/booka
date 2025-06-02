package com.fireboy.booka.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Decoración personalizada para añadir un padding inferior únicamente
 * al último elemento de un RecyclerView.
 *
 * Útil para que el último ítem no quede oculto por elementos como la barra de navegación
 * o un BottomNavigationView.
 */
public class BottomPaddingDecoration extends RecyclerView.ItemDecoration {
    private final int bottomPadding;

    /**
     * Constructor que recibe el valor del padding inferior a aplicar.
     *
     * @param bottomPadding Valor en píxeles del espacio inferior.
     */
    public BottomPaddingDecoration(int bottomPadding) {
        this.bottomPadding = bottomPadding;
    }

    /**
     * Aplica el padding inferior solo al último ítem del RecyclerView.
     *
     * @param outRect Rectángulo donde se especifican los offsets.
     * @param view    Vista actual del ítem.
     * @param parent  RecyclerView contenedor.
     * @param state   Estado del RecyclerView.
     */
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
