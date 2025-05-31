package com.fireboy.booka.controller;

import android.app.Activity;
import android.widget.Toast;

import com.fireboy.booka.model.Reservation;
import com.fireboy.booka.view.UiExtensions;
import com.fireboy.booka.view.activity.MainActivity;
import com.google.firebase.firestore.FirebaseFirestore;

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

        db.collection("reservations")
                .whereEqualTo("businessId", reservation.getBusinessId())
                .whereEqualTo("date", reservation.getDate())
                .whereEqualTo("time", reservation.getTime())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        Toast.makeText(activity, "Esta franja ya está reservada.", Toast.LENGTH_SHORT).show();
                    } else {
                        db.collection("reservations")
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
}
