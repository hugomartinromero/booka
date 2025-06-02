package com.fireboy.booka.controller;

import com.fireboy.booka.model.Business;
import com.fireboy.booka.utils.Constants;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
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
                    List<Business> list = new ArrayList<>();
                    for (DocumentSnapshot doc : snapshot) {
                        Business b = doc.toObject(Business.class);
                        if (b != null) {
                            b.setId(doc.getId());
                            list.add(b);
                        }
                    }
                    callback.onResult(list);
                });
    }

    public void getBusinessById(String businessId, BusinessSingleCallback callback) {
        db.collection(Constants.BUSINESSES_COLLECTION).document(businessId)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        Business b = doc.toObject(Business.class);
                        if (b != null) {
                            b.setId(doc.getId());
                            callback.onResult(b);
                        }
                    }
                });
    }

    public void getBusinessByCategory(String category, BusinessCallback callback) {
        db.collection(Constants.BUSINESSES_COLLECTION)
                .whereEqualTo("category", category)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Business> list = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        Business business = doc.toObject(Business.class);
                        if (business != null) {
                            business.setId(doc.getId());
                            list.add(business);
                        }
                    }
                    callback.onResult(list);
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    callback.onResult(new ArrayList<>()); // vacío si falla
                });
    }

}
