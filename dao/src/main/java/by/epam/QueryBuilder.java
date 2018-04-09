package by.epam;

import by.epam.exception.NoSuchEntityException;
import by.epam.meta.EntityMeta;
import by.epam.meta.FieldMeta;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class QueryBuilder {


    public static String findAllQuery(Class<?> clazz) throws NoSuchEntityException {

        EntityMeta entityMeta = Cache.ENTITY_META_DATA_CACHE.get(clazz);

        if (entityMeta == null) {
            throw new NoSuchEntityException("entity " + clazz + " not found");
        }

        String tableName = entityMeta.getTableName();

        Collection<FieldMeta> values = entityMeta.getFieldMetas().values();
        List<String> allColumns = values.stream().map(FieldMeta::getMappedColumn).collect(Collectors.toList());
        String columns = String.join(", ", allColumns);

        StringBuilder query = new StringBuilder("select ").append(columns).append(" from ").append(tableName);
        return query.toString();
    }


}
