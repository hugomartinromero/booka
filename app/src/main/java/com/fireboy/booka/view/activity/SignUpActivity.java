package com.fireboy.booka.view.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.AuthController;
import com.fireboy.booka.controller.UserController;
import com.fireboy.booka.view.UiExtensions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

/**
 * Actividad de registro de nuevos usuarios.
 * Permite al usuario crear una cuenta mediante nombre, correo y contraseña.
 */
public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText txtName, txtEmail, txtPassword, txtConfirmPassword;
    private MaterialButton btnSignUp;

    private AuthController authController;
    private UserController userController;

    /**
     * Método principal al crear la actividad.
     * Configura los controladores y el listener para el registro.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        UiExtensions.changeStatusBarColor(this, R.color.booka_background);

        initComponents();
        initControllers();
        setupListeners();
    }

    /**
     * Inicializa los campos de entrada y botón del formulario.
     */
    private void initComponents() {
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    /**
     * Inicializa los controladores de autenticación y usuario.
     */
    private void initControllers() {
        authController = new AuthController(this);
        userController = new UserController(this);
    }

    /**
     * Configura el listener para el botón de registro.
     */
    private void setupListeners() {
        btnSignUp.setOnClickListener(v -> registerUser());
    }

    /**
     * Valida los campos del formulario y ejecuta el registro si todo es correcto.
     */
    private void registerUser() {
        String name = Objects.requireNonNull(txtName.getText()).toString().trim();
        String email = Objects.requireNonNull(txtEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(txtPassword.getText()).toString().trim();
        String confirmPassword = Objects.requireNonNull(txtConfirmPassword.getText()).toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            return;
        }

        authController.registerWithEmail(name, email, password, userController);
    }
}
