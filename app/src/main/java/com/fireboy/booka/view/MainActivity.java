package com.fireboy.booka.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fireboy.booka.R;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            UiExtensions.changeStatusBarColor(this, R.color.booka_primary);

            initComponents();

    }

    private void initComponents() {
        rvCategory = findViewById(R.id.rvCategory);
    }
}