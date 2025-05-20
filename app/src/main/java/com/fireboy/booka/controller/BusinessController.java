package com.fireboy.booka.controller;

import com.fireboy.booka.model.Business;
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
        db.collection("businesses")
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
        db.collection("businesses").document(businessId)
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
}
