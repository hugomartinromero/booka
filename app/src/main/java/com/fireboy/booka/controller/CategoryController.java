package com.fireboy.booka.controller;

import com.fireboy.booka.model.Category;
import com.fireboy.booka.utils.Constants;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controlador para gestionar operaciones relacionadas con categorías en Firebase Firestore.
 *
 * Permite obtener todas las categorías o solo las que están activas.
 */
public class CategoryController {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Callback para retornar una lista de objetos {@link Category}.
     */
    public interface CategoryCallback {
        /**
         * Método invocado al completar la operación con una lista de categorías.
         *
         * @param categories Lista de categorías obtenidas o vacía si ocurre un error.
         */
        void onResult(List<Category> categories);
    }

    /**
     * Callback para retornar un único objeto {@link Category}.
     */
    public interface CategorySingleCallback {
        /**
         * Método invocado al completar la operación con una categoría.
         *
         * @param category Categoría obtenida o {@code null} si no se encontró.
         */
        void onResult(Category category);
    }

    /**
     * Obtiene todas las categorías de la colección correspondiente en Firestore.
     *
     * @param callback Callback que recibe la lista de categorías.
     */
    public void getAllCategories(CategoryCallback callback) {
        db.collection(Constants.CATEGORIES_COLLECTION)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<Category> categories = parseCategoryList(snapshot.getDocuments());
                    callback.onResult(categories);
                })
                .addOnFailureListener(e -> callback.onResult(new ArrayList<>()));
    }

    /**
     * Obtiene solo las categorías activas (campo {@code active} en true).
     *
     * @param callback Callback que recibe la lista de categorías activas aleatorizadas.
     */
    public void getActiveCategories(CategoryCallback callback) {
        db.collection(Constants.CATEGORIES_COLLECTION)
                .whereEqualTo("active", true)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<Category> categories = parseCategoryList(snapshot.getDocuments());
                    Collections.shuffle(categories);
                    callback.onResult(categories);
                })
                .addOnFailureListener(e -> callback.onResult(new ArrayList<>()));
    }

    /**
     * Parsea un documento de Firestore a un objeto {@link Category}.
     *
     * @param doc Documento Firestore.
     * @return Objeto {@link Category} o {@code null} si la conversión falla.
     */
    private Category parseCategory(DocumentSnapshot doc) {
        Category category = doc.toObject(Category.class);
        if (category != null) category.setId(doc.getId());
        return category;
    }

    /**
     * Parsea una lista de documentos Firestore a una lista de objetos {@link Category}.
     *
     * @param docs Lista de documentos Firestore.
     * @return Lista de objetos {@link Category}.
     */
    private List<Category> parseCategoryList(List<DocumentSnapshot> docs) {
        List<Category> categories = new ArrayList<>();
        for (DocumentSnapshot doc : docs) {
            Category c = parseCategory(doc);
            if (c != null) categories.add(c);
        }
        return categories;
    }
}
