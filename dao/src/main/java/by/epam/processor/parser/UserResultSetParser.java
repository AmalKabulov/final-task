package by.epam.processor.parser;

import by.epam.entity.User;
import by.epam.processor.meta.EntityMeta;
import by.epam.processor.CacheProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultSetParser implements ResultSetParser {

    private final String idColumn;
    private final String emailColumn;
    private final String passwordColumn;


    //TODO - можно ли в конструкторе логику писать
    public UserResultSetParser() {
        EntityMeta USER_META = CacheProcessor.getInstance().getMeta(User.class);
        idColumn = USER_META.getIdColumnName();
        emailColumn = USER_META.getFieldMetas().get("email").getColumnName();
        passwordColumn = USER_META.getFieldMetas().get("password").getColumnName();
    }

    @Override
    public User parse(final ResultSet resultSet, final String tableAlias) throws SQLException {

        if (tableAlias != null && tableAlias.length() > 0) {
            String idCol = new StringBuilder(tableAlias).append(".").append(idColumn).toString();
            String emailCol = new StringBuilder(tableAlias).append(".").append(emailColumn).toString();
            String passwordCol = new StringBuilder(tableAlias).append(".").append(passwordColumn).toString();

            return parse(resultSet, idCol, emailCol, passwordCol);
        }

        return parse(resultSet, idColumn, emailColumn, passwordColumn);

    }


    private User parse(final ResultSet resultSet, final String idCol, final String emailCol, final String passwordCol) throws SQLException {
        User user = new User();

        user.setId(resultSet.getLong(idCol));
        user.setEmail(resultSet.getString(emailCol));
        user.setPassword(resultSet.getString(passwordCol));

        return user;
    }


}
