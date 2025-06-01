package com.fireboy.booka.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.BusinessController;
import com.fireboy.booka.model.Reservation;
import com.fireboy.booka.utils.FormatUtils;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {
    private List<Reservation> dataset;
    private Context context;

    public ReservationAdapter(List<Reservation> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public ReservationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.reservation_card, parent, false);
        return new ReservationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.ViewHolder holder, int position) {
        Reservation r = dataset.get(position);
        BusinessController bc = new BusinessController();

        bc.getBusinessById(r.getBusinessId(), business -> {
            holder.lblBusinessName.setText(business.getName());
            holder.lblTime.setText(r.getTime());
            holder.lblServiceName.setText(r.getService());
            holder.lblPrice.setText(String.format("%s €", FormatUtils.formatDouble(r.getPrice())));
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblBusinessName, lblTime, lblServiceName, lblPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblBusinessName = itemView.findViewById(R.id.lblReservationBusiness);
            lblTime = itemView.findViewById(R.id.lblTime);
            lblServiceName = itemView.findViewById(R.id.lblReservationServiceName);
            lblPrice = itemView.findViewById(R.id.lblPrice);
        }
    }
}
