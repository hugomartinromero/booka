package com.fireboy.booka.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.AuthController;
import com.fireboy.booka.controller.UserController;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText txtEmail, txtPassword;
    private TextView lblForgottenPassword, lblSignUp;
    private MaterialButton btnGoogle, btnLogIn;

    private AuthController authController;
    private UserController userController;
    private GoogleSignInClient mGoogleSignInClient;

    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UiExtensions.changeStatusBarColor(this, R.color.booka_background);
        initComponents();

        // Inicializar controladores
        authController = new AuthController(this);
        userController = new UserController();

        // Navegación entre pantallas
        lblForgottenPassword.setOnClickListener(v -> UiExtensions.navigateTo(this, RestorePasswordActivity.class));
        lblSignUp.setOnClickListener(v -> UiExtensions.navigateTo(this, SignUpActivity.class));

        // Configurar Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Acciones de botones
        btnGoogle.setOnClickListener(v -> launchGoogleSignIn());
        btnLogIn.setOnClickListener(v -> authController.signInWithEmail(Objects.requireNonNull(txtEmail.getText()).toString().trim(), Objects.requireNonNull(txtPassword.getText()).toString().trim()));
    }

    // Lanzar login de Google
    private void launchGoogleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // Resultado del intent de login con Google
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null && account.getIdToken() != null) {
                    authController.signInWithGoogle(account.getIdToken(), userController);
                }
            } catch (ApiException e) {
                Toast.makeText(this, "Error al iniciar sesión con Google", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initComponents() {
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        lblForgottenPassword = findViewById(R.id.lblForgottenPassword);
        lblSignUp = findViewById(R.id.lblSignUp);
        btnGoogle = findViewById(R.id.btnGoogle);
        btnLogIn = findViewById(R.id.btnLogIn);
    }
}
