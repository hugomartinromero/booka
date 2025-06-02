package com.fireboy.booka.view.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fireboy.booka.R;
import com.fireboy.booka.model.Service;
import com.fireboy.booka.utils.FormatUtils;

import java.util.List;

/**
 * Adaptador que muestra una lista de servicios. En modo booking, permite seleccionar un servicio.
 */
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private final List<Service> dataset;
    private final Context context;
    private final boolean isBookingView;
    private int selectedPosition = -1;

    /**
     * Constructor del adaptador.
     *
     * @param dataset        Lista de servicios a mostrar.
     * @param context        Contexto de la actividad o fragmento.
     * @param isBookingView  Indica si está en modo selección de servicio (reserva).
     */
    public ServiceAdapter(List<Service> dataset, Context context, boolean isBookingView) {
        this.dataset = dataset;
        this.context = context;
        this.isBookingView = isBookingView;
    }

    /**
     * Infla la vista de la tarjeta de servicio.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.service_card, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Asocia los datos del servicio a la vista y gestiona la lógica de selección si aplica.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = dataset.get(position);

        holder.lblName.setText(service.getName());
        holder.lblDuration.setText(String.format("%d min", service.getDuration()));
        holder.lblPrice.setText(String.format("%s €", FormatUtils.formatDouble(service.getPrice())));

        if (isBookingView) {
            holder.bgService.setSelected(position == selectedPosition);

            holder.bgService.setOnClickListener(v -> {
                int previous = selectedPosition;
                selectedPosition = (selectedPosition == holder.getAdapterPosition()) ? -1 : holder.getAdapterPosition();
                notifyItemChanged(previous);
                notifyItemChanged(selectedPosition);
            });

            if (position == selectedPosition) {
                GradientDrawable background = new GradientDrawable();
                background.setColor(ContextCompat.getColor(context, R.color.booka_input_stroke));
                background.setCornerRadius(75);
                holder.bgService.setBackground(background);
            } else {
                holder.bgService.setBackground(null);
            }
        }
    }

    /**
     * @return Número de ítems (servicios) en la lista.
     */
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    /**
     * Devuelve el servicio seleccionado si hay uno, o {@code null} si no hay selección.
     *
     * @return Servicio seleccionado o null.
     */
    public Service getSelectedService() {
        if (selectedPosition >= 0 && selectedPosition < dataset.size()) {
            return dataset.get(selectedPosition);
        }
        return null;
    }

    /**
     * ViewHolder que representa cada tarjeta de servicio.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout bgService;
        TextView lblName, lblDuration, lblPrice;

        /**
         * Constructor que enlaza las vistas de la tarjeta.
         *
         * @param itemView Vista inflada del ítem.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bgService = itemView.findViewById(R.id.bgService);
            lblName = itemView.findViewById(R.id.lblServiceName);
            lblDuration = itemView.findViewById(R.id.lblServiceDuration);
            lblPrice = itemView.findViewById(R.id.lblServicePrice);
        }
    }
}
