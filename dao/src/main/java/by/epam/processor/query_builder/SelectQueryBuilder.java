package by.epam.processor.query_builder;

import by.epam.dao.Assert;
import by.epam.dao.exception.DaoException;
import by.epam.entity.BaseEntity;
import by.epam.processor.CacheProcessor;
import by.epam.processor.annotation.FetchType;
import by.epam.processor.annotation.JoinTable;
import by.epam.processor.annotation.ManyToMany;
import by.epam.processor.exception.MappingIncorrectException;
import by.epam.processor.meta.EntityMeta;
import by.epam.processor.meta.FieldMeta;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SelectQueryBuilder extends QueryBuilder {
    private static final CacheProcessor CACHE_PROCESSOR = CacheProcessor.getInstance();

    public static String findAllQuery(final Class<? extends BaseEntity> clazz) throws DaoException {

        EntityMeta entityMeta = CACHE_PROCESSOR.getMeta(clazz);
        Assert.notNull(entityMeta, "entity " + clazz + " not found");
        Collection<FieldMeta> fieldMetas = entityMeta.getFieldMetas().values();


        for (FieldMeta fieldMeta : fieldMetas) {
            Map<Class<? extends Annotation>, Annotation> annotations = fieldMeta.getAnnotations();
            if (annotations.containsKey(ManyToMany.class)) {

            }
        }

        return null;
    }


    private String manyToManyJoinQuery(final EntityMeta entityMeta, final FieldMeta fieldMeta) {
        Map<Class<? extends Annotation>, Annotation> fieldAnnotations = fieldMeta.getAnnotations();
        ManyToMany manyToMany = (ManyToMany) fieldAnnotations.get(ManyToMany.class);
        JoinTable joinTable = null;

        if (manyToMany.fetch().equals(FetchType.EAGER)) {

            String mappedBy = manyToMany.mappedBy();

            if (mappedBy.length() > 0) {
                Class<?> fieldGenericType = fieldMeta.getFieldGenericType();
                EntityMeta meta = CACHE_PROCESSOR.getMeta(fieldGenericType);
                Collection<FieldMeta> fieldValues = meta.getFieldMetas().values();
                FieldMeta field = fieldValues
                        .stream()
                        .filter(f -> f.getFieldName().equals(mappedBy)).findAny()
                        .orElseThrow(() -> new MappingIncorrectException("Mapped field: " + mappedBy + " not found"));

                joinTable = (JoinTable) field.getAnnotations().get(JoinTable.class);

            } else {
                joinTable = (JoinTable) fieldAnnotations.get(JoinTable.class);
            }
        }

        return null;
    }


    private String getColumns(List<FieldMeta> fieldMetas) {
        List<String> columnsList = fieldMetas
                .stream()
                .filter(field -> !Objects.equals(field.getColumnName(), null))
                .map(FieldMeta::getColumnName)
                .collect(Collectors.toList());

        return String.join(", ", columnsList);

    }
}
