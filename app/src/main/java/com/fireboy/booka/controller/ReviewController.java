package com.fireboy.booka.controller;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fireboy.booka.model.Review;
import com.fireboy.booka.utils.Constants;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con reseñas en Firebase Firestore.
 *
 * Permite guardar reseñas, obtener reseñas por negocio y obtener reseñas por usuario.
 */
public class ReviewController {

    private final FirebaseFirestore db;
    private final Activity activity;

    /**
     * Constructor que inicializa el controlador con la actividad actual.
     *
     * @param activity Actividad desde la cual se instancia este controlador.
     */
    public ReviewController(Activity activity) {
        this.activity = activity;
        this.db = FirebaseFirestore.getInstance();
    }

    /**
     * Guarda una nueva reseña en Firestore si es válida.
     *
     * @param review Objeto {@link Review} a guardar.
     */
    public void saveReview(@NonNull Review review) {
        if (!isValidReview(review)) {
            showToast("Por favor, escribe un comentario válido y puntúa de 0 a 5.");
            return;
        }

        db.collection(Constants.REVIEWS_COLLECTION)
                .add(review)
                .addOnSuccessListener(docRef ->
                        showToast("Reseña enviada con éxito."))
                .addOnFailureListener(e ->
                        showToast("Error al guardar reseña: " + e.getMessage()));
    }

    /**
     * Obtiene todas las reseñas de un negocio específico, ordenadas por fecha descendente.
     *
     * @param businessId ID del negocio.
     * @param onResult   Callback que recibe la lista de reseñas.
     */
    public void getReviewsByBusinessId(@NonNull String businessId, @NonNull Consumer<List<Review>> onResult) {
        if (businessId.trim().isEmpty()) {
            showToast("ID del negocio no válido.");
            return;
        }

        db.collection(Constants.REVIEWS_COLLECTION)
                .whereEqualTo("businessId", businessId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(query -> {
                    List<Review> reviews = parseReviews(query);
                    onResult.accept(reviews);
                })
                .addOnFailureListener(e ->
                        showToast("Error al obtener reseñas: " + e.getMessage()));
    }

    /**
     * Obtiene todas las reseñas realizadas por un usuario, ordenadas por fecha descendente.
     *
     * @param userId   ID del usuario.
     * @param onResult Callback que recibe la lista de reseñas.
     */
    public void getReviewsByUserId(@NonNull String userId, @NonNull Consumer<List<Review>> onResult) {
        if (userId.trim().isEmpty()) {
            showToast("ID del usuario no válido.");
            return;
        }

        db.collection(Constants.REVIEWS_COLLECTION)
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(query -> {
                    List<Review> reviews = parseReviews(query);
                    onResult.accept(reviews);
                })
                .addOnFailureListener(e ->
                        showToast("Error al obtener tus reseñas: " + e.getMessage()));
    }

    /**
     * Valida una reseña antes de guardarla.
     *
     * @param r Objeto {@link Review} a validar.
     * @return {@code true} si la reseña tiene un comentario y rating válido (0 a 5), {@code false} en caso contrario.
     */
    private boolean isValidReview(Review r) {
        return r.getRating() >= 0 && r.getRating() <= 5 && !r.getComment().isEmpty();
    }

    /**
     * Muestra un mensaje tipo Toast en la actividad actual.
     *
     * @param msg Texto a mostrar.
     */
    private void showToast(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Convierte los documentos de Firestore a una lista de objetos {@link Review}.
     *
     * @param docs Iterable de documentos de tipo {@link QueryDocumentSnapshot}.
     * @return Lista de reseñas parseadas.
     */
    private List<Review> parseReviews(Iterable<QueryDocumentSnapshot> docs) {
        List<Review> reviews = new ArrayList<>();
        for (QueryDocumentSnapshot doc : docs) {
            Review r = doc.toObject(Review.class);
            if (r != null) reviews.add(r);
        }
        return reviews;
    }
}
