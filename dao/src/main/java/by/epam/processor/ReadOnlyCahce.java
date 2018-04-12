package by.epam.processor;

import java.util.*;

public class ReadOnlyCahce<K, V> implements Cache1{

    private Map<K, V> values;

    public ReadOnlyCahce(Map<K, V> values) {
        Objects.requireNonNull(values);
        LinkedHashMap newMap = new LinkedHashMap<>(values);
        this.values = Collections.unmodifiableMap(newMap);
    }

    @Override
    public V put(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V get(Object key) {
        return values.get(key);
    }
}
