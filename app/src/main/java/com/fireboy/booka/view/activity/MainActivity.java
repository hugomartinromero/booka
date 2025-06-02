package com.fireboy.booka.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
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
 * Gestiona la navegación entre fragmentos (Inicio, Reservas, Perfil),
 * verifica la sesión de usuario y aplica el tema según configuración.
 */
public class MainActivity extends AppCompatActivity {

    private EditText txtBuscar;
    private FrameLayout[] bottomMenu;
    private ImageView imgSettings;
    private AuthController authController;

    /**
     * Método de ciclo de vida llamado al crear la actividad.
     * Aplica el tema, valida sesión, inicializa componentes y listeners.
     *
     * @param savedInstanceState Estado de instancia anterior si existe.
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
     * Aplica el modo oscuro o claro según la configuración de usuario.
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
     * Inicializa los elementos visuales y carga el fragmento inicial.
     */
    private void initComponents() {
        txtBuscar = findViewById(R.id.txtBuscar);
        imgSettings = findViewById(R.id.settingsIcon);

        bottomMenu = new FrameLayout[]{
                findViewById(R.id.nav_home),
                findViewById(R.id.nav_bookmarks),
                findViewById(R.id.nav_profile)
        };

        // Cargar fragmento inicial (Inicio)
        loadFragment(new HomeFragment(), bottomMenu[0].getId());
    }

    /**
     * Asocia los listeners de los botones de navegación y de búsqueda.
     */
    private void setupListeners() {
        // Navegación inferior
        bottomMenu[0].setOnClickListener(v -> loadFragment(new HomeFragment(), R.id.nav_home));
        bottomMenu[1].setOnClickListener(v -> loadFragment(new MyBookingFragment(), R.id.nav_bookmarks));
        bottomMenu[2].setOnClickListener(v -> loadFragment(new ProfileFragment(), R.id.nav_profile));

        // Botón ajustes
        imgSettings.setOnClickListener(v ->
                UiExtensions.navigateTo(this, SettingsActivity.class, false)
        );

        // Búsqueda dinámica
        txtBuscar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (fragment instanceof HomeFragment) {
                    ((HomeFragment) fragment).filterBusinesses(s.toString());
                }
            }
        });
    }

    /**
     * Reemplaza el fragmento actual por el indicado y marca la opción seleccionada.
     *
     * @param fragment Fragmento a cargar.
     * @param idMenu   ID del menú activo.
     */
    private void loadFragment(Fragment fragment, int idMenu) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment).commit();
        setActiveOption(idMenu);
    }

    /**
     * Marca visualmente el botón de navegación activo.
     *
     * @param idActivo ID del botón activo.
     */
    private void setActiveOption(int idActivo) {
        for (FrameLayout option : bottomMenu) {
            option.setSelected(option.getId() == idActivo);
        }
    }
}
