package by.epam.dao;

import by.epam.processor.CPException;
import by.epam.processor.QueryBuilder;
import by.epam.processor.annotation.Repository;
import by.epam.entity.BaseEntity;
import by.epam.dao.exception.DaoException;
import by.epam.processor.database.DefaultConnectionPool;
import by.epam.processor.meta.EntityMeta;
import by.epam.processor.parser.ResultSetParserManager;
import by.epam.processor.util.CacheProcessor;
import by.epam.processor.util.ReflectionUtil;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public abstract class BaseDaoImpl<T extends Serializable, E extends BaseEntity> implements BaseDao<T, E> {

    private static final CacheProcessor CACHE_PROCESSOR = CacheProcessor.getInstance();

    private ResultSetParserManager resultSetParserManager = ResultSetParserManager.getINSTANCE();
    private DefaultConnectionPool connectionPool = DefaultConnectionPool.getInstance();


    @SuppressWarnings("unchecked")
    public List<E> findAll() throws DaoException {

        List<E> objects = new ArrayList<>();
        Class<? extends BaseEntity> entityClass = getParametrizeClass();
        String query = QueryBuilder.findAllQuery(entityClass);

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BaseEntity entity = resultSetParserManager.parse(entityClass, resultSet);
                objects.add((E) entity);
            }
            Assert.notEmpty(objects, "Nothing was found");
            return objects;
        } catch (SQLException | CPException e) {
            throw new DaoException("Error while finding all.", e);
        }

    }

    @SuppressWarnings("unchecked")
    public E findOne(T id) throws DaoException {
        Class<? extends BaseEntity> entityClass = getParametrizeClass();
        BaseEntity entityFromCache = CACHE_PROCESSOR.getEntity(entityClass, id);
        if (entityFromCache != null) {
            System.out.println("FROM CACHE...");
            return (E) entityFromCache;
        }

        BaseEntity entity = null;
        String query = QueryBuilder.findByIdQuery(entityClass, id);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                entity = resultSetParserManager.parse(entityClass, resultSet);
            }
            Assert.notNull(entity, "Nothing was found");
            CACHE_PROCESSOR.putEntity(entity);
            return (E) entity;
        } catch (SQLException | CPException e) {
            throw new DaoException("Error while searching by id. ", e);
        }


    }

    public void delete(T id) throws DaoException {
        Class<? extends BaseEntity> entityClass = getParametrizeClass();
        String query = QueryBuilder.deleteQuery(entityClass, id);

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int result = preparedStatement.executeUpdate();
            Assert.notZero(result, "Deleting entity: " + entityClass + " failed");
            CACHE_PROCESSOR.deleteEntity(entityClass, id);
        } catch (SQLException | CPException e) {
            throw new DaoException("Error while deleting by id.", e);
        }


    }

    public E save(E entity) throws DaoException {
        String query = QueryBuilder.insertQuery(entity);
        EntityMeta entityMeta = CACHE_PROCESSOR.getMeta(entity.getClass());
        Assert.notNull(entityMeta, "entity: " + entity + " not found");
        String idColumnFieldName = entityMeta.getIdColumnFieldName();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int result = preparedStatement.executeUpdate();
            Assert.notZero(result, "inserting entity: " + entity + " failed");
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            Assert.isTrue(resultSet.next(), "inserting entity: " + entity + " failed. No id obtained");
            //TODO может быть не лонг
            Long id = resultSet.getLong(1);
            ReflectionUtil.invokeSetter(entity, idColumnFieldName, id);

            CACHE_PROCESSOR.putEntity(entity);
            return entity;

        } catch (SQLException | CPException e) {
            throw new DaoException("Error while saving entity: " + entity, e);
        }

    }

    public E update(E entity) throws DaoException {
        String query = QueryBuilder.updateQuery(entity);

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            int updatedRows = preparedStatement.executeUpdate();
            Assert.notZero(updatedRows, "updating entity: " + entity + " failed");
            return entity;

        } catch (SQLException | CPException e) {

            throw new DaoException("Error while updating entity: " + entity, e);
        }


    }


    protected List<E> findByQuery(String query) {
        return null;
    }

    @SuppressWarnings("unchecked")
    private Class<? extends BaseEntity> getParametrizeClass() {
        return (Class<? extends BaseEntity>) ReflectionUtil.getGenericParameterClass(getClass(), 1);
    }




}
