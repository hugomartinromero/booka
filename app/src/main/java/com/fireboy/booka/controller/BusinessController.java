package com.fireboy.booka.controller;

import com.fireboy.booka.model.Business;
import com.fireboy.booka.utils.Constants;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controlador para gestionar operaciones relacionadas con la colección de negocios (businesses)
 * en Firebase Firestore.
 *
 * Proporciona métodos para obtener todos los negocios, un negocio por ID o negocios por categoría.
 */
public class BusinessController {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Callback para retornar una lista de objetos {@link Business}.
     */
    public interface BusinessCallback {
        /**
         * Método invocado al completar la operación con una lista de negocios.
         *
         * @param businesses Lista de negocios obtenidos o vacía en caso de error.
         */
        void onResult(List<Business> businesses);
    }

    /**
     * Callback para retornar un único objeto {@link Business}.
     */
    public interface BusinessSingleCallback {
        /**
         * Método invocado al completar la operación con un negocio.
         *
         * @param business Negocio obtenido o {@code null} si no existe o hay error.
         */
        void onResult(Business business);
    }

    /**
     * Obtiene todos los negocios desde la colección correspondiente en Firestore.
     *
     * @param callback Callback que recibe la lista de negocios.
     */
    public void getAllBusinesses(BusinessCallback callback) {
        db.collection(Constants.BUSINESSES_COLLECTION)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<Business> list = parseBusinessList(snapshot.getDocuments());
                    callback.onResult(list);
                })
                .addOnFailureListener(e -> callback.onResult(new ArrayList<>()));
    }

    /**
     * Obtiene un negocio específico a partir de su ID.
     *
     * @param businessId ID del negocio.
     * @param callback   Callback que recibe el objeto {@link Business}.
     */
    public void getBusinessById(String businessId, BusinessSingleCallback callback) {
        db.collection(Constants.BUSINESSES_COLLECTION).document(businessId)
                .get()
                .addOnSuccessListener(doc -> {
                    Business b = parseBusiness(doc);
                    if (b != null) callback.onResult(b);
                });
    }

    /**
     * Obtiene los negocios que pertenecen a una categoría específica.
     *
     * @param category Categoría a filtrar.
     * @param callback Callback que recibe la lista de negocios aleatorizada.
     */
    public void getBusinessByCategory(String category, BusinessCallback callback) {
        db.collection(Constants.BUSINESSES_COLLECTION)
                .whereEqualTo("category", category)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<Business> businesses = parseBusinessList(snapshot.getDocuments());
                    Collections.shuffle(businesses);
                    callback.onResult(businesses);
                })
                .addOnFailureListener(e -> callback.onResult(new ArrayList<>()));
    }

    /**
     * Parsea un documento de Firestore a un objeto {@link Business}.
     *
     * @param doc Documento Firestore.
     * @return Objeto {@link Business} o {@code null} si la conversión falla.
     */
    private Business parseBusiness(DocumentSnapshot doc) {
        Business business = doc.toObject(Business.class);
        if (business != null) business.setId(doc.getId());
        return business;
    }

    /**
     * Parsea una lista de documentos de Firestore a una lista de objetos {@link Business}.
     *
     * @param docs Lista de documentos Firestore.
     * @return Lista de objetos {@link Business}.
     */
    private List<Business> parseBusinessList(List<DocumentSnapshot> docs) {
        List<Business> businesses = new ArrayList<>();
        for (DocumentSnapshot doc : docs) {
            Business b = parseBusiness(doc);
            if (b != null) businesses.add(b);
        }
        return businesses;
    }
}
