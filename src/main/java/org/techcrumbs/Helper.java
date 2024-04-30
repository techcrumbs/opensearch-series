package org.techcrumbs;

import java.util.List;
import java.util.Map;

public class Helper {

    public static boolean isEmptyString(String value) {
        return value == null || value.isEmpty();
    }

    public static <T> boolean isEmptyList(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <K, V> boolean isEmptyMap(Map<K, V> map) {
        return map == null || map.isEmpty();
    }
}
