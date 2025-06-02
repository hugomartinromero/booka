package com.fireboy.booka.controller;

import com.fireboy.booka.model.Category;
import com.fireboy.booka.utils.Constants;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CategoryController {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public interface CategoryCallback {
        void onResult(List<Category> categories);
    }

    public interface CategoryCallbackSingle {
        void onResult(Category category);
    }

    public void getAllCategories(CategoryCallback callback) {
        db.collection(Constants.CATEGORIES_COLLECTION)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Category> list = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        Category category = doc.toObject(Category.class);
                        if (category != null) {
                            category.setId(doc.getId());
                            list.add(category);
                        }
                    }
                    callback.onResult(list);
                });
    }

    public void getActiveCategories(CategoryCallback callback) {
        db.collection(Constants.CATEGORIES_COLLECTION)
                .whereEqualTo("active", true)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Category> lista = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        Category category = doc.toObject(Category.class);
                        if (category != null) {
                            category.setId(doc.getId());
                            lista.add(category);
                        }
                    }
                    callback.onResult(lista);
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    callback.onResult(new ArrayList<>());
                });
    }
}
