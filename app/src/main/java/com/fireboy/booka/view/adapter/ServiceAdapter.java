package com.fireboy.booka.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fireboy.booka.R;
import com.fireboy.booka.model.Service;
import com.fireboy.booka.utils.FormatUtils;

import java.util.List;


public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    List<Service> dataset;
    Context context;

    public ServiceAdapter(List<Service> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.service_card, parent, false);
        return new ServiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ViewHolder holder, int position) {
        Service service = dataset.get(position);

        holder.lblName.setText(service.getName());
        holder.lblDuration.setText(String.format("%s min", service.getDuration()));
        holder.lblPrice.setText(String.format("%s €", FormatUtils.formatDouble(service.getPrice())));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblName, lblDuration, lblPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblName = itemView.findViewById(R.id.lblServiceName);
            lblDuration = itemView.findViewById(R.id.lblServiceDuration);
            lblPrice = itemView.findViewById(R.id.lblServicePrice);
        }
    }
}
