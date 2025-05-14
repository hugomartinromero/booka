package com.fireboy.booka.controller;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.fireboy.booka.view.MainActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class FirebaseController {
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;
    private final Activity activity;

    public FirebaseController(Activity activity) {
        this.mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
        this.activity = activity;
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
                    FirebaseUser user = authResult.getUser();
                    if (user != null) {
                        guardarUsuarioFirestore(user); // 👈 aquí se guarda el usuario
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(activity, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void guardarUsuarioFirestore(FirebaseUser user) {
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("email", user.getEmail());
        usuario.put("username", user.getDisplayName());
        usuario.put("rol", "usuario");
        usuario.put("foto", user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "");

        // Crea o actualiza el documento en Firestore con UID del usuario
        db.collection("users").document(user.getUid())
                .set(usuario, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(activity, "Bienvenido, " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                    redirigirAMain();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(activity, "Error al crear usuario: " + e.getMessage(), Toast.LENGTH_LONG).show();
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

