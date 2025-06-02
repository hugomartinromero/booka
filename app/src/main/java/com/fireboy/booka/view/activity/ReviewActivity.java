package com.fireboy.booka.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.AuthController;
import com.fireboy.booka.controller.BusinessController;
import com.fireboy.booka.controller.ReviewController;
import com.fireboy.booka.model.Review;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;

/**
 * Actividad que permite al usuario calificar un negocio con estrellas y un comentario.
 * La reseña se guarda en Firebase Firestore.
 */
public class ReviewActivity extends AppCompatActivity {

    private TextView lblBusiness;
    private ImageView[] stars;
    private TextInputEditText txtMessage;
    private MaterialButton btnSubmit;

    private BusinessController businessController;
    private AuthController authController;
    private ReviewController reviewController;

    private final Review review = new Review();

    /**
     * Método que se ejecuta al crear la actividad.
     * Carga el negocio, permite seleccionar estrellas y enviar el comentario.
     *
     * @param savedInstanceState Estado previo de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        initComponents();
        loadBusinessData();
    }

    /**
     * Inicializa los componentes de la vista y los controladores.
     */
    private void initComponents() {
        lblBusiness = findViewById(R.id.lblBusinessReview);
        stars = new ImageView[]{
                findViewById(R.id.star1),
                findViewById(R.id.star2),
                findViewById(R.id.star3),
                findViewById(R.id.star4),
                findViewById(R.id.star5)
        };
        txtMessage = findViewById(R.id.txtMessageContent);
        btnSubmit = findViewById(R.id.btnSubmit);

        businessController = new BusinessController();
        authController = new AuthController(this);
        reviewController = new ReviewController(this);
    }

    /**
     * Carga los datos del negocio y configura la lógica de interacción.
     */
    private void loadBusinessData() {
        String businessId = getIntent().getStringExtra("businessId");

        if (businessId == null || businessId.trim().isEmpty()) {
            finish();
            return;
        }

        businessController.getBusinessById(businessId, business -> {
            if (business == null) {
                finish();
                return;
            }

            lblBusiness.setText(business.getName());

            review.setBusinessId(business.getId());
            review.setUserId(authController.getCurrentUser().getUid());
            review.setTimestamp(Timestamp.now());

            setupStarSelection();
            setupSubmitButton();
        });
    }

    /**
     * Configura la selección de estrellas y visualización del color correspondiente.
     */
    private void setupStarSelection() {
        for (int i = 0; i < stars.length; i++) {
            int index = i;
            stars[i].setOnClickListener(v -> {
                for (int j = 0; j < stars.length; j++) {
                    int colorResId = (j <= index) ? R.color.star_color : R.color.booka_secondary;
                    stars[j].setColorFilter(ContextCompat.getColor(this, colorResId));
                }
                review.setRating(index + 1);
            });
        }
    }

    /**
     * Configura el botón para enviar la reseña a Firestore.
     */
    private void setupSubmitButton() {
        btnSubmit.setOnClickListener(v -> {
            String comment = txtMessage.getText() != null ? txtMessage.getText().toString().trim() : "";
            review.setComment(comment);
            reviewController.saveReview(review);
        });
    }
}
