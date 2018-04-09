package by.epam.dao;

import by.epam.QueryBuilder;
import by.epam.annotation.Repository;
import by.epam.database.DSConnection;
import by.epam.database.DefaultConnection;
import by.epam.entity.BaseEntity;
import by.epam.exception.NoSuchEntityException;
import by.epam.exception.QueryNotFoundException;
import by.epam.parser.ResultSetParserManager;
import by.epam.util.ReflectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public abstract class BaseDaoImpl<T, E extends BaseEntity> implements BaseDao<T, E> {

    private ResultSetParserManager resultSetParserManager = ResultSetParserManager.getINSTANCE();

    protected List<E> findAll() throws NoSuchEntityException, SQLException, ClassNotFoundException, QueryNotFoundException {

        List<E> objects = new ArrayList<>();
        DSConnection dsConnection = new DSConnection();
        DefaultConnection connection = dsConnection.getConnection();


        Class<?> aClass = ReflectionUtil.getGenericParameterClass(getClass(), 1);


        System.out.println("Class: " + aClass);
        String query = QueryBuilder.findAllQuery(aClass);

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();


        while (resultSet.next()) {
            BaseEntity entity = resultSetParserManager.parse(aClass, resultSet);
            objects.add((E) entity);
        }


        System.out.println("query: " + query);
        return objects;
    }

    protected E findOne(T id) {
        return null;
    }

    protected void delete(T id) {

    }

    protected E save(E entity) {

        return null;
    }

    protected E update(E entity) {
        return null;
    }


}
