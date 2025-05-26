package com.fireboy.booka.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SettingsUtils {
    private static SharedPreferences getPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean isDarkModeEnabled(Context context) {
        return getPrefs(context).getBoolean("dark_mode", false);
    }

    public static void setDarkMode(Context context, boolean enabled) {
        getPrefs(context).edit().putBoolean("dark_mode", enabled).apply();
    }

    public static String getCurrentLanguage(Context context) {
        return getPrefs(context).getString("language", "es");
    }

    public static void setLanguage(Context context, String idioma) {
        getPrefs(context).edit().putString("language", idioma).apply();
    }

    public static String getUsername(Context context) {
        return getPrefs(context).getString("username", "default_username");
    }

    public static void setUsername(Context context, String username) {
        getPrefs(context).edit().putString("username", username).apply();
    }

    public static void clearSettings(Context context) {
        getPrefs(context).edit().clear().apply();
    }
}