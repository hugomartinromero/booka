package com.fireboy.booka.view.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.AuthController;
import com.fireboy.booka.controller.BusinessController;
import com.fireboy.booka.controller.ReservationController;
import com.fireboy.booka.model.DaySchedule;
import com.fireboy.booka.model.Reservation;
import com.fireboy.booka.model.Service;
import com.fireboy.booka.view.adapter.ServiceAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {
    TextView lblBooking;
    TextInputEditText txtDatePicker2, txtSchedule2;
    RecyclerView rvServices;
    AutoCompleteTextView spSchedule;
    MaterialButton btnBooking;

    BusinessController businessController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        initComponents();

        businessController = new BusinessController();

        businessController.getBusinessById(getIntent().getStringExtra("businessId"), business -> {
            lblBooking.setText(business.getName());

            ServiceAdapter sa = new ServiceAdapter(business.getServices(), this, true);

            rvServices.setLayoutManager(new LinearLayoutManager(this));
            rvServices.setAdapter(sa);

            txtDatePicker2.setOnClickListener(v -> showDatePicker(business.getSchedule()));

            btnBooking.setOnClickListener(v -> {
                ReservationController rc = new ReservationController(this);
                AuthController ac = new AuthController(this);

                Service selectedService = sa.getSelectedService();

                if (selectedService == null) {
                    Toast.makeText(this, "Por favor, selecciona un servicio.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Reservation reservation = new Reservation(ac.getCurrentUser().getUid(),
                        business.getId(),
                        selectedService.getName(),
                        txtDatePicker2.getText().toString(),
                        spSchedule.getText().toString());

                rc.createReservation(reservation);
            });
        });
    }

    private void initComponents() {
        lblBooking = findViewById(R.id.lblBooking);
        rvServices = findViewById(R.id.rvServices);
        txtDatePicker2 = findViewById(R.id.txtDatePicker2);
        spSchedule = findViewById(R.id.spSchedule);
        btnBooking = findViewById(R.id.btnBooking);
    }

    private void showDatePicker(Map<String, DaySchedule> schedule) {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, y, m, d) -> {
            onDateSelected(y, m, d, schedule);
        }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        Calendar maxDate = (Calendar) calendar.clone();
        maxDate.add(Calendar.MONTH, 1);
        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

        datePickerDialog.show();
    }

    private void onDateSelected(int year, int month, int day, Map<String, DaySchedule> schedule) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, day);

        String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month + 1, year);
        txtDatePicker2.setText(formattedDate);

        String dayName = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(selectedDate.getTime());
        updateScheduleSpinner(dayName.toLowerCase(), schedule);
    }

    private void updateScheduleSpinner(String dayKey, Map<String, DaySchedule> schedule) {
        DaySchedule daySchedule = schedule.get(dayKey);

        if (daySchedule != null && daySchedule.getAvailable().get(0) != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    daySchedule.getAvailable()
            );

            spSchedule.setAdapter(adapter);
            spSchedule.setOnClickListener(v -> spSchedule.showDropDown());
        } else {
            spSchedule.setAdapter(null);
            Toast.makeText(this, "No hay horarios disponibles para ese día.", Toast.LENGTH_SHORT).show();
        }
    }
}