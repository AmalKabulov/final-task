package by.epam.meta;

public class FieldMeta {
    private String fieldName;
    private String mappedColumn;
    private Class<?> fieldType;


    public FieldMeta() {
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMappedColumn() {
        return mappedColumn;
    }

    public void setMappedColumn(String mappedColumn) {
        this.mappedColumn = mappedColumn;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }
}
