package com.fireboy.booka.model;

/**
 * Modelo que representa un usuario en la aplicación Booka.
 *
 * Puede ser un cliente o propietario de negocio, dependiendo del campo {@code role}.
 * Contiene información personal básica como email, nombre de usuario y foto de perfil.
 */
public class User {

    private String email;
    private String username;
    private String role;
    private String photo;

    /**
     * Constructor vacío requerido por Firebase Firestore.
     */
    public User() {
    }

    /**
     * Constructor completo para inicializar un usuario.
     *
     * @param email    Correo electrónico del usuario.
     * @param username Nombre de usuario visible en la app.
     * @param role     Rol del usuario (ej. "user" o "owner").
     * @param img      URL de la foto de perfil.
     */
    public User(String email, String username, String role, String img) {
        this.email = email;
        this.username = username;
        this.role = role;
        this.photo = img;
    }

    /**
     * @return Correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param email Dirección de correo válida.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return Nombre de usuario personalizado.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param username Texto que se muestra en el perfil y comentarios.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return Rol del usuario (por ejemplo: "user", "owner").
     */
    public String getRole() {
        return role;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param role Rol que define el tipo de cuenta.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return URL o referencia de la foto de perfil.
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Establece la foto de perfil.
     *
     * @param photo URL de imagen o recurso.
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
