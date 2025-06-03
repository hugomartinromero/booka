package com.fireboy.booka;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.fireboy.booka.utils.FormatUtils;

import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas unitarias y de rendimiento para {@link FormatUtils}.
 */
public class FormatUtilsTest {

    private double input;

    /**
     * Inicializa valores comunes antes de cada prueba.
     */
    @Before
    public void setup() {
        input = 4.5;
    }

    // --- UNIT TESTS ---

    /**
     * Verifica que un número con un decimal se formatea correctamente a 2 decimales con coma.
     */
    @Test
    public void formatDouble_correctFormat() {
        String formatted = FormatUtils.formatDouble(input);
        assertEquals("4,50", formatted);
    }

    /**
     * Verifica que un número con varios decimales se redondea correctamente.
     */
    @Test
    public void formatDouble_roundingTest() {
        String formatted = FormatUtils.formatDouble(12.349);
        assertEquals("12,35", formatted); // Esperamos redondeo hacia arriba
    }

    /**
     * Verifica que números enteros se formatean con dos decimales.
     */
    @Test
    public void formatDouble_integerInput() {
        String formatted = FormatUtils.formatDouble(7);
        assertEquals("7,00", formatted);
    }

    // --- PERFORMANCE TESTS ---

    /**
     * Mide el tiempo que tarda en formatear múltiples veces y asegura que es razonable.
     */
    @Test
    public void testPerformance_formatDouble() {
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            FormatUtils.formatDouble(12.3456);
        }
        long duration = System.nanoTime() - start;
        assertTrue("La operación es demasiado lenta (>10ms)", duration < 10_000_000);
    }
}
