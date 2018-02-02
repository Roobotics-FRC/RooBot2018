package org.usfirst.frc.team4373.robot.intrusive;

import java.lang.reflect.Field;

/**
 * Created by derros on 2/2/18.
 */
public class DangerousReflections {

    public static void setField(final String className, final String fieldName, final Object val)
            throws SecurityException, NoSuchFieldException, ClassNotFoundException, IllegalArgumentException,
            IllegalAccessException {
        // Get the private String field
        final Field field = Class.forName(className).getDeclaredField(fieldName);
        // Allow modification on the field
        field.setAccessible(true);
        // Get
        final Object oldValue = field.get(Class.forName(className));
        // Sets the field to the new value
        field.set(oldValue, val);
    }

    public static <T> T getField(final String className, final String fieldName)
            throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        final Field field = Class.forName(className).getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(Class.forName(className));
    }
}
