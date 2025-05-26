package com.fireboy.booka.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.BusinessController;
import com.fireboy.booka.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<Review> dataset;
    private Context context;
    private final Activity activity;

    public ReviewAdapter(List<Review> dataset, Activity activity) {
        this.dataset = dataset;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.review_card, parent, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        BusinessController businessController = new BusinessController();
        Review review = dataset.get(position);

        businessController.getBusinessById(review.getBusinessId(),
                business -> {
                    holder.lblUser.setText(business.getName());
                });
        
        holder.lblDate.setText(UiExtensions.formatFirebaseTimestamp(review.getTimestamp()));
        holder.lblRating.setText(String.valueOf(review.getRating()));
        holder.lblMessage.setText(review.getComment());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblUser, lblDate, lblRating, lblMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblUser = itemView.findViewById(R.id.lblUser);
            lblDate = itemView.findViewById(R.id.lblDate);
            lblRating = itemView.findViewById(R.id.lblRating);
            lblMessage = itemView.findViewById(R.id.lblMessage);
        }
    }
}
