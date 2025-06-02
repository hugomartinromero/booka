package com.fireboy.booka.controller;

import com.fireboy.booka.model.Category;
import com.fireboy.booka.utils.Constants;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con categorías en Firebase Firestore.
 */
public class CategoryController {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Interfaz de callback para retornar una lista de objetos {@link Category}.
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
     * Interfaz de callback para retornar un único objeto {@link Category}.
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
     * Obtiene todas las categorías desde Firestore.
     *
     * @param callback Callback que recibe la lista completa de categorías.
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
     * Obtiene solo las categorías que tienen al menos un negocio asociado en Firestore.
     *
     * @param callback Callback que recibe la lista de categorías con negocios.
     */
    public void getActiveCategories(CategoryCallback callback) {
        db.collection(Constants.CATEGORIES_COLLECTION)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<Category> allCategories = parseCategoryList(snapshot.getDocuments());
                    List<Category> filtered = new ArrayList<>();
                    final int[] remaining = {allCategories.size()};

                    if (remaining[0] == 0) {
                        callback.onResult(new ArrayList<>());
                        return;
                    }

                    for (Category category : allCategories) {
                        db.collection(Constants.BUSINESSES_COLLECTION)
                                .whereEqualTo("category", category.getName())
                                .limit(1)
                                .get()
                                .addOnSuccessListener(businessSnap -> {
                                    if (!businessSnap.isEmpty()) {
                                        filtered.add(category);
                                    }
                                    checkCompletion(remaining, filtered, callback);
                                })
                                .addOnFailureListener(e -> checkCompletion(remaining, filtered, callback));
                    }
                })
                .addOnFailureListener(e -> callback.onResult(new ArrayList<>()));
    }

    /**
     * Verifica si todas las consultas han finalizado y retorna el resultado.
     *
     * @param remaining Contador de consultas pendientes.
     * @param filtered  Lista de categorías válidas.
     * @param callback  Callback final.
     */
    private void checkCompletion(int[] remaining, List<Category> filtered, CategoryCallback callback) {
        remaining[0]--;
        if (remaining[0] == 0) {
            Collections.shuffle(filtered);
            callback.onResult(filtered);
        }
    }

    /**
     * Convierte un documento de Firestore a un objeto {@link Category}.
     *
     * @param doc Documento Firestore.
     * @return Objeto {@link Category} o {@code null} si ocurre un error de conversión.
     */
    private Category parseCategory(DocumentSnapshot doc) {
        Category category = doc.toObject(Category.class);
        if (category != null) {
            category.setId(doc.getId());
        }
        return category;
    }

    /**
     * Convierte una lista de documentos Firestore en una lista de objetos {@link Category}.
     *
     * @param docs Lista de documentos.
     * @return Lista de categorías válidas.
     */
    private List<Category> parseCategoryList(List<DocumentSnapshot> docs) {
        List<Category> categories = new ArrayList<>();
        for (DocumentSnapshot doc : docs) {
            Category c = parseCategory(doc);
            if (c != null) {
                categories.add(c);
            }
        }
        return categories;
    }
}
