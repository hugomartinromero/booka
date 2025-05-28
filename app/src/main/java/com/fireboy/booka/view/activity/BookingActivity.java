package com.fireboy.booka.view.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.BusinessController;
import com.fireboy.booka.view.adapter.ServiceAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {
    TextView lblBooking;
    TextInputEditText txtDatePicker2, txtSchedule2;
    RecyclerView rvServices;

    BusinessController businessController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        initComponents();

        businessController = new BusinessController();

        businessController.getBusinessById(getIntent().getStringExtra("businessId"), business -> {
            lblBooking.setText(business.getName());

            rvServices.setLayoutManager(new LinearLayoutManager(this));
            rvServices.setAdapter(new ServiceAdapter(business.getServices(), this, true));
        });

        txtDatePicker2.setOnClickListener(v -> showDatePicker());
    }

    private void initComponents() {
        lblBooking = findViewById(R.id.lblBooking);
        rvServices = findViewById(R.id.rvServices);
        txtDatePicker2 = findViewById(R.id.txtDatePicker2);
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, y, m, d) -> {
            String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", d, m + 1, y);
            txtDatePicker2.setText(selectedDate);

//            AutoCompleteTextView spinnerSchedule = findViewById(R.id.spinnerSchedule);
//            String[] horarios = {"10:00 - 11:00", "11:00 - 12:00", "12:00 - 13:00"};
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                    this,
//                    android.R.layout.simple_dropdown_item_1line,
//                    horarios
//            );
//
//            spinnerSchedule.setAdapter(adapter);
//            spinnerSchedule.setOnClickListener(v -> spinnerSchedule.showDropDown());
        }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        Calendar maxDate = (Calendar) calendar.clone();
        maxDate.add(Calendar.MONTH, 1);
        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

        datePickerDialog.show();
    }
}