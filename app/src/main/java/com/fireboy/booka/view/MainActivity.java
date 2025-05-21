package com.fireboy.booka.view;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.CategoryController;
import com.fireboy.booka.utils.VerticalSpacingDecoration;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvCategory;

    CategoryController categoryController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UiExtensions.changeStatusBarColor(this, R.color.booka_primary);
        initComponents();

//        if (Si no hay iniciada sesión, te lleva a logearte (Con SharedPreferences)) {
//            UiExtensions.navigateTo(this, LoginActivity.class, true);
//        }

        categoryController.getActiveCategories(categorias -> {
            int spacing = (int) (this.getResources().getDisplayMetrics().density * 30); // 30dp

            if (rvCategory.getItemDecorationCount() == 0) {
                rvCategory.addItemDecoration(new VerticalSpacingDecoration(spacing));
            }

            rvCategory.setLayoutManager(new LinearLayoutManager(this));
            rvCategory.setAdapter(new CategoryAdapter(categorias));
        });
        
        findViewById(R.id.nav_home).setOnClickListener(v -> {
            setActiveOption(R.id.nav_home);
        });

        findViewById(R.id.nav_bookmarks).setOnClickListener(v -> {
            setActiveOption(R.id.nav_bookmarks);
        });

        findViewById(R.id.nav_profile).setOnClickListener(v -> {
            setActiveOption(R.id.nav_profile);
        });
    }

    private void initComponents() {
        rvCategory = findViewById(R.id.rvCategory);
        categoryController = new CategoryController();

        findViewById(R.id.nav_home).setBackgroundResource(R.drawable.bg_nav_selected);
        findViewById(R.id.nav_home).setSelected(true);
    }

    private void setActiveOption(int idActivo) {
        FrameLayout[] options = {
                findViewById(R.id.nav_home),
                findViewById(R.id.nav_bookmarks),
                findViewById(R.id.nav_profile)
        };

        for (FrameLayout option : options) {
            option.setBackground(null);
            option.setSelected(false);
            if (option.getId() == idActivo) {
                option.setBackgroundResource(R.drawable.bg_nav_selected);
                option.setSelected(true);
            }
        }
    }
}