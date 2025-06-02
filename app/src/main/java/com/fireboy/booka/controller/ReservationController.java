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
            Toast.makeText(activity, "Datos de la reserva no válidos.", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection(Constants.RESERVATIONS_COLLECTION)
                .whereEqualTo("businessId", reservation.getBusinessId())
                .whereEqualTo("date", reservation.getDate())
                .whereEqualTo("time", reservation.getTime())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        Toast.makeText(activity, "Esta franja ya está reservada.", Toast.LENGTH_SHORT).show();
                    } else {
                        db.collection(Constants.RESERVATIONS_COLLECTION)
                                .add(reservation)
                                .addOnSuccessListener(docRef -> {
                                    Toast.makeText(activity, "Reserva creada con éxito", Toast.LENGTH_SHORT).show();
                                    UiExtensions.navigateTo(activity, MainActivity.class, true);
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(activity, "Error al crear reserva: " + e.getMessage(), Toast.LENGTH_LONG).show());
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(activity, "Error al verificar disponibilidad: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    public void getReservationsByUserId(@NonNull String userId, @NonNull Consumer<List<Reservation>> onResult) {
        if (userId.isEmpty()) {
            Toast.makeText(activity, "ID de usuario no válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection(Constants.RESERVATIONS_COLLECTION)
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Reservation> reservations = new ArrayList<>();

                    if (querySnapshot.isEmpty()) {
                        Toast.makeText(activity, "No tienes reservas registradas.", Toast.LENGTH_SHORT).show();
                    } else {
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            try {
                                Reservation reservation = doc.toObject(Reservation.class);
                                if (reservation != null) {
                                    reservations.add(reservation);
                                }
                            } catch (Exception e) {
                                Toast.makeText(activity, "Error al leer reserva: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    onResult.accept(reservations);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(activity, "Error al obtener reservas: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
}
