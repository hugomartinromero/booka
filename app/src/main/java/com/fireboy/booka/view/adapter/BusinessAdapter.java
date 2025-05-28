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

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.ViewHolder> {
    private List<Business> dataset;
    private final String CATEGORY;
    private final Activity ACTIVITY;
    private Context context;

    public BusinessAdapter(List<Business> dataset, String category, Activity activity) {
        this.dataset = dataset;
        this.CATEGORY = category;
        this.ACTIVITY = activity;
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
        Business business = dataset.get(position);

        if (CATEGORY.equals(business.getCategory())) {
            Glide.with(context)
                    .load(business.getImg())
                    .centerCrop()
                    .into(holder.imgBusiness);
            holder.lblBusinessName.setText(business.getName());
            holder.lblBusinessAddress.setText(business.getAddress());
            holder.lblRating.setText(String.valueOf(business.getRating()));

            holder.bgBusinessCard.setOnClickListener(v -> {
                Intent intent = new Intent(ACTIVITY, InfoActivity.class);
                intent.putExtra("businessId", business.getId());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView bgBusinessCard;
        ImageView imgBusiness;
        TextView lblBusinessName, lblBusinessAddress, lblRating;

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
