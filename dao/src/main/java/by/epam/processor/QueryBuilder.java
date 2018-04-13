package by.epam.processor;

import by.epam.processor.annotation.Column;
import by.epam.processor.annotation.Id;
import by.epam.processor.annotation.Table;
import by.epam.dao.exception.DaoException;
import by.epam.processor.meta.EntityMeta;
import by.epam.processor.meta.FieldMeta;
import by.epam.dao.Assert;
import by.epam.processor.util.CacheProcessor;
import by.epam.processor.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryBuilder {

    private static final CacheProcessor CACHE_PROCESSOR = CacheProcessor.getInstance();

    public static String findAllQuery(Class<?> clazz) throws DaoException {

        EntityMeta entityMeta = CACHE_PROCESSOR.getMeta(clazz);
        Assert.notNull(entityMeta, "entity " + clazz + " not found");

        String tableName = entityMeta.getTableName();
        String columns = getColumns(entityMeta);

        return new StringBuilder("select ").append(columns).append(" from ").append(tableName).append(";").toString();

    }


    public static String findByIdQuery(Class<?> clazz, Object id) throws DaoException {

        EntityMeta entityMeta = CACHE_PROCESSOR.getMeta(clazz);
        Assert.notNull(entityMeta, "entity " + clazz + " not found");

        String tableName = entityMeta.getTableName();
        String idColumnName = entityMeta.getIdColumnName();
        String columns = getColumns(entityMeta);


        return new StringBuilder("select ")
                .append(columns)
                .append(" from ")
                .append(tableName)
                .append(" where ")
                .append(idColumnName).append(" = ").append(id).append(";").toString();

    }

    public static String insertQuery(Object entity) {

        Class<?> entityClass = entity.getClass();
        Map<String, String> columnsValues = getColumnsValues(entity);

        String tableName = entityClass.getAnnotation(Table.class).name();
        String columns = String.join(", ", columnsValues.keySet());
        String values = String.join(", ", columnsValues.values());

        return new StringBuilder("insert into ")
                .append(tableName)
                .append(" (").append(columns)
                .append(") values (")
                .append(values).append(")")
                .append(";").toString();


    }

    public static String updateQuery(Object entity) throws DaoException {

        Class<?> entityClass = entity.getClass();
        Map<String, String> columnsValues = getColumnsValues(entity);
        String tableName = entityClass.getAnnotation(Table.class).name();
        EntityMeta entityMeta = CACHE_PROCESSOR.getMeta(entityClass);
        Assert.notNull(entityMeta, "Entity: " + entityClass + " not found");

        String idColumnFieldName = entityMeta.getIdColumnFieldName();
        String idColumnName = entityMeta.getIdColumnName();
        Object id = ReflectionUtil.invokeGetter(entity, idColumnFieldName);

        StringBuilder query = new StringBuilder("update ").append(tableName).append(" set ");
        List<String> updateValues = columnsValues.entrySet()
                .stream()
                .map(cv -> new StringBuilder(cv.getKey()).append(" = ").append(cv.getValue()).toString())
                .collect(Collectors.toList());

        String values = String.join(", ", updateValues);


        return query.append(values).append(" where ").append(idColumnName).append(" = ").append(id).append(";").toString();

    }


    public static String deleteQuery(Class<?> clazz, Object id) throws DaoException {
        EntityMeta entityMeta = CACHE_PROCESSOR.getMeta(clazz);
        Assert.notNull(entityMeta, "Entity: " + clazz + " not found");
        String tableName = entityMeta.getTableName();
        String idColumnName = entityMeta.getIdColumnName();

        return new StringBuilder("delete from ")
                .append(tableName).append(" where ")
                .append(idColumnName)
                .append(" = ").append(id).toString();
    }


    private static Map<String, String> getColumnsValues(Object entity) {

        Class<?> entityClass = entity.getClass();
        Map<String, String> columnsValues = new LinkedHashMap<>();
        Field[] declaredFields = entityClass.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Column.class)) {
                String columnName = field.getAnnotation(Column.class).name();
                if (!field.isAnnotationPresent(Id.class)) {
                    String columnValue = wrap(String.valueOf(ReflectionUtil.invokeGetter(entity, field.getName())));
                    columnsValues.put(columnName, columnValue);
                }
            }

        }

        return columnsValues;
    }


    private static String getColumns(EntityMeta entityMeta) {
        Collection<FieldMeta> values = entityMeta.getFieldMetas().values();
        List<String> allColumns = values.stream().map(FieldMeta::getMappedColumn).collect(Collectors.toList());
        return String.join(", ", allColumns);
    }


    private static String wrap(String value) {
        return "\'" + value + "\'";
    }



}
