package com.fireboy.booka.controller;

import com.fireboy.booka.model.Category;
import com.fireboy.booka.utils.Constants;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryController {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public interface CategoryCallback {
        void onResult(List<Category> categories);
    }

    public interface CategorySingleCallback {
        void onResult(Category category);
    }

    public void getAllCategories(CategoryCallback callback) {
        db.collection(Constants.CATEGORIES_COLLECTION)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<Category> categories = parseCategoryList(snapshot.getDocuments());
                    callback.onResult(categories);
                })
                .addOnFailureListener(e -> callback.onResult(new ArrayList<>()));
    }

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

    private Category parseCategory(DocumentSnapshot doc) {
        Category category = doc.toObject(Category.class);
        if (category != null) category.setId(doc.getId());
        return category;
    }

    private List<Category> parseCategoryList(List<DocumentSnapshot> docs) {
        List<Category> categories = new ArrayList<>();
        for (DocumentSnapshot doc : docs) {
            Category c = parseCategory(doc);
            if (c != null) categories.add(c);
        }
        return categories;
    }
}
