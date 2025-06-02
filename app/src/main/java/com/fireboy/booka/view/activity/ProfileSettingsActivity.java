package com.fireboy.booka.view.activity;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.AuthController;
import com.fireboy.booka.controller.UserController;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Actividad que permite al usuario modificar su nombre de usuario.
 * El correo solo se muestra como dato informativo.
 */
public class ProfileSettingsActivity extends AppCompatActivity {

    private TextInputEditText txtUsername, txtEmail;
    private AuthController authController;

    /**
     * Método que se ejecuta al crear la actividad.
     * Carga el nombre de usuario y correo actual desde FirebaseAuth y configura el listener para editar.
     *
     * @param savedInstanceState Estado anterior de la actividad (si existe).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        initComponents();
        loadUserData();
        setupUsernameUpdateListener();
    }

    /**
     * Inicializa los componentes de la vista.
     */
    private void initComponents() {
        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail2);
        authController = new AuthController(this);
    }

    /**
     * Carga y muestra los datos del usuario actual (username y correo).
     */
    private void loadUserData() {
        if (authController.getCurrentUser() != null) {
            txtUsername.setText(authController.getCurrentUser().getDisplayName());
            txtEmail.setText(authController.getCurrentUser().getEmail());
        }
    }

    /**
     * Configura el listener para actualizar el nombre de usuario al presionar "Done" en el teclado.
     */
    private void setupUsernameUpdateListener() {
        txtUsername.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                final String newUsername = txtUsername.getText() != null ? txtUsername.getText().toString().trim() : "";

                if (!newUsername.isEmpty()) {
                    UserController userController = new UserController(this);
                    userController.updateUsernameInFirestore(newUsername);
                }

                return true;
            }
            return false;
        });
    }
}
