package com.fireboy.booka.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.AuthController;
import com.fireboy.booka.utils.SettingsUtils;
import com.fireboy.booka.view.UiExtensions;

public class SettingsActivity extends AppCompatActivity {
    View[] items;
    SwitchCompat swDarkMode;
    TextView lblLogout;

    AuthController authController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        UiExtensions.changeStatusBarColor(this, R.color.booka_primary);

        initComponents();

        authController = new AuthController(this);

        items[0].setOnClickListener(v -> UiExtensions.navigateTo(this, ProfileSettingsActivity.class, false));

        swDarkMode.setChecked(SettingsUtils.isDarkModeEnabled(this));
        swDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SettingsUtils.setDarkMode(this, isChecked);
            recreate();
        });

        lblLogout.setOnClickListener(v -> {
            authController.signOut();
            SettingsUtils.clearSettings(this);

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void initComponents() {
        items = new View[]{
                findViewById(R.id.itemAccount),
                findViewById(R.id.itemLanguage),
                findViewById(R.id.itemPassword),
                findViewById(R.id.itemGoogle),
                findViewById(R.id.itemDarkMode)
        };

        String[] itemsTitle = new String[]{
                "Detalles de la cuenta",
                "Idioma",
                "Cambiar contraseña",
                "Conectar con Google",
                "Modo oscuro"
        };

        swDarkMode = items[4].findViewById(R.id.switchItem);
        lblLogout = findViewById(R.id.lblLogout);
        lblLogout = findViewById(R.id.lblLogout);

        for (int i = 0; i < items.length; i++) {
            TextView lblTitle = items[i].findViewById(R.id.lblTitle);
            lblTitle.setText(itemsTitle[i]);
        }
    }
}