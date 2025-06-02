package com.fireboy.booka.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * Utilidad para gestionar la configuración del usuario en la aplicación Booka.
 *
 * Se utiliza para controlar preferencias como modo oscuro, idioma, nombre de usuario, etc.,
 * usando {@link SharedPreferences}.
 */
public class SettingsUtils {

    /**
     * Obtiene la instancia de {@link SharedPreferences} por defecto.
     *
     * @param context Contexto de la aplicación o actividad.
     * @return Objeto SharedPreferences.
     */
    private static SharedPreferences getPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Guarda un valor booleano en SharedPreferences.
     *
     * @param context Contexto actual.
     * @param key     Clave bajo la que se guarda el valor.
     * @param value   Valor booleano a guardar.
     */
    private static void putBoolean(Context context, String key, boolean value) {
        getPrefs(context).edit().putBoolean(key, value).apply();
    }

    /**
     * Guarda una cadena de texto en SharedPreferences.
     *
     * @param context Contexto actual.
     * @param key     Clave bajo la que se guarda el valor.
     * @param value   Valor de tipo String a guardar.
     */
    private static void putString(Context context, String key, String value) {
        getPrefs(context).edit().putString(key, value).apply();
    }

    /**
     * Verifica si el modo oscuro está activado.
     *
     * @param context Contexto actual.
     * @return {@code true} si está activado, {@code false} si no.
     */
    public static boolean isDarkModeEnabled(Context context) {
        return getPrefs(context).getBoolean("dark_mode", false);
    }

    /**
     * Activa o desactiva el modo oscuro globalmente en la app.
     *
     * @param context Contexto actual.
     * @param enabled {@code true} para activar modo oscuro, {@code false} para desactivarlo.
     */
    public static void setDarkMode(Context context, boolean enabled) {
        putBoolean(context, "dark_mode", enabled);
        AppCompatDelegate.setDefaultNightMode(
                enabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
    }

    /**
     * Obtiene el idioma actual configurado por el usuario.
     *
     * @param context Contexto actual.
     * @return Código del idioma (por defecto "es").
     */
    public static String getCurrentLanguage(Context context) {
        return getPrefs(context).getString("language", "es");
    }

    /**
     * Establece el idioma seleccionado por el usuario.
     *
     * @param context  Contexto actual.
     * @param language Código del idioma (ej. "es", "en").
     */
    public static void setLanguage(Context context, String language) {
        putString(context, "language", language);
    }

    /**
     * Obtiene el nombre de usuario guardado localmente.
     *
     * @param context Contexto actual.
     * @return Nombre de usuario o "default_username" si no se ha definido.
     */
    public static String getUsername(Context context) {
        return getPrefs(context).getString("username", "default_username");
    }

    /**
     * Establece el nombre de usuario guardado localmente.
     *
     * @param context  Contexto actual.
     * @param username Nombre de usuario.
     */
    public static void setUsername(Context context, String username) {
        putString(context, "username", username);
    }

    /**
     * Elimina todas las configuraciones guardadas localmente.
     *
     * @param context Contexto actual.
     */
    public static void clearSettings(Context context) {
        getPrefs(context).edit().clear().apply();
    }
}
