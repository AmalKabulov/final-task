package by.epam.processor.parser;

import by.epam.processor.Cache;
import by.epam.entity.User;
import by.epam.processor.meta.EntityMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class UserResultSetParser implements ResultSetParser {

    private EntityMeta USER_META = Cache.ENTITY_META_DATA_CACHE.get(User.class);


    private String idColumn;
    private String emailColumn;
    private String passwordColumn;


    //TODO - можно ли в конструкторе логику писать
    public UserResultSetParser() {

        idColumn = USER_META.getIdColumnName();
        emailColumn = USER_META.getFieldMetas().get("email").getMappedColumn();
        passwordColumn = USER_META.getFieldMetas().get("password").getMappedColumn();
    }

    @Override
    public User parse(final ResultSet resultSet) throws SQLException {


        if (Objects.equals(idColumn, null) || Objects.equals(emailColumn, null) || Objects.equals(passwordColumn, null)) {

//             EntityMeta USER_META = Cache.ENTITY_META_DATA_CACHE.get(User.class);

//            idColumn = USER_META.getIdColumnName();
//            emailColumn = USER_META.getFieldMetas().get("email").getMappedColumn();
//            passwordColumn = USER_META.getFieldMetas().get("password").getMappedColumn();

        }


        User user = new User();

        user.setId(resultSet.getLong(idColumn));
        user.setEmail(resultSet.getString(emailColumn));
        user.setPassword(resultSet.getString(passwordColumn));

        return user;
    }


}
