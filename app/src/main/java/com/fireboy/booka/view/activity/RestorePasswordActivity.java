package com.fireboy.booka.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.AuthController;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Actividad que permite al usuario recuperar su contraseña.
 * Envía un correo de recuperación a la dirección proporcionada.
 */
public class RestorePasswordActivity extends AppCompatActivity {

    private TextInputEditText txtEmail;
    private MaterialButton btnRestorePassword;
    private AuthController authController;

    /**
     * Método llamado al crear la actividad.
     * Inicializa los componentes y configura el listener del botón.
     *
     * @param savedInstanceState Estado previo de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);

        initComponents();
        setupRestoreAction();
    }

    /**
     * Inicializa los elementos visuales y el controlador de autenticación.
     */
    private void initComponents() {
        txtEmail = findViewById(R.id.txtRestorePasswordEmailContent);
        btnRestorePassword = findViewById(R.id.btnRestorePassword);
        authController = new AuthController(this);
    }

    /**
     * Configura el botón para enviar el email de recuperación de contraseña.
     */
    private void setupRestoreAction() {
        btnRestorePassword.setOnClickListener(v -> {
            if (txtEmail.getText() != null) {
                String email = txtEmail.getText().toString().trim();
                authController.recoverPassword(email);
            }
        });
    }
}
