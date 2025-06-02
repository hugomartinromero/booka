package com.fireboy.booka.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;

public class SettingsUtils {

    private static SharedPreferences getPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static void putBoolean(Context context, String key, boolean value) {
        getPrefs(context).edit().putBoolean(key, value).apply();
    }

    private static void putString(Context context, String key, String value) {
        getPrefs(context).edit().putString(key, value).apply();
    }

    public static boolean isDarkModeEnabled(Context context) {
        return getPrefs(context).getBoolean("dark_mode", false);
    }

    public static void setDarkMode(Context context, boolean enabled) {
        putBoolean(context, "dark_mode", enabled);
        AppCompatDelegate.setDefaultNightMode(
                enabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
    }

    public static String getCurrentLanguage(Context context) {
        return getPrefs(context).getString("language", "es");
    }

    public static void setLanguage(Context context, String language) {
        putString(context, "language", language);
    }

    public static String getUsername(Context context) {
        return getPrefs(context).getString("username", "default_username");
    }

    public static void setUsername(Context context, String username) {
        putString(context, "username", username);
    }

    public static void clearSettings(Context context) {
        getPrefs(context).edit().clear().apply();
    }
}
