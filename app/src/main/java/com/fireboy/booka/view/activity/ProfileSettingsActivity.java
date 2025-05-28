package com.fireboy.booka.view.activity;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.AuthController;
import com.fireboy.booka.controller.UserController;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileSettingsActivity extends AppCompatActivity {
    TextInputEditText txtUsername, txtEmail;

    AuthController authController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        initComponents();

        txtUsername.setText(authController.getCurrentUser().getDisplayName());
        txtEmail.setText(authController.getCurrentUser().getEmail());

        txtUsername.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                UserController userController = new UserController(this);

                userController.updateUsernameInFirestore(txtUsername.getText().toString());
                return true;
            }
            return false;
        });
    }

    private void initComponents() {
        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail2);

        authController = new AuthController(this);
    }
}