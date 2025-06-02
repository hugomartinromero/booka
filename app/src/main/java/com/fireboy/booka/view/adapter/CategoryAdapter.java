package com.fireboy.booka.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.BusinessController;
import com.fireboy.booka.model.Category;
import com.fireboy.booka.utils.HorizontalSpacingDecoration;

import java.util.List;

/**
 * Adaptador para mostrar una lista de categorías activas, cada una con un RecyclerView anidado
 * de negocios relacionados a esa categoría.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<Category> dataset;
    private final Activity activity;
    private Context context;

    /**
     * Constructor del adaptador.
     *
     * @param dataset  Lista de categorías a mostrar.
     * @param activity Actividad desde donde se lanza el adaptador.
     */
    public CategoryAdapter(List<Category> dataset, Activity activity) {
        this.dataset = dataset;
        this.activity = activity;
    }

    /**
     * Infla la vista para cada ítem de categoría.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_card, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Asocia cada categoría activa con su lista horizontal de negocios.
     *
     * @param holder   ViewHolder que contiene las vistas.
     * @param position Posición del ítem.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = dataset.get(position);

        if (!category.isActive()) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            return;
        }

        holder.lblCategory.setText(category.getName());

        setupHorizontalRecycler(holder.rvBusiness, category.getName());
    }

    /**
     * Configura el RecyclerView horizontal con negocios según la categoría.
     *
     * @param rv       RecyclerView interno.
     * @param category Nombre de la categoría.
     */
    private void setupHorizontalRecycler(RecyclerView rv, String category) {
        BusinessController businessController = new BusinessController();

        if (rv.getItemDecorationCount() == 0) {
            int spacing = (int) (context.getResources().getDisplayMetrics().density * 30); // 30dp
            rv.addItemDecoration(new HorizontalSpacingDecoration(spacing));
        }

        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        businessController.getBusinessByCategory(category, businesses -> {
            rv.setAdapter(new BusinessAdapter(businesses, category, activity));
        });
    }

    /**
     * @return Cantidad total de ítems (categorías).
     */
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    /**
     * ViewHolder para cada categoría, contiene un título y un RecyclerView anidado.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblCategory;
        RecyclerView rvBusiness;

        /**
         * Constructor que vincula las vistas del layout.
         *
         * @param itemView Vista del ítem.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblCategory = itemView.findViewById(R.id.lblMain);
            rvBusiness = itemView.findViewById(R.id.rvNested);
        }
    }
}
