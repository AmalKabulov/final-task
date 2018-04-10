package by.epam.processor;

import by.epam.processor.meta.EntityMeta;
import by.epam.processor.util.CacheProcessor;

import java.util.HashMap;
import java.util.Map;

public class Cache {


    public static final Map<Class<?>, Map<Object, Object>> ENTITIES_CACHE = new HashMap<>();

    public static final Map<Class<?>, EntityMeta> ENTITY_META_DATA_CACHE;

    public static final Map<String, Map<String, String>> REPOSITORIES_META;



    //TODO инициализацию в конструкторе или в статическом блоке лучше?
    static {
        ENTITY_META_DATA_CACHE = CacheProcessor.getEntitiesMeta("by.epam.entity");
        REPOSITORIES_META = CacheProcessor.getRepositoriesMeta("by.epam.dao");
    }


}
