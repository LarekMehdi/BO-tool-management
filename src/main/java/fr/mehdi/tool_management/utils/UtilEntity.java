package fr.mehdi.tool_management.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;


public abstract class UtilEntity {

    /** EMPTY **/
    
    public static boolean isEmpty(Object obj) {
        if (obj == null) return true;

        if (obj instanceof String) {
            return ((String) obj).trim().isEmpty();
        }

        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }

        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }

        return false;
    }
}
