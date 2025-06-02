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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ReservationDateAdapter extends RecyclerView.Adapter<ReservationDateAdapter.ViewHolder> {
    private final Map<String, List<Reservation>> groupedReservations;
    private final List<String> dates;
    private final Activity ACTIVITY;

    public ReservationDateAdapter(Map<String, List<Reservation>> groupedReservations, Activity activity) {
        this.groupedReservations = groupedReservations;
        this.dates = new ArrayList<>(groupedReservations.keySet());
        this.ACTIVITY = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String date = dates.get(position);
        holder.lblMain.setText(date);

        List<Reservation> reservationsForDate = groupedReservations.get(date);
        Collections.sort(reservationsForDate, Comparator.comparing(Reservation::getTime));
        holder.rvNested.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.rvNested.setAdapter(new ReservationAdapter(reservationsForDate, ACTIVITY));
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblMain;
        RecyclerView rvNested;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblMain = itemView.findViewById(R.id.lblMain);
            rvNested = itemView.findViewById(R.id.rvNested);
        }
    }
}
