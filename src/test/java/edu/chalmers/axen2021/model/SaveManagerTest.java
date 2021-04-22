package edu.chalmers.axen2021.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * Test class for SaveManager class.
 * @author Malte Åkvist
 */
public class SaveManagerTest {
    private SaveManager saveManager = SaveManager.getInstance();

    /*@BeforeAll
    public static void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = SaveManager.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }*/

    @Test
    public void test() {

    }
}