package com.fireboy.booka.controller;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fireboy.booka.model.User;
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
        String uid = firebaseUser.getUid();
        DocumentReference docRef = db.collection("users").document(uid);

        docRef.get().addOnSuccessListener(document -> {
            if (!document.exists()) {
                User user = new User(
                        uid,
                        firebaseUser.getEmail(),
                        firebaseUser.getDisplayName() != null ? firebaseUser.getDisplayName() : "Usuario",
                        "cliente",
                        firebaseUser.getPhotoUrl() != null ? firebaseUser.getPhotoUrl().toString() : ""
                );

                docRef.set(user)
                        .addOnSuccessListener(unused ->
                                Toast.makeText(activity, "Usuario registrado con Google", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e ->
                                Toast.makeText(activity, "Error al guardar usuario: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }

    public void saveNewUserToFirestore(FirebaseUser firebaseUser, String username, Activity activity) {
        String uid = firebaseUser.getUid();

        User user = new User(
                uid,
                firebaseUser.getEmail(),
                username,
                "cliente", //
                "" //
        );

        db.collection("users").document(uid)
                .set(user)
                .addOnSuccessListener(unused ->
                        Toast.makeText(activity, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(activity, "Error al guardar usuario: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    public void getUserById(@NonNull String userId, @NonNull Consumer<User> onSuccess) {
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        User user = snapshot.toObject(User.class);
                        if (user != null) {
                            onSuccess.accept(user);
                        } else {
                            Toast.makeText(activity, "Error al interpretar el usuario.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(activity, "El usuario no existe.", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(activity, "Error al obtener el usuario: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
}
