package com.fireboy.booka.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.CategoryController;
import com.fireboy.booka.utils.BottomPaddingDecoration;
import com.fireboy.booka.utils.VerticalSpacingDecoration;

public class HomeFragment extends Fragment {
    RecyclerView rvCategory;
    View progressLoader;
    View homeContent;

    CategoryController categoryController;

    public HomeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponents(view);
        initRecyclerView();

        categoryController.getActiveCategories(categorias -> {
            rvCategory.setAdapter(new CategoryAdapter(categorias, requireActivity()));
            progressLoader.setVisibility(View.GONE);
            homeContent.setVisibility(View.VISIBLE);
        });
    }

    private void initComponents(View view) {
        rvCategory = view.findViewById(R.id.rvCategory);
        progressLoader = view.findViewById(R.id.progressLoader);
        homeContent = view.findViewById(R.id.homeContent);

        categoryController = new CategoryController();
    }

    private void initRecyclerView() {
        int spacing = (int) (this.getResources().getDisplayMetrics().density * 30); // 30dp
        int extraBottom = (int) (getResources().getDisplayMetrics().density * 60);  // 60dp

        if (rvCategory.getItemDecorationCount() == 0) {
            rvCategory.addItemDecoration(new VerticalSpacingDecoration(spacing));
            rvCategory.addItemDecoration(new BottomPaddingDecoration(extraBottom));
        }

        rvCategory.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

}
