package com.fireboy.booka.view;

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
import com.google.android.material.imageview.ShapeableImageView;

public class ProfileFragment extends Fragment {
    TextView lblUserName;
    ShapeableImageView imgProfile;
    RecyclerView rvReview;
    View progressLoader;
    View homeContent;

    AuthController authController;
    UserController userController;
    ReviewController reviewController;

    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponents(view);
        initRecyclerView();

        userController.getUserById(authController.getCurrentUser().getUid(), user -> {
            Glide.with(requireContext())
                    .load(user.getPhoto())
                    .circleCrop()
                    .into(imgProfile);
            lblUserName.setText(user.getUsername());
        });

        reviewController.getReviewsByUserId(authController.getCurrentUser().getUid(), reviews -> {
            rvReview.setAdapter(new ReviewAdapter(reviews, requireActivity()));
            progressLoader.setVisibility(View.GONE);
            homeContent.setVisibility(View.VISIBLE);
        });
    }

    private void initComponents(View view) {
        lblUserName = view.findViewById(R.id.lblUserName);
        imgProfile = view.findViewById(R.id.imgProfile);
        rvReview = view.findViewById(R.id.rvReviews);
        progressLoader = view.findViewById(R.id.progressLoader3);
        homeContent = view.findViewById(R.id.homeContent3);

        authController = new AuthController(requireActivity());
        userController = new UserController(requireActivity());
        reviewController = new ReviewController(requireActivity());
    }

    private void initRecyclerView() {
        int spacing = (int) (getResources().getDisplayMetrics().density * 30); // 30dp
        int extraBottom = (int) (getResources().getDisplayMetrics().density * 60);  // 60dp

        if (rvReview.getItemDecorationCount() == 0) {
            rvReview.addItemDecoration(new VerticalSpacingDecoration(spacing));
            rvReview.addItemDecoration(new BottomPaddingDecoration(extraBottom));
        }

        rvReview.setLayoutManager(new LinearLayoutManager(requireContext()));
    }
}