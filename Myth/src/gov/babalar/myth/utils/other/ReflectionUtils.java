package gov.babalar.myth.utils.other;

import java.lang.reflect.Field;

public class ReflectionUtils {

    public static Object getField(Class<?> clazz, String fieldName, Object instance) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(instance);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void setField(Class<?> clazz, String fieldName, Object instance, Object args) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, args);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}