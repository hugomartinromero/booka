package com.fireboy.booka.controller;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fireboy.booka.model.Reservation;
import com.fireboy.booka.utils.Constants;
import com.fireboy.booka.view.UiExtensions;
import com.fireboy.booka.view.activity.MainActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ReservationController {
    private final FirebaseFirestore db;
    private final Activity activity;

    public ReservationController(Activity activity) {
        this.activity = activity;
        this.db = FirebaseFirestore.getInstance();
    }

    public void createReservation(Reservation reservation) {
        if (reservation == null) {
            showToast("Datos de la reserva no válidos.");
            return;
        }

        db.collection(Constants.RESERVATIONS_COLLECTION)
                .whereEqualTo("businessId", reservation.getBusinessId())
                .whereEqualTo("date", reservation.getDate())
                .whereEqualTo("time", reservation.getTime())
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!snapshot.isEmpty()) {
                        showToast("Esta franja ya está reservada.");
                    } else {
                        db.collection(Constants.RESERVATIONS_COLLECTION)
                                .add(reservation)
                                .addOnSuccessListener(docRef -> {
                                    showToast("Reserva creada con éxito.");
                                    UiExtensions.navigateTo(activity, MainActivity.class, true);
                                })
                                .addOnFailureListener(e ->
                                        showToast("Error al crear reserva: " + e.getMessage()));
                    }
                })
                .addOnFailureListener(e ->
                        showToast("Error al verificar disponibilidad: " + e.getMessage()));
    }

    public void getReservationsByUserId(@NonNull String userId, @NonNull Consumer<List<Reservation>> onResult) {
        if (userId.isEmpty()) {
            showToast("ID de usuario no válido.");
            return;
        }

        db.collection(Constants.RESERVATIONS_COLLECTION)
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<Reservation> reservations = parseReservations(snapshot.getDocuments());
                    if (reservations.isEmpty()) {
                        showToast("No tienes reservas registradas.");
                    }
                    onResult.accept(reservations);
                })
                .addOnFailureListener(e ->
                        showToast("Error al obtener reservas: " + e.getMessage()));
    }

    private void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    private List<Reservation> parseReservations(List<DocumentSnapshot> docs) {
        List<Reservation> reservations = new ArrayList<>();
        for (DocumentSnapshot doc : docs) {
            try {
                Reservation r = doc.toObject(Reservation.class);
                if (r != null) reservations.add(r);
            } catch (Exception e) {
                showToast("Error al leer reserva: " + e.getMessage());
            }
        }
        return reservations;
    }
}
