package com.fireboy.booka.view;

import android.content.Context;
import android.graphics.Rect;
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
        BusinessController businessController = new BusinessController();
        Category category = dataset.get(position);

        if (category.isActive()) {
            holder.lblCategory.setText(category.getName());
            businessController.getAllBusinesses(negocios -> {
                int spacing = (int) (context.getResources().getDisplayMetrics().density * 30); // 30dp

                holder.rvBusiness.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                holder.rvBusiness.addItemDecoration(new HorizontalSpacingDecoration(spacing));
                holder.rvBusiness.setAdapter(new BusinessAdapter(negocios, category.getName()));
            });
        }

        // holder.rvBusiness.setOnClickListener(v -> UiExtensions.navigateTo(context, InfoActivity.class, true));
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

    public class HorizontalSpacingDecoration extends RecyclerView.ItemDecoration {
        private final int space;

        public HorizontalSpacingDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int itemCount = parent.getAdapter() != null ? parent.getAdapter().getItemCount() : 0;

            outRect.left = space / 2;
            outRect.right = space / 2;

            if (position == 0) {
                outRect.left = space;
            }

            if (position == itemCount - 1) {
                outRect.right = space;
            }
        }
    }

}
