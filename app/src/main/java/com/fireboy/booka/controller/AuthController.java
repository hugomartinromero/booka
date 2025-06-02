package com.fireboy.booka.controller;

import android.app.Activity;
import android.widget.Toast;

import com.fireboy.booka.view.UiExtensions;
import com.fireboy.booka.view.activity.MainActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Controlador encargado de gestionar las operaciones de autenticación del usuario
 * utilizando Firebase Authentication (correo/contraseña o Google).
 */
public class AuthController {
    private final FirebaseAuth mAuth;
    private final Activity activity;

    /**
     * Constructor del controlador de autenticación.
     *
     * @param activity Actividad desde la cual se inicializa el controlador.
     */
    public AuthController(Activity activity) {
        this.activity = activity;
        this.mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Inicia sesión con correo electrónico y contraseña.
     *
     * @param email    Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     */
    public void signInWithEmail(String email, String password) {
        if (isFieldEmpty(email, password)) return;

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> navigateToMain())
                .addOnFailureListener(e -> showError("Error al iniciar sesión.", e));
    }

    /**
     * Inicia sesión con credenciales de Google.
     *
     * @param idToken        Token de autenticación de Google.
     * @param userController Controlador para guardar el usuario en Firestore si es nuevo.
     */
    public void signInWithGoogle(String idToken, UserController userController) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = authResult.getUser();
                    if (user != null) userController.saveUserToFirestore(user, activity);
                    navigateToMain();
                })
                .addOnFailureListener(e -> showError("Error al iniciar sesión con Google.", e));
    }

    /**
     * Registra un nuevo usuario con correo electrónico, contraseña y nombre.
     *
     * @param name           Nombre del usuario.
     * @param email          Correo electrónico del usuario.
     * @param password       Contraseña del usuario.
     * @param userController Controlador para guardar el usuario en Firestore.
     */
    public void registerWithEmail(String name, String email, String password, UserController userController) {
        if (isFieldEmpty(email, password)) return;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) userController.saveNewUserToFirestore(user, name, activity);
                })
                .addOnFailureListener(e -> showError("Error al registrarse.", e));
    }

    /**
     * Envía un correo de recuperación de contraseña al usuario.
     *
     * @param email Correo electrónico del usuario.
     */
    public void recoverPassword(String email) {
        if (email == null || email.isEmpty()) {
            Toast.makeText(activity, "Por favor, introduce un correo válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(unused -> Toast.makeText(activity, "Correo de recuperación enviado.", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> showError("Error al enviar el correo.", e));
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    public void signOut() {
        mAuth.signOut();
    }

    /**
     * Obtiene el usuario actualmente autenticado.
     *
     * @return Usuario autenticado o null si no hay sesión iniciada.
     */
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    /**
     * Verifica si alguno de los campos está vacío y muestra un Toast si es así.
     *
     * @param email    Correo electrónico.
     * @param password Contraseña.
     * @return true si algún campo está vacío, false en caso contrario.
     */
    private boolean isFieldEmpty(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(activity, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    /**
     * Muestra un mensaje de error personalizado mediante un Toast.
     *
     * @param prefix Mensaje de contexto.
     * @param e      Excepción capturada.
     */
    private void showError(String prefix, Exception e) {
        Toast.makeText(activity, prefix + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * Redirige a la pantalla principal de la app.
     */
    private void navigateToMain() {
        UiExtensions.navigateTo(activity, MainActivity.class, true);
    }
}
