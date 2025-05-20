package com.fireboy.booka.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fireboy.booka.R;
import com.fireboy.booka.model.Business;

import java.util.List;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.ViewHolder> {
    private List<Business> dataset;
    private Context context;

    public BusinessAdapter(List<Business> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public BusinessAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.business_card, parent, false);
        return new BusinessAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(dataset.get(position).getImg())
                .centerCrop()
                .into(holder.imgBusiness);
        holder.lblBusinessName.setText(dataset.get(position).getName());
        holder.lblBusinessAddress.setText(dataset.get(position).getAddress());
        holder.lblRating.setText(String.valueOf(dataset.get(position).getRating()));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBusiness;
        TextView lblBusinessName, lblBusinessAddress, lblRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBusiness = itemView.findViewById(R.id.imgBusiness);
            lblBusinessName = itemView.findViewById(R.id.lblBusinessName);
            lblBusinessAddress = itemView.findViewById(R.id.lblBusinessAddress);
            lblRating = itemView.findViewById(R.id.lblRating);
        }
    }
}
