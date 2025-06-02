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

/**
 * Controlador encargado de gestionar las operaciones relacionadas con las reservas en Firebase Firestore.
 *
 * Permite crear reservas, validar disponibilidad y obtener reservas de un usuario.
 */
public class ReservationController {

    private final FirebaseFirestore db;
    private final Activity activity;

    /**
     * Constructor que inicializa el controlador con la actividad actual.
     *
     * @param activity Actividad desde donde se instancia el controlador.
     */
    public ReservationController(Activity activity) {
        this.activity = activity;
        this.db = FirebaseFirestore.getInstance();
    }

    /**
     * Crea una nueva reserva en Firestore si la franja horaria está disponible.
     * En caso de éxito, muestra un mensaje y redirige al usuario a {@link MainActivity}.
     *
     * @param reservation Objeto {@link Reservation} a crear.
     */
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

    /**
     * Obtiene todas las reservas asociadas al ID de usuario especificado.
     *
     * @param userId   ID del usuario.
     * @param onResult Callback que recibe la lista de reservas.
     */
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

    /**
     * Muestra un mensaje tipo Toast en la actividad actual.
     *
     * @param message Texto a mostrar.
     */
    private void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Convierte una lista de documentos Firestore a una lista de objetos {@link Reservation}.
     *
     * @param docs Lista de documentos.
     * @return Lista de reservas parseadas.
     */
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
