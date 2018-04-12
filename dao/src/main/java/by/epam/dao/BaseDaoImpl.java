package by.epam.dao;

import by.epam.processor.CPException;
import by.epam.processor.Cache;
import by.epam.processor.QueryBuilder;
import by.epam.processor.annotation.Repository;
import by.epam.processor.database.DSConnector;
import by.epam.processor.database.DefaultConnection;
import by.epam.entity.BaseEntity;
import by.epam.dao.exception.DaoException;
import by.epam.processor.database.DefaultConnectionPool;
import by.epam.processor.meta.EntityMeta;
import by.epam.processor.parser.ResultSetParserManager;
import by.epam.processor.util.ReflectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public abstract class BaseDaoImpl<T, E extends BaseEntity> implements BaseDao<T, E> {

    private ResultSetParserManager resultSetParserManager = ResultSetParserManager.getINSTANCE();
    private DefaultConnectionPool connectionPool = DefaultConnectionPool.getInstance();

    public List<E> findAll() throws DaoException {

        List<E> objects = new ArrayList<>();

        Class<?> entityClass = ReflectionUtil.getGenericParameterClass(getClass(), 1);
        System.out.println("Class: " + entityClass);
        String query = QueryBuilder.findAllQuery(entityClass);

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BaseEntity entity = resultSetParserManager.parse(entityClass, resultSet);
                objects.add((E) entity);
            }

            System.out.println("query: " + query);
            Assert.notEmpty(objects, "Nothing was found");
            return objects;
        } catch (SQLException | CPException e) {
            throw new DaoException("Error while finding all.", e);
        }

    }


    public E findOne(T id) throws DaoException {
        BaseEntity entity = null;


        Class<?> aClass = ReflectionUtil.getGenericParameterClass(getClass(), 1);

        System.out.println("Class: " + aClass);

        String query = QueryBuilder.findByIdQuery(aClass, id);

        System.out.println(query);

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                entity = resultSetParserManager.parse(aClass, resultSet);
            }
            return (E) entity;
        } catch (SQLException | CPException e) {
            throw new DaoException("Error while searching by id. ", e);
        }


    }

    public void delete(T id) throws DaoException {

        Class<?> entityClass = ReflectionUtil.getGenericParameterClass(getClass(), 1);
        String query = QueryBuilder.deleteQuery(entityClass, id);
        System.out.println(query);

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int result = preparedStatement.executeUpdate();
            Assert.notZero(result, "Deleting entity: " + entityClass + " failed");
        } catch (SQLException | CPException e) {
            throw new DaoException("Error while deleting by id.", e);
        }


    }

    public E save(E entity) throws DaoException {


        String query = QueryBuilder.insertQuery(entity);

        System.out.println(query);

        EntityMeta entityMeta = Cache.ENTITY_META_DATA_CACHE.get(entity.getClass());
        Assert.notNull(entityMeta, "entity: " + entity + " not found");
        String idColumnFieldName = entityMeta.getIdColumnFieldName();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int result = preparedStatement.executeUpdate();
            Assert.notZero(result, "inserting entity: " + entity + " failed");
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            Assert.isTrue(generatedKeys.next(), "inserting entity: " + entity + " failed. No id obtained");
            //TODO может быть не лонг
            Long id = generatedKeys.getLong(1);
            ReflectionUtil.invokeSetter(entity, idColumnFieldName, id);
            return entity;

        } catch (SQLException | CPException e) {
            throw new DaoException("Error while saving entity: " + entity, e);
        }

    }

    public E update(E entity) throws DaoException {
        String query = QueryBuilder.updateQuery(entity);
        System.out.println(query);


        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            int updatedRows = preparedStatement.executeUpdate();
            Assert.notZero(updatedRows, "updating entity: " + entity + " failed");
            return entity;

        } catch (SQLException | CPException e) {

            throw new DaoException("");
        }


    }


    protected List<E> findByQuery(String query) {

        return null;
    }


}
