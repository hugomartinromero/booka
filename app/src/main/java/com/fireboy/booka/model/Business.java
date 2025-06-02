package com.fireboy.booka.model;

import java.util.List;
import java.util.Map;

/**
 * Modelo que representa un negocio disponible en la aplicación Booka.
 *
 * Contiene información como nombre, categoría, dirección, imagen, propietario,
 * horario de atención, calificación promedio y servicios ofrecidos.
 */
public class Business {
    private String id;
    private String name;
    private String category;
    private String address;
    private String img;
    private String owner;
    private Map<String, DaySchedule> schedule;
    private float rating;
    private List<Service> services;

    /**
     * Constructor vacío requerido por Firebase Firestore.
     */
    public Business() {
    }

    /**
     * Constructor completo para inicializar todos los campos del negocio.
     *
     * @param id        ID del negocio.
     * @param name      Nombre del negocio.
     * @param category  Categoría a la que pertenece.
     * @param address   Dirección física del negocio.
     * @param img       URL o referencia de la imagen del negocio.
     * @param owner     UID del propietario del negocio.
     * @param schedule  Mapa de horarios por día (clave: día, valor: horario).
     * @param rating    Calificación promedio del negocio.
     * @param services  Lista de servicios ofrecidos por el negocio.
     */
    public Business(String id, String name, String category, String address, String img, String owner,
                    Map<String, DaySchedule> schedule, float rating, List<Service> services) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.address = address;
        this.img = img;
        this.owner = owner;
        this.schedule = schedule;
        this.rating = rating;
        this.services = services;
    }

    /**
     * @return ID del negocio.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el ID del negocio.
     * @param id ID a asignar.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Nombre del negocio.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del negocio.
     * @param name Nombre a asignar.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Categoría del negocio.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Establece la categoría del negocio.
     * @param category Categoría a asignar.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return Dirección del negocio.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Establece la dirección del negocio.
     * @param address Dirección a asignar.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return URL o referencia de imagen del negocio.
     */
    public String getImg() {
        return img;
    }

    /**
     * Establece la imagen del negocio.
     * @param img URL o recurso a asignar.
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * @return UID del propietario del negocio.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Establece el UID del propietario.
     * @param owner UID del dueño.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return Horarios de atención del negocio, organizados por día.
     */
    public Map<String, DaySchedule> getSchedule() {
        return schedule;
    }

    /**
     * Establece el horario semanal del negocio.
     * @param schedule Mapa de días y horarios.
     */
    public void setSchedule(Map<String, DaySchedule> schedule) {
        this.schedule = schedule;
    }

    /**
     * @return Calificación promedio del negocio.
     */
    public float getRating() {
        return rating;
    }

    /**
     * Establece la calificación promedio.
     * @param rating Valor entre 0 y 5.
     */
    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * @return Lista de servicios que ofrece el negocio.
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     * Establece los servicios ofrecidos.
     * @param services Lista de servicios.
     */
    public void setServices(List<Service> services) {
        this.services = services;
    }
}
