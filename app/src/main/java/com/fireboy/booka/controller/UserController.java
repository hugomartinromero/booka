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

/**
 * Controlador encargado de gestionar operaciones relacionadas con usuarios en Firebase Firestore.
 *
 * Permite guardar usuarios nuevos (por email o Google), obtener usuarios por ID
 * y actualizar el nombre de usuario.
 */
public class UserController {

    private final FirebaseFirestore db;
    private final Activity activity;

    /**
     * Constructor que recibe la actividad desde donde se maneja la interfaz.
     *
     * @param activity Actividad asociada al controlador (para mostrar mensajes).
     */
    public UserController(Activity activity) {
        this.activity = activity;
        this.db = FirebaseFirestore.getInstance();
    }

    /**
     * Guarda en Firestore un nuevo usuario autenticado con Google si aún no existe.
     *
     * @param firebaseUser Usuario autenticado con Firebase.
     * @param activity     Actividad desde la cual se ejecuta.
     */
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

    /**
     * Guarda un nuevo usuario registrado por email/contraseña en Firestore.
     *
     * @param firebaseUser Usuario autenticado.
     * @param username     Nombre de usuario personalizado.
     * @param activity     Actividad desde la cual se ejecuta.
     */
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

    /**
     * Obtiene un usuario por su ID desde Firestore.
     *
     * @param userId   ID del usuario.
     * @param onSuccess Callback que recibe el objeto {@link User} si se encuentra.
     */
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

    /**
     * Actualiza el nombre de usuario en Firestore del usuario actualmente autenticado.
     *
     * @param newName Nuevo nombre de usuario.
     */
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

    /**
     * Muestra un mensaje tipo Toast en la actividad actual.
     *
     * @param message Texto a mostrar.
     */
    private void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
