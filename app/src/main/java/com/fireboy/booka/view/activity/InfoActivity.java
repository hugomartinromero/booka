package com.fireboy.booka.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fireboy.booka.R;
import com.fireboy.booka.controller.BusinessController;
import com.fireboy.booka.controller.ReviewController;
import com.fireboy.booka.view.adapter.ReviewAdapter;
import com.google.android.material.imageview.ShapeableImageView;

public class InfoActivity extends AppCompatActivity {
    ShapeableImageView imgBusiness;
    TextView lblBusinessName, lblBusinessAddress, lblRating, txtEmptyReviews;;
    RecyclerView rvServices, rvReviewsInfo;

    BusinessController businessController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        initComponents();

        businessController.getBusinessById(getIntent().getStringExtra("businessId"), business -> {
            Glide.with(this)
                    .load(business.getImg())
                    .centerCrop()
                    .into(imgBusiness);
            lblBusinessName.setText(business.getName());
            lblBusinessAddress.setText(business.getAddress());
            lblRating.setText(String.valueOf(business.getRating()));

            ReviewController reviewController = new ReviewController(this);
            reviewController.getReviewsByBusinessId(business.getId(), reviews -> {
                if (reviews.isEmpty()) {
                    txtEmptyReviews.setVisibility(View.VISIBLE);
                } else {
                    rvReviewsInfo.setLayoutManager(new LinearLayoutManager(this));
                    rvReviewsInfo.setAdapter(new ReviewAdapter(reviews, this, false));
                }
            });
        });
    }

    private void initComponents() {
        imgBusiness = findViewById(R.id.imgBusinessInfo);
        lblBusinessName = findViewById(R.id.lblBusinessName);
        lblBusinessAddress = findViewById(R.id.lblBusinessAddress);
        lblRating = findViewById(R.id.lblRating);
        txtEmptyReviews = findViewById(R.id.txtEmptyReviews);
        rvServices = findViewById(R.id.rvServices);
        rvReviewsInfo = findViewById(R.id.rvReviewsInfo);

        businessController = new BusinessController();
    }
}