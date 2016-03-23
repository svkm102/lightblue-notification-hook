package org.esbtools.lightbluenotificationhook;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class EntityNotificationHookConfigurationReader {
    public EntityNotificationHookConfiguration readConfiguration(
            Class<?> notification) throws IllegalAccessException {
        for (Field field : notification.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) &&
                    EntityNotificationHookConfiguration.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);

                EntityNotificationHookConfiguration config =
                        (EntityNotificationHookConfiguration) field.get(null);

                if (config == null) {
                    continue;
                }

                return config;
            }
        }

        throw new IllegalArgumentException("No static field found with non-null instance of " +
                EntityNotificationHookConfiguration.class + " on class " + notification + " when " +
                "trying to generate notification hook configuration.");
    }
}