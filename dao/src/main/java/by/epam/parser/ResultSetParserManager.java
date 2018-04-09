package by.epam.parser;

import by.epam.entity.BaseEntity;
import by.epam.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ResultSetParserManager {

    private static final ResultSetParserManager INSTANCE = new ResultSetParserManager();

    private static final Map<Class<?>, ResultSetParser> PARSERS = new HashMap<>();


    //TODO - почему когда переносишь в конструктор падает exception
    static {
        PARSERS.put(User.class, new UserResultSetParser());
    }

    private ResultSetParserManager() {

    }


    public BaseEntity parse(Class<?> entityClass, ResultSet resultSet) throws SQLException {
        return PARSERS.get(entityClass).parse(resultSet);
    }


    public static ResultSetParserManager getINSTANCE() {
        return INSTANCE;
    }
}
