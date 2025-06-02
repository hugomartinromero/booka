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
import com.fireboy.booka.model.Business;
import com.fireboy.booka.utils.BottomPaddingDecoration;
import com.fireboy.booka.view.adapter.ReviewAdapter;
import com.fireboy.booka.view.adapter.ServiceAdapter;
import com.google.android.material.imageview.ShapeableImageView;

/**
 * Actividad que muestra información detallada sobre un negocio, incluyendo servicios y reseñas.
 * Permite al usuario iniciar el proceso de reserva.
 */
public class InfoActivity extends AppCompatActivity {

    private ShapeableImageView imgBusiness;
    private TextView lblBusinessName, lblBusinessAddress, lblRating, txtEmptyReviews;
    private RecyclerView rvServices, rvReviewsInfo;
    private Button btnBooking;

    private BusinessController businessController;

    /**
     * Método principal que se llama al crear la actividad. Carga el negocio desde Firestore
     * y actualiza la interfaz con sus datos, servicios y reseñas.
     *
     * @param savedInstanceState Estado previo de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        initComponents();
        loadBusinessInfo(getIntent().getStringExtra("businessId"));
        setupBookingButton();
    }

    /**
     * Inicializa los componentes visuales de la actividad.
     */
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

    /**
     * Configura el botón para abrir la pantalla de reserva (BookingActivity).
     */
    private void setupBookingButton() {
        btnBooking.setOnClickListener(v -> {
            Intent intent = new Intent(this, BookingActivity.class);
            intent.putExtra("businessId", getIntent().getStringExtra("businessId"));
            startActivity(intent);
        });
    }

    /**
     * Carga los datos del negocio desde Firestore y muestra sus servicios y reseñas.
     *
     * @param businessId ID del negocio a cargar.
     */
    private void loadBusinessInfo(String businessId) {
        if (businessId == null || businessId.trim().isEmpty()) {
            finish();
            return;
        }

        businessController.getBusinessById(businessId, this::populateBusinessDetails);
    }

    /**
     * Muestra en la interfaz los datos del negocio, los servicios y las reseñas.
     *
     * @param business Objeto {@link Business} obtenido desde Firestore.
     */
    private void populateBusinessDetails(Business business) {
        if (business == null) {
            finish();
            return;
        }

        Glide.with(this)
                .load(business.getImg())
                .centerCrop()
                .into(imgBusiness);

        lblBusinessName.setText(business.getName());
        lblBusinessAddress.setText(business.getAddress());
        lblRating.setText(String.valueOf(business.getRating()));

        rvServices.setLayoutManager(new LinearLayoutManager(this));
        rvServices.setAdapter(new ServiceAdapter(business.getServices(), this, false));

        loadBusinessReviews(business.getId());
    }

    /**
     * Carga y muestra las reseñas del negocio. Si no hay reseñas, muestra un mensaje vacío.
     *
     * @param businessId ID del negocio.
     */
    private void loadBusinessReviews(String businessId) {
        ReviewController reviewController = new ReviewController(this);

        reviewController.getReviewsByBusinessId(businessId, reviews -> {
            if (reviews.isEmpty()) {
                txtEmptyReviews.setVisibility(View.VISIBLE);
                return;
            }

            int extraBottom = (int) (getResources().getDisplayMetrics().density * 66); // 66dp

            if (rvReviewsInfo.getItemDecorationCount() == 0) {
                rvReviewsInfo.addItemDecoration(new BottomPaddingDecoration(extraBottom));
            }

            rvReviewsInfo.setLayoutManager(new LinearLayoutManager(this));
            rvReviewsInfo.setAdapter(new ReviewAdapter(reviews, this, false));
        });
    }
}
