package com.fireboy.booka.model;

/**
 * Modelo que representa una reserva realizada por un usuario en un negocio.
 *
 * Contiene la información necesaria para agendar una cita: usuario, negocio, servicio,
 * fecha, hora y precio.
 */
public class Reservation {

    private String userId;
    private String businessId;
    private String service;
    private String date;
    private String time;
    private double price;

    /**
     * Constructor vacío requerido por Firebase Firestore.
     */
    public Reservation() {
    }

    /**
     * Constructor completo para inicializar una reserva.
     *
     * @param userId     ID del usuario que realiza la reserva.
     * @param businessId ID del negocio donde se reserva.
     * @param service    Nombre o ID del servicio reservado.
     * @param date       Fecha de la reserva (formato: yyyy-MM-dd).
     * @param time       Hora de la reserva (formato: HH:mm).
     * @param price      Precio del servicio reservado.
     */
    public Reservation(String userId, String businessId, String service, String date, String time, double price) {
        this.userId = userId;
        this.businessId = businessId;
        this.service = service;
        this.date = date;
        this.time = time;
        this.price = price;
    }

    /**
     * @return ID del usuario que hizo la reserva.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Establece el ID del usuario.
     *
     * @param userId ID del usuario.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return ID del negocio donde se hace la reserva.
     */
    public String getBusinessId() {
        return businessId;
    }

    /**
     * Establece el ID del negocio.
     *
     * @param businessId ID del negocio.
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    /**
     * @return Nombre o ID del servicio reservado.
     */
    public String getService() {
        return service;
    }

    /**
     * Establece el servicio reservado.
     *
     * @param serviceId Nombre o ID del servicio.
     */
    public void setService(String serviceId) {
        this.service = serviceId;
    }

    /**
     * @return Fecha de la reserva (formato: yyyy-MM-dd).
     */
    public String getDate() {
        return date;
    }

    /**
     * Establece la fecha de la reserva.
     *
     * @param date Fecha de la reserva.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return Hora de la reserva (formato: HH:mm).
     */
    public String getTime() {
        return time;
    }

    /**
     * Establece la hora de la reserva.
     *
     * @param time Hora de la reserva.
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return Precio del servicio reservado.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Establece el precio del servicio.
     *
     * @param price Precio en moneda local.
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
