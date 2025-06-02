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

/**
 * Actividad que permite al usuario gestionar su configuración:
 * cuenta, idioma, tema oscuro y cerrar sesión.
 */
public class SettingsActivity extends AppCompatActivity {

    private View[] items;
    private SwitchCompat swDarkMode;
    private TextView lblLogout;

    private AuthController authController;

    /**
     * Método principal que se ejecuta al crear la actividad.
     * Configura los ítems de ajustes, el switch de modo oscuro y el cierre de sesión.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        UiExtensions.changeStatusBarColor(this, R.color.booka_primary);

        initComponents();
        initControllers();
        setupListeners();
    }

    /**
     * Inicializa los controladores necesarios.
     */
    private void initControllers() {
        authController = new AuthController(this);
    }

    /**
     * Inicializa los componentes de la vista y asigna títulos a los ítems del menú.
     */
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

        for (int i = 0; i < items.length; i++) {
            TextView lblTitle = items[i].findViewById(R.id.lblTitle);
            lblTitle.setText(itemsTitle[i]);
        }
    }

    /**
     * Configura los listeners para los ítems interactivos del menú de ajustes.
     */
    private void setupListeners() {
        // Navegar a detalles de cuenta
        items[0].setOnClickListener(v ->
                UiExtensions.navigateTo(this, ProfileSettingsActivity.class, false));

        // Switch de modo oscuro
        swDarkMode.setChecked(SettingsUtils.isDarkModeEnabled(this));
        swDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SettingsUtils.setDarkMode(this, isChecked);
            recreate();
        });

        // Cerrar sesión
        lblLogout.setOnClickListener(v -> {
            authController.signOut();
            SettingsUtils.clearSettings(this);

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
