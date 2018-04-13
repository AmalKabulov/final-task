package by.epam.processor.util;

import by.epam.entity.BaseEntity;
import by.epam.processor.EntityCache;
import by.epam.processor.MetaCache;
import by.epam.processor.annotation.Column;
import by.epam.processor.annotation.Entity;
import by.epam.processor.annotation.Id;
import by.epam.processor.annotation.Table;
import by.epam.processor.meta.EntityMeta;
import by.epam.processor.meta.FieldMeta;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheProcessor {

    private static final CacheProcessor INSTANCE = new CacheProcessor();

    private MetaCache<Class<?>, EntityMeta> entitiesMetaCache;
    private EntityCache entitiesCache;

    public void initCache(final long timeToLive, final long idleInterval, final int maxItems) {
        this.entitiesMetaCache = new MetaCache<>(fillEntitiesMetaCache("by.epam.entity"));
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


    private Map<Class<?>, EntityMeta> fillEntitiesMetaCache(final String pkgName) {

        Map<Class<?>, EntityMeta> entitiesMeta = new HashMap<>();
        List<Class<?>> entities = AnnotationProcessor.getClassesByAnnotation(Entity.class, pkgName);

        for (Class<?> clazz : entities) {
            EntityMeta entityMeta = new EntityMeta();

            Table table = clazz.getAnnotation(Table.class);
            String tableName = table.name();
            entityMeta.setTableName(tableName);

            Field[] classFields = clazz.getDeclaredFields();
            for (Field field : classFields) {
                FieldMeta fieldMeta = new FieldMeta();

                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    String columnName = column.name();

                    fieldMeta.setMappedColumn(columnName);
                    fieldMeta.setFieldName(field.getName());
                    fieldMeta.setFieldType(field.getType());

                    if (field.isAnnotationPresent(Id.class)) {
                        entityMeta.setIdColumnFieldName(field.getName());
                        entityMeta.setIdColumnName(columnName);
                    }
                }

                entityMeta.getFieldMetas().put(fieldMeta.getFieldName(), fieldMeta);
            }

            entitiesMeta.put(clazz, entityMeta);
        }
        return entitiesMeta;

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
