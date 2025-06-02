package com.fireboy.booka.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.AuthController;
import com.fireboy.booka.utils.SettingsUtils;
import com.fireboy.booka.view.UiExtensions;
import com.fireboy.booka.view.fragment.HomeFragment;
import com.fireboy.booka.view.fragment.MyBookingFragment;
import com.fireboy.booka.view.fragment.ProfileFragment;

/**
 * Actividad principal de la aplicación Booka.
 * Gestiona la navegación entre fragmentos (inicio, reservas, perfil) y la verificación de sesión.
 */
public class MainActivity extends AppCompatActivity {

    private FrameLayout[] bottomMenu;
    private ImageView imgSettings;

    private AuthController authController;

    /**
     * Método principal llamado al crear la actividad.
     * Verifica la sesión, configura el tema, inicializa vistas y carga el fragmento inicial.
     *
     * @param savedInstanceState Estado guardado de la instancia.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applyAppTheme();

        super.onCreate(savedInstanceState);

        authController = new AuthController(this);
        if (authController.getCurrentUser() == null) {
            redirectToLogin();
            return;
        }

        setContentView(R.layout.activity_main);
        UiExtensions.changeStatusBarColor(this, R.color.booka_primary);

        initComponents();
        setupListeners();
    }

    /**
     * Aplica el tema claro u oscuro según la configuración guardada.
     */
    private void applyAppTheme() {
        if (SettingsUtils.isDarkModeEnabled(this)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    /**
     * Redirige al usuario al login si no hay sesión activa.
     */
    private void redirectToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    /**
     * Inicializa las vistas y carga el fragmento inicial.
     */
    private void initComponents() {
        bottomMenu = new FrameLayout[]{
                findViewById(R.id.nav_home),
                findViewById(R.id.nav_bookmarks),
                findViewById(R.id.nav_profile)
        };

        imgSettings = findViewById(R.id.settingsIcon);

        // Fragmento por defecto
        loadFragment(new HomeFragment(), bottomMenu[0].getId());
    }

    /**
     * Asocia listeners a los ítems del menú inferior y al botón de ajustes.
     */
    private void setupListeners() {
        bottomMenu[0].setOnClickListener(v -> loadFragment(new HomeFragment(), R.id.nav_home));
        bottomMenu[1].setOnClickListener(v -> loadFragment(new MyBookingFragment(), R.id.nav_bookmarks));
        bottomMenu[2].setOnClickListener(v -> loadFragment(new ProfileFragment(), R.id.nav_profile));

        imgSettings.setOnClickListener(v ->
                UiExtensions.navigateTo(this, SettingsActivity.class, false));
    }

    /**
     * Carga un fragmento dentro del contenedor principal y actualiza el estado del menú inferior.
     *
     * @param fragment Fragmento a cargar.
     * @param idMenu   ID del ítem de menú que será marcado como activo.
     */
    private void loadFragment(Fragment fragment, int idMenu) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment).commit();

        setActiveOption(idMenu);
    }

    /**
     * Marca como seleccionado el ítem activo del menú inferior.
     *
     * @param idActivo ID del ítem activo.
     */
    private void setActiveOption(int idActivo) {
        for (FrameLayout option : bottomMenu) {
            option.setSelected(option.getId() == idActivo);
        }
    }
}
