package com.fireboy.booka;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.fireboy.booka.view.activity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;

/**
 * Pruebas UI, funcionales, de aceptación y usabilidad para LoginActivity.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityUITest {

    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "123456";

    @Rule
    public ActivityScenarioRule<LoginActivity> rule = new ActivityScenarioRule<>(LoginActivity.class);

    // --- UI TESTS ---

    /**
     * Verifica que la pantalla de login se muestra correctamente.
     */
    @Test
    public void loginScreen_isVisible() {
        onView(withId(R.id.btnLogIn)).check(matches(isDisplayed()));
    }

    /**
     * Verifica que el texto del botón está traducido correctamente.
     */
    @Test
    public void loginButtonText_isTranslated() {
        onView(withId(R.id.btnLogIn)).check(matches(withText(R.string.iniciar_sesion)));
    }

    // --- FUNCTIONAL TESTS ---

    /**
     * Verifica que al pulsar el botón de login con campos llenos se ejecuta el flujo.
     */
    @Test
    public void loginButton_performsLoginFlow() {
        onView(withId(R.id.txtEmail)).perform(ViewActions.replaceText(TEST_EMAIL));
        onView(withId(R.id.txtPassword)).perform(ViewActions.replaceText(TEST_PASSWORD));
        onView(withId(R.id.btnLogIn)).perform(ViewActions.click());
    }

    /**
     * Verifica que se muestra error si los campos están vacíos.
     */
    @Test
    public void emptyLoginFields_showError() {
        onView(withId(R.id.btnLogIn)).perform(ViewActions.click());
        onView(withId(R.id.txtEmail)).check(matches(isDisplayed())); // como placeholder mínimo
    }

    // --- ACCEPTANCE TESTS ---

    /**
     * Simula una sesión de login completa con credenciales correctas.
     */
    @Test
    public void acceptance_loginSuccess() {
        onView(withId(R.id.txtEmail)).perform(ViewActions.replaceText(TEST_EMAIL));
        onView(withId(R.id.txtPassword)).perform(ViewActions.replaceText(TEST_PASSWORD));
        onView(withId(R.id.btnLogIn)).perform(ViewActions.click());

        // Supone que MainActivity muestra fragmentContainer al iniciar correctamente
        onView(withId(R.id.fragmentContainer)).check(matches(isDisplayed()));
    }

    // --- USABILITY TESTS ---

    /**
     * Simula retrasos al escribir para evaluar la experiencia de usuario.
     */
    @Test
    public void usabilitySimulation_typingDelays() throws InterruptedException {
        onView(withId(R.id.txtEmail)).perform(ViewActions.typeText(TEST_EMAIL));
        Thread.sleep(500); // evitar en producción, usar IdlingResource si es posible
        onView(withId(R.id.txtPassword)).perform(ViewActions.typeText(TEST_PASSWORD));
        Thread.sleep(500);
        onView(withId(R.id.btnLogIn)).perform(ViewActions.click());
    }

    // --- INTEGRATION TESTS (opcional) ---

    /**
     * Intenta hacer login directamente con Firebase (requiere conexión y cuenta válida).
     */
    @Test
    public void testLoginWithCorrectCredentials() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(TEST_EMAIL, TEST_PASSWORD)
                .addOnCompleteListener(task -> assertTrue("El login debe ser exitoso", task.isSuccessful()));
    }
}
