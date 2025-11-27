package fr.mehdi.tool_management.filters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import fr.mehdi.tool_management.utils.UtilEntity;

public class FiltersApplied {
    
    private final Map<String, Object> filters = new HashMap<>();

    public FiltersApplied() {}

    public FiltersApplied(Map<String, Object> filters) {
        if (!UtilEntity.isEmpty(filters)) {
            this.filters.putAll(filters);
        }
    }

    /** VALUE **/

    public Object getValue(String key) {
        return filters.get(key);
    }

    /** KEY **/

    public boolean hasKey(String key) {
        return filters.containsKey(key);
    }

    /** ADD **/

    public void add(String key, Object value) {
        if (!UtilEntity.isEmpty(key) && value != null) {
            filters.put(key, value);
        }
    }

    /** GET ALL **/

    public Map<String, Object> getAll() {
        return Collections.unmodifiableMap(filters);
    }

    /** EMPTY **/

    public boolean isEmpty() {
        return filters.isEmpty();
    }

}
