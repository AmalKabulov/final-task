package by.epam.dao;

import by.epam.processor.Cache;
import by.epam.processor.QueryBuilder;
import by.epam.processor.annotation.Repository;
import by.epam.processor.database.DSConnector;
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

    public List<E> findAll() throws DaoException {

        List<E> objects = new ArrayList<>();
        DSConnector dsConnector = new DSConnector();
        DefaultConnection connection = dsConnector.getConnection();

        Class<?> entityClass = ReflectionUtil.getGenericParameterClass(getClass(), 1);
        System.out.println("Class: " + entityClass);
        String query = QueryBuilder.findAllQuery(entityClass);

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BaseEntity entity = resultSetParserManager.parse(entityClass, resultSet);
                objects.add((E) entity);
            }

            System.out.println("query: " + query);
            Assert.notEmpty(objects, "Nothing was found");
            return objects;
        } catch (SQLException e) {
            throw new DaoException("");
        }

    }


    public E findOne(T id) throws DaoException {
        BaseEntity entity = null;

        DSConnector dsConnector = new DSConnector();
        DefaultConnection connection = dsConnector.getConnection();

        Class<?> aClass = ReflectionUtil.getGenericParameterClass(getClass(), 1);

        System.out.println("Class: " + aClass);

        String query = QueryBuilder.findByIdQuery(aClass, id);

        System.out.println(query);

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                entity = resultSetParserManager.parse(aClass, resultSet);
            }
            return (E) entity;
        } catch (SQLException e) {
            throw new DaoException("");
        }


    }

    public void delete(T id) throws DaoException {

        DSConnector dsConnector = new DSConnector();
        DefaultConnection connection = dsConnector.getConnection();

        Class<?> entityClass = ReflectionUtil.getGenericParameterClass(getClass(), 1);
        String query = QueryBuilder.deleteQuery(entityClass, id);
        System.out.println(query);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int result = preparedStatement.executeUpdate();
            Assert.notZero(result, "Deleting entity: " + entityClass + " failed");
        } catch (SQLException e) {
            throw new DaoException("");
        }


    }

    public E save(E entity) throws DaoException {
        DSConnector dsConnector = new DSConnector();
        DefaultConnection connection = dsConnector.getConnection();

        String query = QueryBuilder.insertQuery(entity);

        System.out.println(query);

        EntityMeta entityMeta = Cache.ENTITY_META_DATA_CACHE.get(entity.getClass());
        Assert.notNull(entityMeta, "entity: " + entity + " not found");
        String idColumnFieldName = entityMeta.getIdColumnFieldName();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int result = preparedStatement.executeUpdate();
            Assert.notZero(result, "inserting entity: " + entity + " failed");
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            Assert.isTrue(generatedKeys.next(), "inserting entity: " + entity + " failed. No id obtained");
            //TODO может быть не лонг
            Long id = generatedKeys.getLong(1);
            ReflectionUtil.invokeSetter(entity, idColumnFieldName, id);
            return entity;

        } catch (SQLException e) {
            throw new DaoException("");
        }

    }

    public E update(E entity) throws DaoException {
        DSConnector dsConnector = new DSConnector();
        DefaultConnection connection = dsConnector.getConnection();
        String query = QueryBuilder.updateQuery(entity);
        System.out.println(query);
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(query);
            int updatedRows = preparedStatement.executeUpdate();
            Assert.notZero(updatedRows, "updating entity: " + entity + " failed");
            return entity;

        } catch (SQLException e) {

            throw new DaoException("");
        }


    }


    protected List<E> findByQuery(String query) {

        return null;
    }


}
