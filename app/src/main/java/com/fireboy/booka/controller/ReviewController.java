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

public class ReviewController {
    private final FirebaseFirestore db;
    private final Activity activity;

    public ReviewController(Activity activity) {
        this.activity = activity;
        this.db = FirebaseFirestore.getInstance();
    }

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

    private boolean isValidReview(Review r) {
        return r.getRating() >= 0 && r.getRating() <= 5 && !r.getComment().isEmpty();
    }

    private void showToast(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    private List<Review> parseReviews(Iterable<QueryDocumentSnapshot> docs) {
        List<Review> reviews = new ArrayList<>();
        for (QueryDocumentSnapshot doc : docs) {
            Review r = doc.toObject(Review.class);
            if (r != null) reviews.add(r);
        }
        return reviews;
    }
}
