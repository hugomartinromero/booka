package com.fireboy.booka.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;


public class SettingsUtils {
    private static SharedPreferences getPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean isDarkModeEnabled(Context context) {
        return getPrefs(context).getBoolean("dark_mode", false);
    }

    public static void setDarkMode(Context context, boolean enabled) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("dark_mode", enabled);
        editor.apply();
        AppCompatDelegate.setDefaultNightMode(enabled ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
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