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
 * Fragmento principal que muestra las categorías disponibles junto con los negocios
 * correspondientes en listas anidadas. Permite también el filtrado por nombre.
 */
public class HomeFragment extends Fragment {

    private RecyclerView rvCategory;
    private CategoryAdapter adapter;
    private View progressLoader;
    private View homeContent;
    private CategoryController categoryController;

    /**
     * Constructor vacío requerido para instanciación del fragmento.
     */
    public HomeFragment() {
        // Constructor por defecto
    }

    /**
     * Infla el layout asociado al fragmento.
     *
     * @param inflater           El objeto LayoutInflater.
     * @param container          El contenedor padre del fragmento.
     * @param savedInstanceState Estado guardado del fragmento.
     * @return La vista inflada del fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    /**
     * Método invocado cuando la vista ha sido creada.
     * Inicializa vistas, configuración del RecyclerView y carga de datos.
     *
     * @param view               Vista raíz del fragmento.
     * @param savedInstanceState Estado guardado del fragmento.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        setupRecyclerView();
        loadCategories();
    }

    /**
     * Inicializa las vistas del fragmento y el controlador de categorías.
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
     * Configura el RecyclerView con separación vertical y relleno inferior.
     */
    private void setupRecyclerView() {
        int spacing = (int) (getResources().getDisplayMetrics().density * 30); // 30dp
        int bottomPadding = (int) (getResources().getDisplayMetrics().density * 60); // 60dp

        if (rvCategory.getItemDecorationCount() == 0) {
            rvCategory.addItemDecoration(new VerticalSpacingDecoration(spacing));
            rvCategory.addItemDecoration(new BottomPaddingDecoration(bottomPadding));
        }

        rvCategory.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    /**
     * Obtiene las categorías desde Firebase y actualiza el adaptador.
     * Solo se muestran categorías con negocios relacionados.
     */
    private void loadCategories() {
        categoryController.getActiveCategories(categories -> {
            adapter = new CategoryAdapter(categories, requireActivity());
            rvCategory.setAdapter(adapter);
            progressLoader.setVisibility(View.GONE);
            homeContent.setVisibility(View.VISIBLE);
        });
    }

    /**
     * Filtra los negocios mostrados en el adaptador según el texto de búsqueda.
     *
     * @param query Texto ingresado por el usuario para buscar.
     */
    public void filterBusinesses(String query) {
        if (adapter != null) {
            adapter.filter(query);
        }
    }
}
