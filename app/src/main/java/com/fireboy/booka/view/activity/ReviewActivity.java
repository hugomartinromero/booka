package com.fireboy.booka.view.activity;

import android.os.Bundle;
import android.widget.EditText;
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

public class ReviewActivity extends AppCompatActivity {
    private TextView lblBusiness;
    private ImageView[] stars;
    private TextInputEditText txtMessage;
    private MaterialButton btnSubmit;

    BusinessController bc;
    AuthController ac;
    ReviewController rc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        initComponents();

        bc.getBusinessById(getIntent().getStringExtra("businessId"), business -> {
            Review r = new Review();

            lblBusiness.setText(business.getName());

            for (int i = 0; i < stars.length; i++) {
                int index = i;
                stars[i].setOnClickListener(v -> {
                    for (int j = 0; j < stars.length; j++) {
                        int color = (j <= index) ? R.color.star_color : R.color.booka_secondary;
                        stars[j].setColorFilter(ContextCompat.getColor(this, color));
                    }
                    r.setRating(index + 1);
                });
            }

            r.setBusinessId(business.getId());
            r.setTimestamp(Timestamp.now());
            r.setUserId(ac.getCurrentUser().getUid());

            btnSubmit.setOnClickListener(v -> {
                r.setComment(txtMessage.getText().toString().trim());
                rc.saveReview(r);
            });
        });
    }

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

        bc = new BusinessController();
        ac = new AuthController(this);
        rc = new ReviewController(this);
    }
}