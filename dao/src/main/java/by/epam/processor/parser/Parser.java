package by.epam.processor.parser;

import by.epam.processor.CacheProcessor;
import by.epam.processor.meta.EntityMeta;
import by.epam.processor.meta.FieldMeta;
import by.epam.processor.util.ReflectionUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    private CacheProcessor cacheProcessor = CacheProcessor.getInstance();

    public Object parse(final Class<?> entityClass, final ResultSet resultSet, final boolean complex) throws SQLException {
        EntityMeta entityMeta = cacheProcessor.getMeta(entityClass);
        if (entityMeta == null) {
            return null;
        }


        String tableName = entityMeta.getTableName();
        String idColumnName = entityMeta.getIdColumnName();
        Collection<FieldMeta> fieldMetas = entityMeta.getFieldMetas().values();

        for (FieldMeta fieldMeta : fieldMetas) {
            Object entity = ReflectionUtil.newInstance(entityMeta.getEntityClassName());

            String columnName = fieldMeta.getColumnName();
            if (columnName != null) {
                columnName = tableName + "." + columnName;
                Object result = resultSet.getObject(columnName, fieldMeta.getFieldType());
                ReflectionUtil.invokeSetter(entity, fieldMeta.getFieldName(), result);
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
