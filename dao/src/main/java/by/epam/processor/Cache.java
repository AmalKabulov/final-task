package by.epam.processor;

import by.epam.dao.Assert;
import by.epam.dao.exception.DaoException;
import by.epam.processor.annotation.Column;
import by.epam.processor.annotation.Entity;
import by.epam.processor.annotation.Id;
import by.epam.processor.annotation.Table;
import by.epam.processor.meta.EntityMeta;
import by.epam.processor.meta.FieldMeta;
import by.epam.processor.util.AnnotationProcessor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache {


    private Map<Class<?>, Map<Object, Object>> entites = new HashMap<>();
    private Map<Class<?>, EntityMeta> entitiesMeta;
    private Map<String, Map<String, String>> REPOSITORIES_META;


    //TODO инициализацию в конструкторе или в статическом блоке лучше?
//    static {
//        ENTITY_META_DATA_CACHE = CacheProcessor.fillEntitiesMetaCache("by.epam.entity");
//        REPOSITORIES_META = CacheProcessor.getRepositoriesMeta("by.epam.dao");
//    }


    public void warm() {
        fillEntitiesMetaCache("by.epam.entity");
//        REPOSITORIES_META = CacheProcessor.getRepositoriesMeta("by.epam.dao");
    }


    public EntityMeta findEntityMeta(Class<?> clazz) throws DaoException {
        EntityMeta entityMeta = entitiesMeta.get(clazz);
        Assert.notNull(entityMeta, "entity: " + clazz + " not found");
        return entityMeta;
    }


    private void fillEntitiesMetaCache(final String pkgName) {

//        Map<Class<?>, EntityMeta> entitiesMeta = new HashMap<>();
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

    }


}
