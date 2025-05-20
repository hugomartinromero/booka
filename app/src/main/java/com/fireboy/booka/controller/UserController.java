package com.fireboy.booka.controller;

import android.app.Activity;
import android.widget.Toast;

import com.fireboy.booka.view.MainActivity;
import com.fireboy.booka.view.UiExtensions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class UserController {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void saveUserToFirestore(FirebaseUser user, Activity activity) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", user.getEmail());
        userData.put("username", user.getDisplayName());
        userData.put("role", "usuario");
        userData.put("photo", user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "");

        db.collection("users").document(user.getUid())
                .set(userData, SetOptions.merge())
                .addOnSuccessListener(unused ->
                        UiExtensions.navigateTo(activity, MainActivity.class))
                .addOnFailureListener(e ->
                        Toast.makeText(activity, "Error al crear usuario: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
}
