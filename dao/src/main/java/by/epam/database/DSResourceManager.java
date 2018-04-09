package by.epam.database;

import java.util.ResourceBundle;

public class DSResourceManager {

    private static final ResourceBundle resource = ResourceBundle.getBundle("database.properties");


    public DSResourceManager() {
    }


    public static String getValue(final String key) {

        return resource.getString(key);
    }
}
