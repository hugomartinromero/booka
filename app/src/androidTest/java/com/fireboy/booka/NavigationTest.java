package com.fireboy.booka;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.fireboy.booka.view.activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class NoAuthRedirectTest {

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<MainActivity>(MainActivity.class, true, false);

    @Before
    public void logoutBeforeTest() {
        FirebaseAuth.getInstance().signOut(); // Nos aseguramos de que no haya sesión
    }

    @Test
    public void shouldRedirectToLoginIfNotAuthenticated() {
        rule.launchActivity(new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class));

        // Verifica que LoginActivity se ha abierto (por ejemplo, con un botón específico)
        onView(withId(R.id.btnLogIn)).check(matches(isDisplayed()));
    }

    private class ActivityTestRule<M extends AppCompatActivity> {
        public ActivityTestRule(Class<MainActivity> mainActivityClass, boolean b, boolean b1) {
        }
    }
}
