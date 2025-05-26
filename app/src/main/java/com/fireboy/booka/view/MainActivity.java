package com.fireboy.booka.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.AuthController;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FrameLayout[] bottomMenu;
    ImageView imgSettings;

    AuthController authController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authController = new AuthController(this);

        if (authController.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        UiExtensions.changeStatusBarColor(this, R.color.booka_primary);
        initComponents();

        bottomMenu[0].setOnClickListener(v -> loadFragment(new HomeFragment(), R.id.nav_home));

        bottomMenu[1].setOnClickListener(v -> loadFragment(new MyBookingFragment(), R.id.nav_bookmarks));

        bottomMenu[2].setOnClickListener(v -> loadFragment(new ProfileFragment(), R.id.nav_profile));

        imgSettings.setOnClickListener(v -> UiExtensions.navigateTo(this, SettingsActivity.class, false));
    }

    private void initComponents() {
        bottomMenu = new FrameLayout[]{
                findViewById(R.id.nav_home),
                findViewById(R.id.nav_bookmarks),
                findViewById(R.id.nav_profile)
        };
        imgSettings = findViewById(R.id.settingsIcon);

        loadFragment(new HomeFragment(), bottomMenu[0].getId());
    }

    private void loadFragment(Fragment fragment, int idMenu) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment).commit();

        setActiveOption(idMenu);
    }

    private void setActiveOption(int idActivo) {
        for (FrameLayout option : bottomMenu) {
            option.setSelected(false);
            if (option.getId() == idActivo) {
                option.setSelected(true);
            }
        }
    }
}