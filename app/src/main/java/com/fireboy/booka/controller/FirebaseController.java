package com.fireboy.booka.controller;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.fireboy.booka.view.MainActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class FirebaseController {
    private final FirebaseAuth mAuth;
    private final Activity activity;

    public FirebaseController(Activity activity) {
        this.activity = activity;
        this.mAuth = FirebaseAuth.getInstance();
    }

    public void iniciarSesionEmail(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(activity, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    redirigirAMain();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(activity, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    // Iniciar sesión con Google (token recibido desde GoogleSignInAccount.getIdToken())
    public void iniciarSesionConGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    redirigirAMain();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(activity, "Error al iniciar sesión con Google.", Toast.LENGTH_LONG).show();
                });
    }

    // Obtener el usuario actual
    public FirebaseUser getUsuarioActual() {
        return mAuth.getCurrentUser();
    }

    // Cerrar sesión
    public void cerrarSesion() {
        mAuth.signOut();
    }

    // Redirige a MainActivity
    private void redirigirAMain() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}

