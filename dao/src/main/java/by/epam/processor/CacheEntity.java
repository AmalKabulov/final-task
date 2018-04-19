package by.epam.processor;

import by.epam.processor.meta.EntityMeta;
import by.epam.processor.util.ReflectionUtil;

import java.io.Serializable;

public class CacheEntity<E> {

    private CacheProcessor cacheProcessor = CacheProcessor.getInstance();

    private E entity;

    public CacheEntity(E entity) {
        this.entity = entity;
    }


    @SuppressWarnings("unchecked")
    public <T extends Serializable> T getPrimaryKey() {
        EntityMeta meta = cacheProcessor.getMeta(entity.getClass());
        return (T) ReflectionUtil.invokeGetter(entity, meta.getIdColumnFieldName());
    }

    public Class<?> getEntityClass() {
        return entity.getClass();
    }
}
