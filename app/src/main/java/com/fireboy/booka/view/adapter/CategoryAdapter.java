package com.fireboy.booka.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.BusinessController;
import com.fireboy.booka.model.Category;
import com.fireboy.booka.utils.HorizontalSpacingDecoration;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> dataset;
    private Context context;
    private final Activity ACTIVITY;

    public CategoryAdapter(List<Category> dataset, Activity activity) {
        this.dataset = dataset;
        this.ACTIVITY = activity;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        BusinessController businessController = new BusinessController();
        Category category = dataset.get(position);

        if (category.isActive()) {
            holder.lblCategory.setText(category.getName());
            int spacing = (int) (context.getResources().getDisplayMetrics().density * 30); // 30dp

            holder.rvBusiness.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

            if (holder.rvBusiness.getItemDecorationCount() == 0) {
                holder.rvBusiness.addItemDecoration(new HorizontalSpacingDecoration(spacing));
            }

            businessController.getBusinessByCategory(category.getName(), negocios -> {
                holder.rvBusiness.setAdapter(new BusinessAdapter(negocios, category.getName(), ACTIVITY));
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblCategory;
        RecyclerView rvBusiness;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblCategory = itemView.findViewById(R.id.lblMain);
            rvBusiness = itemView.findViewById(R.id.rvNested);
        }
    }
}
