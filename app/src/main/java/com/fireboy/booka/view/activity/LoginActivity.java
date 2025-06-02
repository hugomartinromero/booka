package com.fireboy.booka.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.AuthController;
import com.fireboy.booka.controller.UserController;
import com.fireboy.booka.view.UiExtensions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

/**
 * Actividad de inicio de sesión.
 * Permite iniciar sesión con correo y contraseña o mediante Google.
 */
public class LoginActivity extends AppCompatActivity {

    private TextInputEditText txtEmail, txtPassword;
    private TextView lblForgottenPassword, lblSignUp;
    private MaterialButton btnGoogle, btnLogIn;

    private AuthController authController;
    private UserController userController;
    private GoogleSignInClient googleSignInClient;

    private static final int RC_SIGN_IN = 9001;

    /**
     * Método que se llama al iniciar la actividad. Configura los componentes y los listeners.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UiExtensions.changeStatusBarColor(this, R.color.booka_background);
        initComponents();
        initControllers();
        setupListeners();
        setupGoogleSignIn();
    }

    /**
     * Inicializa las vistas del layout.
     */
    private void initComponents() {
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        lblForgottenPassword = findViewById(R.id.lblForgottenPassword);
        lblSignUp = findViewById(R.id.lblSignUp);
        btnGoogle = findViewById(R.id.btnGoogle);
        btnLogIn = findViewById(R.id.btnLogIn);
    }

    /**
     * Inicializa los controladores de autenticación y usuario.
     */
    private void initControllers() {
        authController = new AuthController(this);
        userController = new UserController(this);
    }

    /**
     * Configura los listeners para los botones y enlaces.
     */
    private void setupListeners() {
        lblForgottenPassword.setOnClickListener(v ->
                UiExtensions.navigateTo(this, RestorePasswordActivity.class, false));

        lblSignUp.setOnClickListener(v ->
                UiExtensions.navigateTo(this, SignUpActivity.class, false));

        btnGoogle.setOnClickListener(v -> launchGoogleSignIn());

        btnLogIn.setOnClickListener(v -> {
            String email = Objects.requireNonNull(txtEmail.getText()).toString().trim();
            String password = Objects.requireNonNull(txtPassword.getText()).toString().trim();
            authController.signInWithEmail(email, password);
        });
    }

    /**
     * Configura el cliente de inicio de sesión con Google.
     */
    private void setupGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    /**
     * Lanza el intent para iniciar sesión con Google.
     */
    private void launchGoogleSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Maneja el resultado del intent de inicio de sesión con Google.
     *
     * @param requestCode Código de solicitud.
     * @param resultCode  Código de resultado.
     * @param data        Intent de resultado.
     */
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
}
