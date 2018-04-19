package by.epam.processor.parser;

import by.epam.processor.CacheProcessor;
import by.epam.processor.annotation.ManyToMany;
import by.epam.processor.annotation.OneToMany;
import by.epam.processor.meta.EntityMeta;
import by.epam.processor.meta.FieldMeta;
import by.epam.processor.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Parser {

    private CacheProcessor cacheProcessor = CacheProcessor.getInstance();

    public Object parse(final Class<?> entityClass, final ResultSet resultSet, final boolean complex) throws SQLException {
        EntityMeta entityMeta = cacheProcessor.getMeta(entityClass);
        if (entityMeta == null) {
            return null;
        }


        String tableName = entityMeta.getTableName();
//        String idColumnName = entityMeta.getIdColumnName();
        Collection<FieldMeta> fieldMetas = entityMeta.getFieldMetas().values();

        for (FieldMeta fieldMeta : fieldMetas) {
            Object entity = ReflectionUtil.newInstance(entityMeta.getEntityClassName());

            String columnName = fieldMeta.getColumnName();
            if (columnName != null) {
                columnName = tableName + "." + columnName;
                Object result = resultSet.getObject(columnName, fieldMeta.getFieldType());
                ReflectionUtil.invokeSetter(entity, fieldMeta.getFieldName(), result);
            }

            if (fieldMeta.getAnnotations().containsKey(ManyToMany.class)
                    || fieldMeta.getAnnotations().containsKey(OneToMany.class)) {
                Class<?> joinEntityClass = fieldMeta.getFieldGenericType();
                EntityMeta joinEntityMeta = cacheProcessor.getMeta(joinEntityClass);
                String joinTableName = joinEntityMeta.getTableName();


                Collection<FieldMeta> joinFieldMetas = joinEntityMeta.getFieldMetas().values();
                Object joinEntity = ReflectionUtil.newInstance(entityMeta.getEntityClassName());
                for (FieldMeta joinFieldMeta : joinFieldMetas) {
                    if (columnName != null) {
                        columnName = joinTableName + "." + columnName;
                        Object joinResult = resultSet.getObject(columnName, joinFieldMeta.getFieldType());
                        ReflectionUtil.invokeSetter(joinEntity, joinFieldMeta.getFieldName(), joinResult);
                    }

                }
//                ///////**** Здесь вызывается геттер из оновной ентити ****\\\\\\\\\
//                Object collection = ReflectionUtil.invokeGetter(entity, fieldMeta.getFieldName());
//                Method collectionAddMethod = ReflectionUtil.getMethod(collection.getClass(), "add");
//                ReflectionUtil.invokeMethod(collection, collectionAddMethod, joinResult);
            }
        }

        return null;

    }


    public List<String> getColumnNames(final EntityMeta entityMeta) {
        String tableName = entityMeta.getTableName();
        return entityMeta.getFieldMetas().values()
                .stream()
                .filter(fieldMeta -> fieldMeta.getColumnName() != null)
                .map(fieldMeta -> tableName + "." + fieldMeta.getColumnName())
                .collect(Collectors.toList());
    }
}
