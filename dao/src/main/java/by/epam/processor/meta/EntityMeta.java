package by.epam.processor.meta;

import java.util.*;

public class EntityMeta {

    private String tableName;
    private String idColumnName;
    private String idColumnFieldName;

//    List<FieldMeta> fieldMetas = new ArrayList<>();

    private Map<String, FieldMeta> fieldMetas = new LinkedHashMap<>();

    public EntityMeta() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIdColumnName() {
        return idColumnName;
    }

    public void setIdColumnName(String idColumnName) {
        this.idColumnName = idColumnName;
    }


    public Map<String, FieldMeta> getFieldMetas() {
        return fieldMetas;
    }

    public void setFieldMetas(Map<String, FieldMeta> fieldMetas) {
        this.fieldMetas = fieldMetas;
    }

    public String getIdColumnFieldName() {
        return idColumnFieldName;
    }

    public void setIdColumnFieldName(String idColumnFieldName) {
        this.idColumnFieldName = idColumnFieldName;
    }
}
