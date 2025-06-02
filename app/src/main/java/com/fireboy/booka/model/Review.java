package com.fireboy.booka.model;

import com.google.firebase.Timestamp;

/**
 * Modelo que representa una reseña hecha por un usuario sobre un negocio.
 *
 * Contiene información como calificación, comentario, autor, negocio asociado y fecha/hora de creación.
 */
public class Review {

    private String userId;
    private String businessId;
    private double rating;
    private String comment;
    private Timestamp timestamp;

    /**
     * Constructor vacío requerido por Firebase Firestore.
     */
    public Review() {}

    /**
     * Constructor completo para inicializar una reseña.
     *
     * @param userId     ID del usuario que hizo la reseña.
     * @param businessId ID del negocio reseñado.
     * @param rating     Calificación dada por el usuario (0 a 5).
     * @param comment    Comentario escrito por el usuario.
     * @param timestamp  Marca de tiempo de la reseña.
     */
    public Review(String userId, String businessId, double rating, String comment, Timestamp timestamp) {
        this.userId = userId;
        this.businessId = businessId;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    /**
     * @return ID del usuario que realizó la reseña.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Establece el ID del usuario.
     *
     * @param userId ID del autor de la reseña.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return ID del negocio al que se refiere la reseña.
     */
    public String getBusinessId() {
        return businessId;
    }

    /**
     * Establece el ID del negocio reseñado.
     *
     * @param businessId ID del negocio.
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    /**
     * @return Calificación otorgada por el usuario (de 0 a 5).
     */
    public double getRating() {
        return rating;
    }

    /**
     * Establece la calificación.
     *
     * @param rating Número entre 0 y 5 que representa la valoración del usuario.
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * @return Comentario escrito por el usuario.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Establece el comentario de la reseña.
     *
     * @param comment Texto libre escrito por el usuario.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return Timestamp que indica cuándo se creó la reseña.
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Establece el timestamp de la reseña.
     *
     * @param timestamp Marca de tiempo Firebase.
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
