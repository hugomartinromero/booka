package com.fireboy.booka.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.BusinessController;
import com.fireboy.booka.model.Reservation;
import com.fireboy.booka.utils.FormatUtils;
import com.fireboy.booka.view.activity.ReviewActivity;

import java.util.List;

/**
 * Adaptador para mostrar una lista de reservas del usuario.
 * Cada ítem muestra la información del negocio y permite dejar una reseña.
 */
public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private final List<Reservation> dataset;
    private final Activity activity;
    private Context context;

    /**
     * Constructor del adaptador.
     *
     * @param dataset  Lista de reservas a mostrar.
     * @param activity Actividad desde donde se lanza el adaptador.
     */
    public ReservationAdapter(List<Reservation> dataset, Activity activity) {
        this.dataset = dataset;
        this.activity = activity;
    }

    /**
     * Infla el layout de cada ítem de reserva.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.reservation_card, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Asocia los datos de la reserva al layout de la tarjeta.
     *
     * @param holder   ViewHolder con las vistas.
     * @param position Posición de la reserva.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservation reservation = dataset.get(position);
        BusinessController businessController = new BusinessController();

        businessController.getBusinessById(reservation.getBusinessId(), business -> {
            if (business == null) return;

            holder.lblBusinessName.setText(business.getName());
            holder.lblTime.setText(reservation.getTime());
            holder.lblServiceName.setText(reservation.getService());
            holder.lblPrice.setText(String.format("%s €", FormatUtils.formatDouble(reservation.getPrice())));

            holder.bgReviewCard.setOnClickListener(v -> {
                Intent intent = new Intent(activity, ReviewActivity.class);
                intent.putExtra("businessId", business.getId());
                context.startActivity(intent);
            });
        });
    }

    /**
     * Devuelve el número total de ítems en la lista.
     */
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    /**
     * ViewHolder que contiene las vistas de una tarjeta de reserva.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblBusinessName, lblTime, lblServiceName, lblPrice;
        ConstraintLayout bgReviewCard;

        /**
         * Constructor que vincula las vistas del layout.
         *
         * @param itemView Vista inflada del ítem.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblBusinessName = itemView.findViewById(R.id.lblReservationBusiness);
            lblTime = itemView.findViewById(R.id.lblTime);
            lblServiceName = itemView.findViewById(R.id.lblReservationServiceName);
            lblPrice = itemView.findViewById(R.id.lblPrice);
            bgReviewCard = itemView.findViewById(R.id.bgReviewCard);
        }
    }
}
