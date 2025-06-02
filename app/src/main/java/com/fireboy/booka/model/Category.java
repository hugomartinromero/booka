package com.fireboy.booka.model;

/**
 * Modelo que representa una categoría de negocio en la aplicación Booka.
 *
 * Las categorías permiten clasificar los negocios (por ejemplo: Belleza, Salud, Deporte, etc.)
 * y pueden activarse o desactivarse para su uso en la app.
 */
public class Category {
    private String id;
    private String name;

    /**
     * Constructor vacío requerido por Firebase Firestore.
     */
    public Category() {
    }

    /**
     * Constructor completo para inicializar una categoría.
     *
     * @param id     ID único del documento.
     * @param name   Nombre de la categoría.
     */
    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return ID del documento de la categoría en Firestore.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el ID de la categoría.
     *
     * @param id ID del documento en Firestore.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Nombre de la categoría (ej. "Belleza y estética").
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre de la categoría.
     *
     * @param name Nombre descriptivo de la categoría.
     */
    public void setName(String name) {
        this.name = name;
    }
}
