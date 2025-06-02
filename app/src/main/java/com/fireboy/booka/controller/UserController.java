package com.fireboy.booka.controller;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fireboy.booka.model.User;
import com.fireboy.booka.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.function.Consumer;

public class UserController {

    private final FirebaseFirestore db;
    private final Activity activity;

    public UserController(Activity activity) {
        this.activity = activity;
        this.db = FirebaseFirestore.getInstance();
    }

    public void saveUserToFirestore(FirebaseUser firebaseUser, Activity activity) {
        if (firebaseUser == null) {
            showToast("Error: Usuario no válido.");
            return;
        }

        String uid = firebaseUser.getUid();
        DocumentReference docRef = db.collection(Constants.USERS_COLLECTION).document(uid);

        docRef.get().addOnSuccessListener(document -> {
            if (!document.exists()) {
                User user = new User(
                        firebaseUser.getEmail(),
                        firebaseUser.getDisplayName() != null ? firebaseUser.getDisplayName() : "Usuario",
                        "user",
                        firebaseUser.getPhotoUrl() != null ? firebaseUser.getPhotoUrl().toString() : ""
                );

                docRef.set(user)
                        .addOnSuccessListener(unused -> showToast("Usuario registrado con Google."))
                        .addOnFailureListener(e -> showToast("Error al guardar usuario: " + e.getMessage()));
            }
        });
    }

    public void saveNewUserToFirestore(FirebaseUser firebaseUser, String username, Activity activity) {
        if (firebaseUser == null || username == null || username.trim().isEmpty()) {
            showToast("Datos de usuario inválidos.");
            return;
        }

        String uid = firebaseUser.getUid();
        User user = new User(
                firebaseUser.getEmail(),
                username.trim(),
                "user",
                Constants.DEFAULT_PROFILE_PIC
        );

        db.collection(Constants.USERS_COLLECTION).document(uid)
                .set(user)
                .addOnSuccessListener(unused -> showToast("Usuario registrado correctamente"))
                .addOnFailureListener(e -> showToast("Error al guardar usuario: " + e.getMessage()));
    }

    public void getUserById(@NonNull String userId, @NonNull Consumer<User> onSuccess) {
        if (userId.trim().isEmpty()) {
            showToast("ID de usuario no válido.");
            return;
        }

        db.collection(Constants.USERS_COLLECTION).document(userId)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        User user = snapshot.toObject(User.class);
                        if (user != null) {
                            onSuccess.accept(user);
                        } else {
                            showToast("Error al interpretar el usuario.");
                        }
                    } else {
                        showToast("El usuario no existe.");
                    }
                })
                .addOnFailureListener(e -> showToast("Error al obtener el usuario: " + e.getMessage()));
    }

    public void updateUsernameInFirestore(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            showToast("El nombre no puede estar vacío.");
            return;
        }

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection(Constants.USERS_COLLECTION)
                .document(uid)
                .update("username", newName.trim())
                .addOnSuccessListener(aVoid -> showToast("Nombre actualizado."))
                .addOnFailureListener(e -> showToast("Error al actualizar: " + e.getMessage()));
    }

    private void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
