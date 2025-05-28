package com.fireboy.booka.controller;

import android.app.Activity;
import android.widget.Toast;

import com.fireboy.booka.view.activity.MainActivity;
import com.fireboy.booka.view.UiExtensions;
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
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(activity, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult ->
                        UiExtensions.navigateTo(activity, MainActivity.class , true))
                .addOnFailureListener(e ->
                        Toast.makeText(activity, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    public void signInWithGoogle(String idToken, UserController userController) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = authResult.getUser();
                    if (user != null) {
                        userController.saveUserToFirestore(user, activity);
                    }
                    UiExtensions.navigateTo(activity, MainActivity.class , true);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(activity, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    public void registerWithEmail(String name, String email, String password, UserController userController) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        userController.saveNewUserToFirestore(user, name, activity);
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(activity, "Error al registrarse: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    public void signOut() {
        mAuth.signOut();
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }
}
