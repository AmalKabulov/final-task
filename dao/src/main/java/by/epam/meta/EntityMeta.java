package by.epam.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityMeta {

    private String tableName;
    private String idColumnName;

//    List<FieldMeta> fieldMetas = new ArrayList<>();

    private Map<String, FieldMeta> fieldMetas= new HashMap<>();

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

//    public List<FieldMeta> getFieldMetas() {
//        return fieldMetas;
//    }
//
//    public void setFieldMetas(List<FieldMeta> fieldMetas) {
//        this.fieldMetas = fieldMetas;
//    }


    public Map<String, FieldMeta> getFieldMetas() {
        return fieldMetas;
    }

    public void setFieldMetas(Map<String, FieldMeta> fieldMetas) {
        this.fieldMetas = fieldMetas;
    }
}
