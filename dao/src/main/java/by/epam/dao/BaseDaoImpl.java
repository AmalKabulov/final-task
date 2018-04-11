package by.epam.dao;

import by.epam.processor.Cache;
import by.epam.processor.QueryBuilder;
import by.epam.processor.annotation.Repository;
import by.epam.processor.database.DSConnection;
import by.epam.processor.database.DefaultConnection;
import by.epam.entity.BaseEntity;
import by.epam.exception.DaoException;
import by.epam.processor.meta.EntityMeta;
import by.epam.processor.parser.ResultSetParserManager;
import by.epam.processor.util.Assert;
import by.epam.processor.util.ReflectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public abstract class BaseDaoImpl<T, E extends BaseEntity> implements BaseDao<T, E> {

    private ResultSetParserManager resultSetParserManager = ResultSetParserManager.getINSTANCE();

    public List<E> findAll() throws DaoException, SQLException {

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


    protected E findOne(T id) throws DaoException, SQLException {
        BaseEntity entity = null;

        DSConnection dsConnection = new DSConnection();
        DefaultConnection connection = dsConnection.getConnection();

        Class<?> aClass = ReflectionUtil.getGenericParameterClass(getClass(), 1);

        System.out.println("Class: " + aClass);

        String query = QueryBuilder.findByIdQuery(aClass, id);

        System.out.println(query);

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            entity = resultSetParserManager.parse(aClass, resultSet);
        }


        return (E) entity;
    }

    protected void delete(T id) {

    }

    protected E save(E entity) throws DaoException, SQLException {
        DSConnection dsConnection = new DSConnection();
        DefaultConnection connection = dsConnection.getConnection();

        String query = QueryBuilder.insertQuery(entity);

        System.out.println(query);

        EntityMeta entityMeta = Cache.ENTITY_META_DATA_CACHE.get(entity.getClass());
        Assert.notNull(entityMeta, "entity: " + entity + " not found");

        String idColumnFieldName = entityMeta.getIdColumnFieldName();
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        int result = preparedStatement.executeUpdate();
        Assert.notZero(result, "inserting entity: " + entity + " failed");

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        Assert.isTrue(generatedKeys.next(), "inserting entity: " + entity + " failed. No id obtained");

        Long id = generatedKeys.getLong(1);

        ReflectionUtil.invokeSetter(entity, idColumnFieldName, id);
        return entity;
    }

    protected E update(E entity) throws DaoException, SQLException {
        DSConnection dsConnection = new DSConnection();
        DefaultConnection connection = dsConnection.getConnection();
        String query = QueryBuilder.updateQuery(entity);
        System.out.println(query);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        int updatedRows = preparedStatement.executeUpdate();
        Assert.notZero(updatedRows, "updating entity: " + entity + " failed");
        return entity;
    }


    protected List<E> findByQuery(String query) throws SQLException, ClassNotFoundException {

        return null;
    }


}
