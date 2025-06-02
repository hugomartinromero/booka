package com.fireboy.booka.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fireboy.booka.R;
import com.fireboy.booka.model.Business;
import com.fireboy.booka.view.activity.InfoActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

/**
 * Adaptador para mostrar una lista de negocios en un RecyclerView.
 * Solo muestra los negocios que pertenecen a una categoría específica.
 */
public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.ViewHolder> {

    private final List<Business> dataset;
    private final String categoryFilter;
    private final Activity activity;
    private Context context;

    /**
     * Constructor del adaptador.
     *
     * @param dataset   Lista de negocios a mostrar.
     * @param category  Categoría por la cual se filtrarán los negocios.
     * @param activity  Actividad desde donde se lanza el adaptador.
     */
    public BusinessAdapter(List<Business> dataset, String category, Activity activity) {
        this.dataset = dataset;
        this.categoryFilter = category;
        this.activity = activity;
    }

    /**
     * Crea la vista de cada ítem del RecyclerView.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.business_card, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Asocia los datos del negocio a la vista solo si coincide con la categoría.
     *
     * @param holder   ViewHolder que contiene las vistas.
     * @param position Posición del ítem en la lista.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Business business = dataset.get(position);

        if (!categoryFilter.equals(business.getCategory())) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            return;
        }

        Glide.with(context)
                .load(business.getImg())
                .centerCrop()
                .into(holder.imgBusiness);

        holder.lblBusinessName.setText(business.getName());
        holder.lblBusinessAddress.setText(business.getAddress());
        holder.lblRating.setText(String.valueOf(business.getRating()));

        holder.bgBusinessCard.setOnClickListener(v -> {
            Intent intent = new Intent(activity, InfoActivity.class);
            intent.putExtra("businessId", business.getId());
            context.startActivity(intent);
        });
    }

    /**
     * Retorna el tamaño total de la lista.
     */
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    /**
     * ViewHolder que contiene las vistas necesarias para cada ítem del RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView bgBusinessCard;
        ImageView imgBusiness;
        TextView lblBusinessName, lblBusinessAddress, lblRating;

        /**
         * Constructor del ViewHolder.
         *
         * @param itemView Vista del ítem.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bgBusinessCard = itemView.findViewById(R.id.bgBusinessCard);
            imgBusiness = itemView.findViewById(R.id.imgBusiness);
            lblBusinessName = itemView.findViewById(R.id.lblBusinessName);
            lblBusinessAddress = itemView.findViewById(R.id.lblBusinessAddress);
            lblRating = itemView.findViewById(R.id.lblRating);
        }
    }
}
