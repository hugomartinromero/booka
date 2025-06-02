package com.fireboy.booka.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fireboy.booka.R;
import com.fireboy.booka.model.Reservation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Adaptador que agrupa las reservas por fecha y muestra un RecyclerView anidado
 * con las reservas correspondientes a cada día.
 */
public class ReservationDateAdapter extends RecyclerView.Adapter<ReservationDateAdapter.ViewHolder> {

    private final Map<String, List<Reservation>> groupedReservations;
    private final List<String> dates;
    private final Activity activity;

    /**
     * Constructor del adaptador.
     *
     * @param groupedReservations Mapa de reservas agrupadas por fecha.
     * @param activity            Actividad asociada (para navegación).
     */
    public ReservationDateAdapter(Map<String, List<Reservation>> groupedReservations, Activity activity) {
        this.groupedReservations = groupedReservations;
        this.dates = new ArrayList<>(groupedReservations.keySet());
        this.activity = activity;
    }

    /**
     * Infla la vista para cada grupo de reservas (una fecha).
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Asocia la fecha y su lista de reservas ordenadas por hora.
     *
     * @param holder   ViewHolder con las vistas.
     * @param position Índice del grupo de fecha.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String date = dates.get(position);
        holder.lblMain.setText(date);

        List<Reservation> reservationsForDate = groupedReservations.get(date);
        if (reservationsForDate == null || reservationsForDate.isEmpty()) return;

        reservationsForDate.sort(Comparator.comparing(Reservation::getTime));

        holder.rvNested.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.rvNested.setAdapter(new ReservationAdapter(reservationsForDate, activity));
    }

    /**
     * @return Número de fechas distintas (grupos).
     */
    @Override
    public int getItemCount() {
        return dates.size();
    }

    /**
     * ViewHolder que contiene la fecha y el RecyclerView anidado de reservas.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblMain;
        RecyclerView rvNested;

        /**
         * Constructor que enlaza vistas del ítem.
         *
         * @param itemView Vista inflada del ítem.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblMain = itemView.findViewById(R.id.lblMain);
            rvNested = itemView.findViewById(R.id.rvNested);
        }
    }
}
