package by.epam.processor;

import by.epam.entity.BaseEntity;
import by.epam.processor.cache.EntityCache;
import by.epam.processor.cache.MetaCache;
import by.epam.processor.meta.EntityMeta;

import java.io.Serializable;
import java.util.List;

public class CacheProcessor {

    private static final CacheProcessor INSTANCE = new CacheProcessor();

    private MetaCache<Class<?>, EntityMeta> entitiesMetaCache;
    private EntityCache entitiesCache;

    public void initCache(final long timeToLive, final long idleInterval, final int maxItems) {
        this.entitiesMetaCache = new MetaCache<>(MetaProcessor.collectMeta("by.epam.entity"));
        this.entitiesCache = new EntityCache(timeToLive, idleInterval, maxItems);
    }


    public EntityMeta getMeta(final Class<?> entityClass) {
        return entitiesMetaCache.get(entityClass);
    }

    public <T extends BaseEntity> T getEntity(final Class<T> entityClass, final Serializable id) {
        T baseEntity = entitiesCache.get(id, entityClass);
        return baseEntity;
    }

    public void putEntity(final BaseEntity entity) {
        entitiesCache.put(entity);
    }

    public void deleteEntity(final Class<? extends BaseEntity> entityClass, final Serializable id) {
        entitiesCache.remove(id, entityClass);
    }


    public List<? extends BaseEntity> getEntitiesByClass(final Class<? extends BaseEntity> entityClass) {
        return entitiesCache.getByClass(entityClass);
    }


    public void putAll(final List<? extends BaseEntity> entities) {
        for (BaseEntity entity : entities) {
            entitiesCache.put(entity);
        }
    }



    public static CacheProcessor getInstance() {
        return INSTANCE;
    }

    //    /**
//     * First map parameter is className marked as @Repository
//     * Second is map which contains methodName and query
//     * returns repository meta data
//     */
//    public static Map<String, Map<String, String>> getRepositoriesMeta(final String pkgName) {
//
//        Map<String, Map<String, String>> repositoriesMeta = new HashMap<>();
//        List<Class<?>> repositories = AnnotationProcessor.getClassesByAnnotation(Repository.class, pkgName);
//
//        for (Class<?> repository : repositories) {
//            Method[] methods = repository.getDeclaredMethods();
//            Map<String, String> methodsMeta = new HashMap<>();
//
//            for (Method method : methods) {
//                if (method.isAnnotationPresent(Query.class)) {
//                    Query query = method.getAnnotation(Query.class);
//                    methodsMeta.put(method.getName(), query.value());
//                }
//            }
//
//            repositoriesMeta.put(repository.getName(), methodsMeta);
//        }
//
//        return repositoriesMeta;
//    }


}
