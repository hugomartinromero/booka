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
import com.fireboy.booka.model.Business;
import com.fireboy.booka.model.Category;
import com.fireboy.booka.utils.HorizontalSpacingDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para mostrar una lista de categorías con un RecyclerView anidado de negocios por cada categoría.
 * Permite aplicar un filtro de búsqueda por nombre de negocio.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<Category> originalDataset;
    private final List<Category> visibleCategories = new ArrayList<>();
    private final Activity activity;
    private final Context context;
    private final BusinessController businessController;
    private String currentQuery = "";

    /**
     * Constructor del adaptador.
     *
     * @param dataset  Lista completa de categorías.
     * @param activity Actividad asociada al adaptador.
     */
    public CategoryAdapter(List<Category> dataset, Activity activity) {
        this.originalDataset = dataset;
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.businessController = new BusinessController();
        this.visibleCategories.addAll(dataset); // Mostrar todas al inicio
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = visibleCategories.get(position);
        holder.lblCategory.setText(category.getName());

        setupNestedRecyclerView(holder.rvBusiness, category, holder.itemView);
    }

    @Override
    public int getItemCount() {
        return visibleCategories.size();
    }

    /**
     * Filtra las categorías y negocios visibles en función de un texto ingresado.
     * Solo se muestran categorías que tienen al menos un negocio coincidente.
     *
     * @param query Texto de búsqueda ingresado por el usuario.
     */
    public void filter(String query) {
        currentQuery = query.toLowerCase().trim();
        visibleCategories.clear();
        notifyDataSetChanged(); // Limpia la vista antes de iniciar nueva búsqueda

        if (query.isEmpty()) {
            // Mostrar solo las categorías con negocios
            final int[] remaining = {originalDataset.size()};
            for (Category category : originalDataset) {
                businessController.getBusinessByCategory(category.getName(), businesses -> {
                    if (!businesses.isEmpty()) {
                        visibleCategories.add(category);
                    }
                    remaining[0]--;
                    if (remaining[0] == 0) {
                        notifyDataSetChanged();
                    }
                });
            }
            return;
        }

        final int[] remaining = {originalDataset.size()};
        for (Category category : originalDataset) {
            businessController.getBusinessByCategory(category.getName(), businesses -> {
                boolean hasMatch = false;
                for (Business b : businesses) {
                    if (b.getName().toLowerCase().contains(currentQuery)) {
                        hasMatch = true;
                        break;
                    }
                }

                if (hasMatch) {
                    visibleCategories.add(category);
                }

                remaining[0]--;
                if (remaining[0] == 0) {
                    notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * Configura el RecyclerView anidado para una categoría específica, incluyendo filtrado y visibilidad.
     *
     * @param rv         RecyclerView horizontal dentro del ítem.
     * @param category   Categoría asociada.
     * @param itemView   Vista del ítem para ocultarla si no hay negocios coincidentes.
     */
    private void setupNestedRecyclerView(RecyclerView rv, Category category, View itemView) {
        if (rv.getItemDecorationCount() == 0) {
            int spacing = (int) (context.getResources().getDisplayMetrics().density * 30); // 30dp
            rv.addItemDecoration(new HorizontalSpacingDecoration(spacing));
        }

        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        businessController.getBusinessByCategory(category.getName(), businesses -> {
            List<Business> filtered = new ArrayList<>();
            for (Business b : businesses) {
                if (b.getName().toLowerCase().contains(currentQuery)) {
                    filtered.add(b);
                }
            }

            rv.setAdapter(new BusinessAdapter(filtered, category.getName(), activity));
            itemView.setVisibility(filtered.isEmpty() ? View.GONE : View.VISIBLE);
        });
    }

    /**
     * ViewHolder para cada ítem de categoría.
     * Contiene el nombre de la categoría y un RecyclerView horizontal para los negocios.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblCategory;
        RecyclerView rvBusiness;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblCategory = itemView.findViewById(R.id.lblMain);
            rvBusiness = itemView.findViewById(R.id.rvNested);
        }
    }
}
