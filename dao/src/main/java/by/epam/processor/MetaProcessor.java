package by.epam.processor;

import by.epam.processor.annotation.*;
import by.epam.processor.meta.EntityMeta;
import by.epam.processor.meta.FieldMeta;
import by.epam.processor.util.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetaProcessor {


    public static Map<Class<?>, EntityMeta> collectMeta(final String pkgName) {

        Map<Class<?>, EntityMeta> entitiesMeta = new HashMap<>();
        List<Class<?>> entities = AnnotationProcessor.getClassesByAnnotation(Entity.class, pkgName);

        for (Class<?> clazz : entities) {
            EntityMeta entityMeta = createEntityMeta(clazz);
            entitiesMeta.put(clazz, entityMeta);
        }
        return entitiesMeta;
    }


    private static EntityMeta createEntityMeta(final Class<?> clazz) {
        EntityMeta entityMeta = new EntityMeta();

        Table table = clazz.getAnnotation(Table.class);
        String tableName = table.name();
        entityMeta.setTableName(tableName);

        Field[] classFields = clazz.getDeclaredFields();

        for (Field field : classFields) {
            if (field.isAnnotationPresent(Id.class)) {
                Column column = field.getAnnotation(Column.class);
                entityMeta.setIdColumnFieldName(field.getName());
                entityMeta.setIdColumnName(column.name());
            }

            FieldMeta fieldMeta = createFieldMeta(field);
            entityMeta.getFieldMetas().put(fieldMeta.getFieldName(), fieldMeta);
        }

        return entityMeta;

    }


    private static FieldMeta createFieldMeta(final Field field) {
        FieldMeta fieldMeta = new FieldMeta();
        fieldMeta.setFieldName(field.getName());
        fieldMeta.setFieldType(field.getType());

        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            String columnName = column.name();
            fieldMeta.setColumnName(columnName);


        } else if (field.isAnnotationPresent(ManyToMany.class) || field.isAnnotationPresent(OneToMany.class)) {
            fieldMeta.setFieldGenericType(ReflectionUtil.getFieldGenericType(field));
        }

        fillFieldMetaWithAnnotations(field, fieldMeta);

        return fieldMeta;
    }


    private static void fillFieldMetaWithAnnotations(final Field field, final FieldMeta fieldMeta) {
        Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
        if (fieldAnnotations != null && fieldAnnotations.length > 0) {

            for (Annotation annotation : fieldAnnotations) {
                fieldMeta.getAnnotations().put(annotation.getClass(), annotation);
            }

        }
    }

}