package com.fireboy.booka.utils;

import com.google.firebase.Timestamp;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utilidades para formateo de datos como números y fechas en la aplicación Booka.
 *
 * Esta clase incluye métodos estáticos para dar formato a valores decimales
 * y convertir marcas de tiempo de Firebase en fechas legibles.
 */
public class FormatUtils {

    /** Símbolos de formato para decimales en español (coma como separador). */
    private static final DecimalFormatSymbols SPANISH_SYMBOLS = new DecimalFormatSymbols(Locale.getDefault());

    /** Formato decimal con 2 decimales, usando coma como separador decimal. */
    private static final DecimalFormat DECIMAL_FORMAT;

    static {
        SPANISH_SYMBOLS.setDecimalSeparator(',');
        DECIMAL_FORMAT = new DecimalFormat("#0.00", SPANISH_SYMBOLS);
    }

    /**
     * Formatea un número decimal a texto con 2 decimales y coma como separador.
     *
     * @param value Valor decimal a formatear.
     * @return Cadena con formato "#0,00" en idioma local.
     */
    public static String formatDouble(double value) {
        return DECIMAL_FORMAT.format(value);
    }

    /**
     * Convierte un {@link Timestamp} de Firebase a una fecha en formato legible.
     *
     * @param timestamp Marca de tiempo de Firebase.
     * @return Fecha formateada como "dd/MM/yyyy" o cadena vacía si es {@code null}.
     */
    public static String formatFirebaseTimestamp(Timestamp timestamp) {
        if (timestamp == null) return "";
        Date date = timestamp.toDate();
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date);
    }
}
