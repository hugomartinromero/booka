package com.fireboy.booka.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fireboy.booka.R;
import com.fireboy.booka.controller.AuthController;
import com.fireboy.booka.controller.ReviewController;
import com.fireboy.booka.controller.UserController;
import com.fireboy.booka.utils.BottomPaddingDecoration;
import com.fireboy.booka.utils.VerticalSpacingDecoration;
import com.fireboy.booka.view.adapter.ReviewAdapter;
import com.google.android.material.imageview.ShapeableImageView;

/**
 * Fragmento que muestra el perfil del usuario logueado, incluyendo su nombre,
 * foto y reseñas publicadas.
 */
public class ProfileFragment extends Fragment {

    private TextView lblUserName, txtEmptyReviews;
    private ShapeableImageView imgProfile;
    private RecyclerView rvReview;
    private View progressLoader, homeContent;

    private AuthController authController;
    private UserController userController;
    private ReviewController reviewController;

    /**
     * Constructor requerido vacío.
     */
    public ProfileFragment() {}

    /**
     * Infla el layout del fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    /**
     * Se ejecuta una vez que la vista está creada. Carga datos del usuario y sus reseñas.
     */
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponents(view);
        initRecyclerView();
        loadUserInfo();
        loadUserReviews();
    }

    /**
     * Inicializa las vistas y controladores del fragmento.
     */
    private void initComponents(View view) {
        lblUserName = view.findViewById(R.id.lblUserName);
        txtEmptyReviews = view.findViewById(R.id.lblEmptyReviews);
        imgProfile = view.findViewById(R.id.imgProfile);
        rvReview = view.findViewById(R.id.rvReviews);
        progressLoader = view.findViewById(R.id.progressLoader3);
        homeContent = view.findViewById(R.id.homeContent3);

        authController = new AuthController(requireActivity());
        userController = new UserController(requireActivity());
        reviewController = new ReviewController(requireActivity());
    }

    /**
     * Configura el RecyclerView con espaciado y padding inferior.
     */
    private void initRecyclerView() {
        int spacing = (int) (getResources().getDisplayMetrics().density * 30); // 30dp
        int extraBottom = (int) (getResources().getDisplayMetrics().density * 60); // 60dp

        if (rvReview.getItemDecorationCount() == 0) {
            rvReview.addItemDecoration(new VerticalSpacingDecoration(spacing));
            rvReview.addItemDecoration(new BottomPaddingDecoration(extraBottom));
        }

        rvReview.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    /**
     * Carga la información del usuario logueado desde Firestore y la muestra.
     */
    private void loadUserInfo() {
        String userId = authController.getCurrentUser().getUid();

        userController.getUserById(userId, user -> {
            if (user == null) return;

            Glide.with(requireContext())
                    .load(user.getPhoto())
                    .circleCrop()
                    .into(imgProfile);

            lblUserName.setText(user.getUsername());
        });
    }

    /**
     * Carga las reseñas publicadas por el usuario y las muestra en el RecyclerView.
     * Si no hay reseñas, muestra un mensaje.
     */
    private void loadUserReviews() {
        String userId = authController.getCurrentUser().getUid();

        reviewController.getReviewsByUserId(userId, reviews -> {
            if (reviews.isEmpty()) {
                txtEmptyReviews.setVisibility(View.VISIBLE);
            } else {
                rvReview.setAdapter(new ReviewAdapter(reviews, requireActivity(), true));
            }

            progressLoader.setVisibility(View.GONE);
            homeContent.setVisibility(View.VISIBLE);
        });
    }
}
