package com.fireboy.booka.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.CategoryController;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvCategory;

    CategoryController categoryController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UiExtensions.changeStatusBarColor(this, R.color.booka_primary);

        initComponents();

        categoryController.getActiveCategories(categorias -> {
            rvCategory.setLayoutManager(new LinearLayoutManager(this));
            rvCategory.setAdapter(new CategoryAdapter(categorias));
        });
    }

    private void initComponents() {
        rvCategory = findViewById(R.id.rvCategory);
        categoryController = new CategoryController();
    }
}