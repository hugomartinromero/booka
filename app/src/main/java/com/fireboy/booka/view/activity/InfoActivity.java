package com.fireboy.booka.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fireboy.booka.R;
import com.fireboy.booka.controller.BusinessController;
import com.fireboy.booka.controller.ReviewController;
import com.fireboy.booka.utils.BottomPaddingDecoration;
import com.fireboy.booka.view.adapter.ReviewAdapter;
import com.fireboy.booka.view.adapter.ServiceAdapter;
import com.google.android.material.imageview.ShapeableImageView;

public class InfoActivity extends AppCompatActivity {
    ShapeableImageView imgBusiness;
    TextView lblBusinessName, lblBusinessAddress, lblRating, txtEmptyReviews;;
    RecyclerView rvServices, rvReviewsInfo;
    Button btnBooking;

    BusinessController businessController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        initComponents();

        btnBooking.setOnClickListener(v -> {
            Intent intent = new Intent(this, BookingActivity.class);
            intent.putExtra("businessId", getIntent().getStringExtra("businessId"));
            this.startActivity(intent);
        });

        businessController.getBusinessById(getIntent().getStringExtra("businessId"), business -> {
            Glide.with(this)
                    .load(business.getImg())
                    .centerCrop()
                    .into(imgBusiness);
            lblBusinessName.setText(business.getName());
            lblBusinessAddress.setText(business.getAddress());
            lblRating.setText(String.valueOf(business.getRating()));

            rvServices.setLayoutManager(new LinearLayoutManager(this));
            rvServices.setAdapter(new ServiceAdapter(business.getServices(), this, false));

            ReviewController reviewController = new ReviewController(this);
            reviewController.getReviewsByBusinessId(business.getId(), reviews -> {
                if (reviews.isEmpty()) {
                    txtEmptyReviews.setVisibility(View.VISIBLE);
                } else {
                    int extraBottom = (int) (getResources().getDisplayMetrics().density * 66);  // 66dp

                    if (rvReviewsInfo.getItemDecorationCount() == 0) {
                        rvReviewsInfo.addItemDecoration(new BottomPaddingDecoration(extraBottom));
                    }

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
        txtEmptyReviews = findViewById(R.id.lblEmptyReviews);
        rvServices = findViewById(R.id.rvServices);
        rvReviewsInfo = findViewById(R.id.rvReviewsInfo);
        btnBooking = findViewById(R.id.btnBooking);

        businessController = new BusinessController();
    }
}