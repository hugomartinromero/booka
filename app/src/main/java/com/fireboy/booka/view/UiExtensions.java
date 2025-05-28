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
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UiExtensions {
    public static void navigateTo(Activity activity, Class<?> destination, boolean finish) {
        Intent intent = new Intent(activity, destination);
        activity.startActivity(intent);
        if (finish) {
            activity.finish();
        }
    }

    public static void showToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static void changeStatusBarColor(Activity activity, int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Window window = activity.getWindow();

            window.setStatusBarColor(ContextCompat.getColor(activity, colorRes));

            if (colorRes == R.color.booka_primary) {
                View decor = window.getDecorView();
                decor.setSystemUiVisibility(0);
            } else {
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
