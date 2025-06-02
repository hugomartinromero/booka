package com.fireboy.booka.utils;

import com.google.firebase.Timestamp;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtils {

    private static final DecimalFormatSymbols SPANISH_SYMBOLS = new DecimalFormatSymbols(Locale.getDefault());
    private static final DecimalFormat DECIMAL_FORMAT;

    static {
        SPANISH_SYMBOLS.setDecimalSeparator(',');
        DECIMAL_FORMAT = new DecimalFormat("#0.00", SPANISH_SYMBOLS);
    }

    public static String formatDouble(double value) {
        return DECIMAL_FORMAT.format(value);
    }

    public static String formatFirebaseTimestamp(Timestamp timestamp) {
        if (timestamp == null) return "";
        Date date = timestamp.toDate();
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date);
    }
}
