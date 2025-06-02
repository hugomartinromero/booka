package com.fireboy.booka.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.fireboy.booka.R;

/**
 * Clase de utilidades para acciones comunes en la interfaz de usuario de Booka.
 * Incluye navegación, mensajes rápidos y personalización de la barra de estado.
 */
public class UiExtensions {

    /**
     * Navega desde una actividad actual a otra especificada.
     *
     * @param activity    Actividad actual.
     * @param destination Clase de la actividad destino.
     * @param finish      Si {@code true}, finaliza la actividad actual después de navegar.
     */
    public static void navigateTo(Activity activity, Class<?> destination, boolean finish) {
        Intent intent = new Intent(activity, destination);
        activity.startActivity(intent);
        if (finish) {
            activity.finish();
        }
    }

    /**
     * Muestra un mensaje rápido en pantalla tipo Toast.
     *
     * @param activity Actividad actual.
     * @param message  Texto a mostrar.
     */
    public static void showToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Cambia el color de la barra de estado y ajusta la visibilidad de los iconos según el fondo.
     *
     * @param activity Actividad actual.
     * @param colorRes ID del recurso de color (por ejemplo, {@code R.color.booka_primary}).
     */
    public static void changeStatusBarColor(Activity activity, int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Window window = activity.getWindow();
            window.setStatusBarColor(ContextCompat.getColor(activity, colorRes));

            if (colorRes == R.color.booka_primary) {
                // Fondo oscuro → iconos claros
                View decor = window.getDecorView();
                decor.setSystemUiVisibility(0);
            } else {
                // Fondo claro → iconos oscuros
                WindowInsetsController insetsController = window.getInsetsController();
                if (insetsController != null) {
                    insetsController.setSystemBarsAppearance(
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    );
                }
            }
        }
    }
}
