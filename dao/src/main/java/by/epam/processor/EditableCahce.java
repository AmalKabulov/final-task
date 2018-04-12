package by.epam.processor;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class EditableCahce<K, V> implements Cache1 {


    private ConcurrentHashMap<K, V> values = new ConcurrentHashMap<>();

    public EditableCahce() {
    }

    public EditableCahce(ConcurrentHashMap<K, V> values) {
        this.values = values;
    }

    @Override
    public Object put(Object key, Object value) {


        return null;
    }

    @Override
    public Object get(Object key) {
        return null;
    }
}
