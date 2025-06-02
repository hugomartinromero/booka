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
        if (review.getRating() < 0 || review.getRating() > 5 || review.getComment().isEmpty()) {
            Toast.makeText(activity, "Por favor, escribe un comentario válido y puntúa de 0 a 5.", Toast.LENGTH_LONG).show();
            return;
        }

        db.collection(Constants.REVIEWS_COLLECTION)
                .add(review)
                .addOnSuccessListener(docRef -> {
                    Toast.makeText(activity, "¡Reseña enviada con éxito!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(activity, "Error al guardar reseña: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    public void getReviewsByBusinessId(@NonNull String businessId, @NonNull Consumer<List<Review>> onResult) {
        if (businessId.trim().isEmpty()) {
            Toast.makeText(activity, "ID del negocio no válido", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection(Constants.REVIEWS_COLLECTION)
                .whereEqualTo("businessId", businessId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(query -> {
                    List<Review> reviews = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : query) {
                        Review review = doc.toObject(Review.class);
                        reviews.add(review);
                    }
                    onResult.accept(reviews);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(activity, "Error al obtener reseñas: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    public void getReviewsByUserId(@NonNull String userId, @NonNull Consumer<List<Review>> onResult) {
        db.collection(Constants.REVIEWS_COLLECTION)
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(query -> {
                    List<Review> reviews = new ArrayList<>();
                    query.forEach(doc -> {
                        Review r = doc.toObject(Review.class);
                        reviews.add(r);
                    });
                    onResult.accept(reviews);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(activity, "Error al obtener tus reseñas: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
