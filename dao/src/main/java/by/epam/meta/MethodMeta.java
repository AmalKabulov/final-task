package by.epam.meta;

public class MethodMeta {

    private String methodName;
    private String query;

    public MethodMeta() {
    }

    public MethodMeta(String methodName, String query) {
        this.methodName = methodName;
        this.query = query;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
