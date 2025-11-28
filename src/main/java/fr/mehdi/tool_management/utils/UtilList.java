package fr.mehdi.tool_management.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.lang.reflect.Field;

public abstract class UtilList {

    /** COLLECT **/
    
    // collecte un attribut d'une liste
    public static <U, V> List<V> collect(List<U> list, Function<? super U, ? extends V> t) {
        if (list == null || t == null) return null;
        List<V> list1 = new ArrayList<>();
        for (U u : list)
            list1.add(u != null ? t.apply(u) : null);
        return list1;
    }

    // collect un attribut d'une liste, si il respecte la condition
    public static <U, V> List<V> collect(List<U> list, Function<? super U, ? extends V> t, Predicate<? super U> f) {
        if (list == null || t == null) return null;
        List<V> list1 = new ArrayList<>();
        for (U u : list)
            if (f != null && f.test(u)) list1.add(u != null ? t.apply(u) : null);
        return list1;
    }

    /** SORT **/

    public static <T> void sortByField(List<T> list, String fieldName, boolean asc) {
        if (list == null || list.isEmpty() || fieldName == null) return;

        Comparator<T> comparator = Comparator.comparing(item -> {
            try {
                Field field = item.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(item);
                return (Comparable) value;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // TODO: exception?
                return null;
            }
        }, Comparator.nullsLast(Comparator.naturalOrder()));

        if (!asc) comparator = comparator.reversed();
        list.sort(comparator);
    }

}
