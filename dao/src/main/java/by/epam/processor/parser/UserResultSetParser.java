package by.epam.processor.parser;

import by.epam.entity.BaseEntity;
import by.epam.entity.Role;
import by.epam.entity.User;
import by.epam.processor.annotation.ManyToMany;
import by.epam.processor.annotation.ManyToOne;
import by.epam.processor.annotation.OneToMany;
import by.epam.processor.annotation.OneToOne;
import by.epam.processor.meta.EntityMeta;
import by.epam.processor.CacheProcessor;
import by.epam.processor.meta.FieldMeta;

import java.lang.annotation.Annotation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserResultSetParser implements ResultSetParser {

    private final ResultSetParserManager parserManager = ResultSetParserManager.getInstance();
    private final EntityMeta USER_META = CacheProcessor.getInstance().getMeta(User.class);
    private final Collection<FieldMeta> fieldMetas = USER_META.getFieldMetas().values();

    private final String idColumn;
    private final String emailColumn;
    private final String passwordColumn;

    public UserResultSetParser() {
        String tableName = USER_META.getTableName();
        idColumn = tableName + "." + USER_META.getIdColumnName();
        emailColumn = tableName + "." +USER_META.getFieldMetas().get("email").getColumnName();
        passwordColumn = tableName + "." +USER_META.getFieldMetas().get("password").getColumnName();
    }

    @Override
    public User parse(final ResultSet resultSet/*, final String tableAlias*/) throws SQLException {


        User user = new User();

        user.setId(resultSet.getLong(idColumn));
        user.setEmail(resultSet.getString(emailColumn));
        user.setPassword(resultSet.getString(passwordColumn));

//        System.out.println(resultSet.getObject(idColumn), USER_META.getFieldMetas().get("id").getFieldType());

        return user;

//        if (tableAlias != null && tableAlias.length() > 0) {
//            String idCol = tableAlias + "." + idColumn;
//            String emailCol = tableAlias + "." + emailColumn;
//            String passwordCol = tableAlias + "." + passwordColumn;
//            return parseSimple(resultSet, idCol, emailCol, passwordCol);
//        }

//        return parseSimple(resultSet, idColumn, emailColumn, passwordColumn);
    }


//    private User parseSimple(final ResultSet resultSet, final String idCol, final String emailCol, final String passwordCol) throws SQLException {
//        User user = new User();
//
//        user.setId(resultSet.getLong(idCol));
//        user.setEmail(resultSet.getString(emailCol));
//        user.setPassword(resultSet.getString(passwordCol));
//
//        return user;
//    }


    @SuppressWarnings("unchecked")
    public List<User> complexParse(final ResultSet resultSet) throws SQLException {

        List<User> users = new ArrayList<>();
        String tableName = USER_META.getTableName();
        String idCol = tableName + "." + idColumn;
        String emailCol = tableName + "." + emailColumn;
        String passwordCol = tableName + "." + passwordColumn;

        User user = new User();

        while (resultSet.next()) {
            long id = resultSet.getLong(idCol);
            if (id != user.getId()) {
                user = new User();
            }
            user.setId(id);
            user.setEmail(resultSet.getString(emailCol));
            user.setPassword(resultSet.getString(passwordCol));

            for (FieldMeta fieldMeta : fieldMetas) {
                Map<Class<? extends Annotation>, Annotation> annotations = fieldMeta.getAnnotations();
                if (annotations.containsKey(ManyToMany.class) || annotations.containsKey(OneToMany.class)) {
                    BaseEntity role = parserManager.parse((Class<? extends BaseEntity>) fieldMeta.getFieldGenericType(), resultSet);
                    user.getRoles().add((Role) role);
                } /*else if (annotations.containsKey(ManyToOne.class) || annotations.containsKey(OneToOne.class)) {
                    parserManager.parseSimple((Class<? extends BaseEntity>) fieldMeta.getFieldType(), resultSet);
                }*/
            }


        }

        return null;
    }


}
