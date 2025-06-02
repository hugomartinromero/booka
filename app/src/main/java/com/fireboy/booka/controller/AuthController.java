package com.fireboy.booka.controller;

import android.app.Activity;
import android.widget.Toast;

import com.fireboy.booka.view.UiExtensions;
import com.fireboy.booka.view.activity.MainActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthController {
    private final FirebaseAuth mAuth;
    private final Activity activity;

    public AuthController(Activity activity) {
        this.activity = activity;
        this.mAuth = FirebaseAuth.getInstance();
    }

    public void signInWithEmail(String email, String password) {
        if (isFieldEmpty(email, password)) return;

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> navigateToMain())
                .addOnFailureListener(e -> showError("Error al iniciar sesión.", e));
    }

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

    public void registerWithEmail(String name, String email, String password, UserController userController) {
        if (isFieldEmpty(email, password)) return;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) userController.saveNewUserToFirestore(user, name, activity);
                })
                .addOnFailureListener(e -> showError("Error al registrarse.", e));
    }

    public void recoverPassword(String email) {
        if (email == null || email.isEmpty()) {
            Toast.makeText(activity, "Por favor, introduce un correo válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(unused -> Toast.makeText(activity, "Correo de recuperación enviado.", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> showError("Error al enviar el correo.", e));
    }

    public void signOut() {
        mAuth.signOut();
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    private boolean isFieldEmpty(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(activity, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void showError(String prefix, Exception e) {
        Toast.makeText(activity, prefix + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void navigateToMain() {
        UiExtensions.navigateTo(activity, MainActivity.class, true);
    }
}
