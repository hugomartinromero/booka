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

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private List<Service> dataset;
    private Context context;
    private boolean isBookingView;
    private int selectedPosition = -1;

    public ServiceAdapter(List<Service> dataset, Context context, boolean isBookingView) {
        this.dataset = dataset;
        this.context = context;
        this.isBookingView = isBookingView;
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

        if (isBookingView) {
            holder.bgService.setSelected(position == selectedPosition);

            holder.bgService.setOnClickListener(v -> {
                if (selectedPosition == holder.getAdapterPosition()) {
                    selectedPosition = -1;
                    notifyItemChanged(holder.getAdapterPosition());
                } else {
                    int oldPosition = selectedPosition;
                    selectedPosition = holder.getAdapterPosition();
                    notifyItemChanged(oldPosition);
                    notifyItemChanged(selectedPosition);
                }
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

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout bgService;
        TextView lblName, lblDuration, lblPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bgService = itemView.findViewById(R.id.bgService);
            lblName = itemView.findViewById(R.id.lblServiceName);
            lblDuration = itemView.findViewById(R.id.lblServiceDuration);
            lblPrice = itemView.findViewById(R.id.lblServicePrice);
        }
    }

    public Service getSelectedService() {
        if (selectedPosition >= 0 && selectedPosition < dataset.size()) {
            return dataset.get(selectedPosition);
        }
        return null;
    }
}
