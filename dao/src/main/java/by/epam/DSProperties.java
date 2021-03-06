package by.epam;

public final class DSProperties {

    public static final String URL;
    public static final String DRIVER;
    public static final String USERNAME;
    public static final String PASSWORD;
    public static final Integer MAX_POOL_SIZE;


    static {
       URL = DSResourceManager.getValue("db.url");
       DRIVER = DSResourceManager.getValue("db.driver");
       USERNAME = DSResourceManager.getValue("db.username");
       PASSWORD = DSResourceManager.getValue("db.password");
       MAX_POOL_SIZE = Integer.valueOf(DSResourceManager.getValue("db.con.size"));
    }

    private DSProperties() {
    }
}
