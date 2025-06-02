package com.fireboy.booka.model;

import java.util.List;

/**
 * Modelo que representa el horario de atención de un negocio para un día específico.
 *
 * Se utiliza dentro del mapa de horarios del modelo {@link com.fireboy.booka.model.Business}.
 */
public class DaySchedule {
    private List<String> available;

    /**
     * Constructor vacío requerido por Firebase Firestore.
     */
    public DaySchedule() {
    }

    /**
     * Constructor que inicializa el horario con una lista de franjas disponibles.
     *
     * @param available Lista de horarios disponibles en formato de texto (ej. "10:00", "14:30").
     */
    public DaySchedule(List<String> available) {
        this.available = available;
    }

    /**
     * @return Lista de horas disponibles para reservas en ese día.
     */
    public List<String> getAvailable() {
        return available;
    }

    /**
     * Establece las horas disponibles para el día.
     *
     * @param available Lista de strings con las horas disponibles.
     */
    public void setAvailable(List<String> available) {
        this.available = available;
    }
}
