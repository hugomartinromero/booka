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
import java.util.Locale;
import java.util.Map;

/**
 * Actividad donde el usuario puede reservar un servicio ofrecido por un negocio.
 * Permite elegir servicio, fecha y horario disponible.
 */
public class BookingActivity extends AppCompatActivity {

    private TextView lblBusinessName;
    private TextInputEditText txtDate;
    private AutoCompleteTextView spSchedule;
    private RecyclerView rvServices;
    private MaterialButton btnReserve;

    private ServiceAdapter serviceAdapter;
    private BusinessController businessController;

    /**
     * Método principal llamado al crear la actividad. Inicializa vistas y obtiene el negocio.
     *
     * @param savedInstanceState Estado previo de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        initViews();

        businessController = new BusinessController();
        final String businessId = getIntent().getStringExtra("businessId");

        if (businessId == null || businessId.trim().isEmpty()) {
            Toast.makeText(this, "ID de negocio no válido.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadBusinessData(businessId);
    }

    /**
     * Asocia las variables de clase con las vistas del layout.
     */
    private void initViews() {
        lblBusinessName = findViewById(R.id.lblBooking);
        txtDate = findViewById(R.id.txtDatePicker2);
        spSchedule = findViewById(R.id.spSchedule);
        rvServices = findViewById(R.id.rvServices);
        btnReserve = findViewById(R.id.btnBooking);
    }

    /**
     * Obtiene la información del negocio y configura la vista con sus servicios y horarios.
     *
     * @param businessId ID del negocio seleccionado.
     */
    private void loadBusinessData(String businessId) {
        businessController.getBusinessById(businessId, business -> {
            if (business == null) {
                Toast.makeText(this, "Negocio no encontrado.", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            lblBusinessName.setText(business.getName());
            setupServicesList(business.getServices());

            txtDate.setOnClickListener(v -> showDatePicker(business.getSchedule()));
            btnReserve.setOnClickListener(v -> reserveService(business.getId(), business.getSchedule()));
        });
    }

    /**
     * Configura el RecyclerView con los servicios del negocio.
     *
     * @param services Lista de servicios ofrecidos.
     */
    private void setupServicesList(java.util.List<Service> services) {
        serviceAdapter = new ServiceAdapter(services, this, true);
        rvServices.setLayoutManager(new LinearLayoutManager(this));
        rvServices.setAdapter(serviceAdapter);
    }

    /**
     * Muestra un DatePicker con fechas limitadas al mes actual.
     *
     * @param schedule Mapa de horarios disponibles por día.
     */
    private void showDatePicker(Map<String, DaySchedule> schedule) {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, (view, y, m, d) ->
                onDateSelected(y, m, d, schedule), year, month, day);

        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        calendar.add(Calendar.MONTH, 1);
        dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        dialog.show();
    }

    /**
     * Maneja la lógica al seleccionar una fecha y actualiza el horario disponible.
     *
     * @param year     Año seleccionado.
     * @param month    Mes seleccionado (0–11).
     * @param day      Día del mes.
     * @param schedule Horario completo del negocio.
     */
    private void onDateSelected(int year, int month, int day, Map<String, DaySchedule> schedule) {
        final Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, day);

        final String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month + 1, year);
        txtDate.setText(formattedDate);

        final String dayName = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(selectedDate.getTime()).toLowerCase();
        updateScheduleOptions(dayName, schedule);
    }

    /**
     * Muestra los horarios disponibles para el día seleccionado en el spinner.
     *
     * @param dayKey   Día en inglés (ej. "monday").
     * @param schedule Horarios del negocio.
     */
    private void updateScheduleOptions(String dayKey, Map<String, DaySchedule> schedule) {
        final DaySchedule daySchedule = schedule.get(dayKey);

        if (daySchedule != null && daySchedule.getAvailable() != null && !daySchedule.getAvailable().isEmpty()) {
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

    /**
     * Crea y envía una reserva a Firebase con los datos seleccionados.
     *
     * @param businessId ID del negocio.
     * @param schedule   Horarios del negocio.
     */
    private void reserveService(String businessId, Map<String, DaySchedule> schedule) {
        final AuthController authController = new AuthController(this);
        final ReservationController reservationController = new ReservationController(this);

        final Service selectedService = serviceAdapter.getSelectedService();
        final String selectedDate = txtDate.getText() != null ? txtDate.getText().toString() : "";
        final String selectedTime = spSchedule.getText() != null ? spSchedule.getText().toString() : "";

        if (selectedService == null) {
            Toast.makeText(this, "Por favor, selecciona un servicio.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedDate.isEmpty() || selectedTime.isEmpty()) {
            Toast.makeText(this, "Selecciona fecha y hora para la reserva.", Toast.LENGTH_SHORT).show();
            return;
        }

        Reservation reservation = new Reservation(
                authController.getCurrentUser().getUid(),
                businessId,
                selectedService.getName(),
                selectedDate,
                selectedTime,
                selectedService.getPrice()
        );

        reservationController.createReservation(reservation);
    }
}
