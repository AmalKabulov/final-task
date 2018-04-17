package by.epam.processor.parser;

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


    public BaseEntity parse(final Class<? extends BaseEntity> entityClass, final ResultSet resultSet, final String tableAlias) throws SQLException {
        return PARSERS.get(entityClass).parse(resultSet, tableAlias);
    }

    public BaseEntity parse(final Class<? extends BaseEntity> entityClass, final ResultSet resultSet) throws SQLException {
        return parse(entityClass, resultSet, null);
    }

    public static ResultSetParserManager getInstance() {
        return INSTANCE;
    }
}
