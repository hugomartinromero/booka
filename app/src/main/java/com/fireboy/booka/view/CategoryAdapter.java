package com.fireboy.booka.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fireboy.booka.R;
import com.fireboy.booka.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> dataset;
    private Context context;

    public CategoryAdapter(List<Category> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.category_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        if (dataset.get(position).isActive()) {
            holder.lblCategory.setText(dataset.get(position).getName());
            holder.rvBusiness.setLayoutManager(new LinearLayoutManager(context));
            holder.rvBusiness.setAdapter(new BusinessAdapter(List.of()));
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
            lblCategory = itemView.findViewById(R.id.lblCategory);
            rvBusiness = itemView.findViewById(R.id.rvBusiness);
        }
    }
}
