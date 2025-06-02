package com.fireboy.booka.model;

/**
 * Modelo que representa un servicio ofrecido por un negocio en la aplicación Booka.
 *
 * Incluye nombre, precio, duración del servicio y el ID del negocio al que pertenece.
 */
public class Service {

    private String name;
    private double price;
    private int duration;
    private String businessId;

    /**
     * Constructor vacío requerido por Firebase Firestore.
     */
    public Service() {
    }

    /**
     * Constructor completo para inicializar un servicio.
     *
     * @param name       Nombre del servicio (ej. Corte de cabello, Masaje, etc.).
     * @param price      Precio del servicio en moneda local.
     * @param duration   Duración del servicio en minutos.
     * @param businessId ID del negocio que ofrece este servicio.
     */
    public Service(String name, double price, int duration, String businessId) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.businessId = businessId;
    }

    /**
     * @return Nombre del servicio.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del servicio.
     *
     * @param name Nombre del servicio.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Precio del servicio.
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

    /**
     * @return Duración del servicio en minutos.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Establece la duración del servicio.
     *
     * @param duration Duración en minutos.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return ID del negocio que ofrece este servicio.
     */
    public String getBusinessId() {
        return businessId;
    }

    /**
     * Establece el ID del negocio al que pertenece el servicio.
     *
     * @param businessId ID del negocio.
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}
