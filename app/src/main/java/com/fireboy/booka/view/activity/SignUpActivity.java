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

public class SignUpActivity extends AppCompatActivity {
    private TextInputEditText txtName, txtEmail, txtPassword, txtConfirmPassword;
    private MaterialButton btnSignUp;

    private AuthController authController;
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        UiExtensions.changeStatusBarColor(this, R.color.booka_background);

        initComponents();

        authController = new AuthController(this);
        userController = new UserController(this);

        btnSignUp.setOnClickListener(v -> registerUser());
    }

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

    private void initComponents() {
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
    }
}
