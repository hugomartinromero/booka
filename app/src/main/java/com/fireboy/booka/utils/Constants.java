package com.fireboy.booka.utils;

/**
 * Clase de constantes utilizadas en toda la aplicación Booka.
 *
 * Contiene nombres de colecciones de Firestore y valores predeterminados reutilizables.
 */
public class Constants {

    /** Nombre de la colección de usuarios en Firestore. */
    public static final String USERS_COLLECTION = "users";

    /** Nombre de la colección de negocios en Firestore. */
    public static final String BUSINESSES_COLLECTION = "businesses";

    /** Nombre de la colección de reservas en Firestore. */
    public static final String RESERVATIONS_COLLECTION = "reservations";

    /** Nombre de la colección de reseñas en Firestore. */
    public static final String REVIEWS_COLLECTION = "reviews";

    /** Nombre de la colección de categorías en Firestore. */
    public static final String CATEGORIES_COLLECTION = "categories";

    /** URL de la imagen por defecto para perfiles de usuario sin foto personalizada. */
    public static final String DEFAULT_PROFILE_PIC = "https://firebasestorage.googleapis.com/v0/b/booka-hg.firebasestorage.app/o/default-pic.png?alt=media&token=473bcf95-1b59-4711-af03-147ef9d27e51";
}
