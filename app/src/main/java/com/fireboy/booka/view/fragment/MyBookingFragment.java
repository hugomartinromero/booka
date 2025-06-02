package com.fireboy.booka.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.AuthController;
import com.fireboy.booka.controller.ReservationController;
import com.fireboy.booka.model.Reservation;
import com.fireboy.booka.utils.BottomPaddingDecoration;
import com.fireboy.booka.utils.VerticalSpacingDecoration;
import com.fireboy.booka.view.adapter.ReservationDateAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBookingFragment extends Fragment {
    TextView lblReservations, lblEmptyReservations;
    RecyclerView rvDate;
    View progressLoader;
    View homeContent;

    ReservationController rc;
    AuthController ac;

    public MyBookingFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_bookings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponents(view);
        initRecyclerView();

        rc.getReservationsByUserId(ac.getCurrentUser().getUid(), reservations -> {
            if (reservations.isEmpty()) {
                lblReservations.setVisibility(View.VISIBLE);
                lblEmptyReservations.setVisibility(View.VISIBLE);
            } else {
                Map<String, List<Reservation>> grouped = new HashMap<>();

                for (Reservation r : reservations) {
                    if (!grouped.containsKey(r.getDate())) {
                        grouped.put(r.getDate(), new ArrayList<>());
                    }
                    grouped.get(r.getDate()).add(r);
                }

                rvDate.setAdapter(new ReservationDateAdapter(grouped, requireActivity()));
            }

            progressLoader.setVisibility(View.GONE);
            homeContent.setVisibility(View.VISIBLE);
        });

    }

    private void initComponents(View view) {
        lblReservations = view.findViewById(R.id.lblReservations);
        lblEmptyReservations = view.findViewById(R.id.lblEmptyReservations);
        rvDate = view.findViewById(R.id.rvDate);
        progressLoader = view.findViewById(R.id.progressLoader);
        homeContent = view.findViewById(R.id.homeContent2);

        rc = new ReservationController(requireActivity());
        ac = new AuthController(requireActivity());
    }

    private void initRecyclerView() {
        int spacing = (int) (this.getResources().getDisplayMetrics().density * 30); // 30dp
        int extraBottom = (int) (getResources().getDisplayMetrics().density * 60);  // 60dp

        if (rvDate.getItemDecorationCount() == 0) {
            rvDate.addItemDecoration(new VerticalSpacingDecoration(spacing));
            rvDate.addItemDecoration(new BottomPaddingDecoration(extraBottom));
        }

        rvDate.setLayoutManager(new LinearLayoutManager(requireContext()));
    }
}