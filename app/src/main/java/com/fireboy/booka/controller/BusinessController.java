package com.fireboy.booka.controller;

import com.fireboy.booka.model.Business;
import com.fireboy.booka.utils.Constants;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusinessController {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public interface BusinessCallback {
        void onResult(List<Business> businesses);
    }

    public interface BusinessSingleCallback {
        void onResult(Business business);
    }

    public void getAllBusinesses(BusinessCallback callback) {
        db.collection(Constants.BUSINESSES_COLLECTION)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<Business> list = parseBusinessList(snapshot.getDocuments());
                    callback.onResult(list);
                })
                .addOnFailureListener(e -> callback.onResult(new ArrayList<>()));
    }

    public void getBusinessById(String businessId, BusinessSingleCallback callback) {
        db.collection(Constants.BUSINESSES_COLLECTION).document(businessId)
                .get()
                .addOnSuccessListener(doc -> {
                    Business b = parseBusiness(doc);
                    if (b != null) callback.onResult(b);
                });
    }

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

    private Business parseBusiness(DocumentSnapshot doc) {
        Business business = doc.toObject(Business.class);
        if (business != null) business.setId(doc.getId());
        return business;
    }

    private List<Business> parseBusinessList(List<DocumentSnapshot> docs) {
        List<Business> businesses = new ArrayList<>();
        for (DocumentSnapshot doc : docs) {
            Business b = parseBusiness(doc);
            if (b != null) businesses.add(b);
        }
        return businesses;
    }
}
