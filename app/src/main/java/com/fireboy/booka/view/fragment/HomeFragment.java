package com.fireboy.booka.view.fragment;

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
import com.fireboy.booka.view.adapter.CategoryAdapter;

/**
 * Fragmento de inicio que muestra las categorías activas y sus negocios.
 */
public class HomeFragment extends Fragment {

    private RecyclerView rvCategory;
    private View progressLoader;
    private View homeContent;
    private CategoryController categoryController;

    /**
     * Constructor vacío requerido para instanciar el fragmento.
     */
    public HomeFragment() {}

    /**
     * Infla el layout XML del fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    /**
     * Se llama después de que la vista ha sido creada.
     * Inicializa componentes y carga las categorías activas.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        initRecyclerView();
        loadCategories();
    }

    /**
     * Inicializa los componentes de la interfaz y el controlador de categorías.
     *
     * @param view Vista raíz inflada del fragmento.
     */
    private void initComponents(View view) {
        rvCategory = view.findViewById(R.id.rvCategory);
        progressLoader = view.findViewById(R.id.progressLoader);
        homeContent = view.findViewById(R.id.homeContent);
        categoryController = new CategoryController();
    }

    /**
     * Configura el RecyclerView de categorías con decoraciones y layout.
     */
    private void initRecyclerView() {
        int spacing = (int) (getResources().getDisplayMetrics().density * 30); // 30dp
        int extraBottom = (int) (getResources().getDisplayMetrics().density * 60); // 60dp

        if (rvCategory.getItemDecorationCount() == 0) {
            rvCategory.addItemDecoration(new VerticalSpacingDecoration(spacing));
            rvCategory.addItemDecoration(new BottomPaddingDecoration(extraBottom));
        }

        rvCategory.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    /**
     * Carga las categorías activas desde Firebase y actualiza el adaptador.
     */
    private void loadCategories() {
        categoryController.getActiveCategories(categories -> {
            rvCategory.setAdapter(new CategoryAdapter(categories, requireActivity()));
            progressLoader.setVisibility(View.GONE);
            homeContent.setVisibility(View.VISIBLE);
        });
    }
}
