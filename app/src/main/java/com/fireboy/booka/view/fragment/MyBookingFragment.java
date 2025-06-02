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

/**
 * Fragmento que muestra las reservas del usuario agrupadas por fecha.
 */
public class MyBookingFragment extends Fragment {

    private TextView lblReservations, lblEmptyReservations;
    private RecyclerView rvDate;
    private View progressLoader, homeContent;

    private ReservationController reservationController;
    private AuthController authController;

    public MyBookingFragment() {}

    /**
     * Infla el layout del fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_bookings, container, false);
    }

    /**
     * Se ejecuta al completar la creación de la vista.
     * Carga las reservas del usuario y las muestra agrupadas por fecha.
     */
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponents(view);
        initRecyclerView();
        loadUserReservations();
    }

    /**
     * Inicializa los elementos visuales y los controladores.
     */
    private void initComponents(View view) {
        lblReservations = view.findViewById(R.id.lblReservations);
        lblEmptyReservations = view.findViewById(R.id.lblEmptyReservations);
        rvDate = view.findViewById(R.id.rvDate);
        progressLoader = view.findViewById(R.id.progressLoader);
        homeContent = view.findViewById(R.id.homeContent2);

        reservationController = new ReservationController(requireActivity());
        authController = new AuthController(requireActivity());
    }

    /**
     * Configura el RecyclerView con espaciado vertical y padding inferior.
     */
    private void initRecyclerView() {
        int spacing = (int) (getResources().getDisplayMetrics().density * 30); // 30dp
        int extraBottom = (int) (getResources().getDisplayMetrics().density * 60); // 60dp

        if (rvDate.getItemDecorationCount() == 0) {
            rvDate.addItemDecoration(new VerticalSpacingDecoration(spacing));
            rvDate.addItemDecoration(new BottomPaddingDecoration(extraBottom));
        }

        rvDate.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    /**
     * Carga las reservas del usuario autenticado y las agrupa por fecha.
     * Muestra mensaje si no hay reservas.
     */
    private void loadUserReservations() {
        String userId = authController.getCurrentUser().getUid();

        reservationController.getReservationsByUserId(userId, reservations -> {
            if (reservations.isEmpty()) {
                lblReservations.setVisibility(View.VISIBLE);
                lblEmptyReservations.setVisibility(View.VISIBLE);
            } else {
                Map<String, List<Reservation>> grouped = groupReservationsByDate(reservations);
                rvDate.setAdapter(new ReservationDateAdapter(grouped, requireActivity()));
            }

            progressLoader.setVisibility(View.GONE);
            homeContent.setVisibility(View.VISIBLE);
        });
    }

    /**
     * Agrupa una lista de reservas por fecha.
     *
     * @param reservations Lista de reservas del usuario.
     * @return Mapa agrupado por fecha (clave: String).
     */
    private Map<String, List<Reservation>> groupReservationsByDate(List<Reservation> reservations) {
        Map<String, List<Reservation>> grouped = new HashMap<>();

        for (Reservation r : reservations) {
            String date = r.getDate();
            if (!grouped.containsKey(date)) {
                grouped.put(date, new ArrayList<>());
            }
            grouped.get(date).add(r);
        }

        return grouped;
    }
}
