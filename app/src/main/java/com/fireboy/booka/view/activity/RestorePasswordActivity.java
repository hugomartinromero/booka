package com.fireboy.booka.view.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.AuthController;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RestorePasswordActivity extends AppCompatActivity {
    private TextInputEditText txtEmail;
    private MaterialButton btnRestorePassword;

    AuthController ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);

        initComponents();

        btnRestorePassword.setOnClickListener(v -> {
            String email = txtEmail.getText().toString().trim();
            ac.recoverPassword(email);
        });
    }

    private void initComponents() {
        txtEmail = findViewById(R.id.txtRestorePasswordEmailContent);
        btnRestorePassword = findViewById(R.id.btnRestorePassword);

        ac = new AuthController(this);
    }
}