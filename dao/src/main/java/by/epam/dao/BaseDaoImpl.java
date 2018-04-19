package by.epam.dao;

import by.epam.processor.exception.CPException;
import by.epam.processor.parser.Parser;
import by.epam.processor.query_builder.QueryBuilder;
import by.epam.processor.annotation.Repository;
import by.epam.entity.BaseEntity;
import by.epam.dao.exception.DaoException;
import by.epam.processor.database.DefaultConnectionPool;
import by.epam.processor.meta.EntityMeta;
import by.epam.processor.parser.ResultSetParserManager;
import by.epam.processor.CacheProcessor;
import by.epam.processor.query_builder.SelectQueryBuilder;
import by.epam.processor.util.ReflectionUtil;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public abstract class BaseDaoImpl<T extends Serializable, E> implements BaseDao<T, E> {

    private CacheProcessor cacheProcessor = CacheProcessor.getInstance();
    private ResultSetParserManager parserManager = ResultSetParserManager.getInstance();
    private DefaultConnectionPool connectionPool = DefaultConnectionPool.getInstance();


    @SuppressWarnings("unchecked")
    public List<E> findAll() throws DaoException {
        List<E> entities = new ArrayList<>();
        Class<? extends BaseEntity> entityClass = getParametrizeClass();
        String query = SelectQueryBuilder.findAllQuery(entityClass);
        System.out.println("QUERY: " + query);

        List<? extends BaseEntity> entitiesFromCache = cacheProcessor.getEntitiesByClass(entityClass);


        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            Long rowCounts = getRowCounts(entityClass, connection);

            if (rowCounts != null && rowCounts == entitiesFromCache.size()) {
                System.out.println("Returning from cache");
                return ((List<E>) entitiesFromCache);
            }

            Parser parser = new Parser();
            ResultSet resultSet = preparedStatement.executeQuery();
//            parserManager.complexParse(entityClass, resultSet);
             entities = (List<E>) parser.parseComplex(entityClass, resultSet);

            Assert.notEmpty(entities, "Nothing was found");
            return entities;
        } catch (SQLException | CPException e) {
            throw new DaoException("Error while finding all.", e);
        }

    }


    public List<E> findByLimit(final int skip, final int count) throws DaoException {
        List<E> entities = new ArrayList<>();
        Class<? extends BaseEntity> entityClass = getParametrizeClass();
        String query = QueryBuilder.findByLimit(entityClass, skip, count);

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BaseEntity entity = parserManager.parse(entityClass, resultSet);
                entities.add((E) entity);
            }

            Assert.notEmpty(entities, "Nothing was found");
//            cacheProcessor.putAll(entities);
            return entities;

        } catch (SQLException | CPException e) {
            throw new DaoException("Error while finding by limit", e);
        }

    }

    @SuppressWarnings("unchecked")
    public E findOne(T id) throws DaoException {
        Class<? extends BaseEntity> entityClass = getParametrizeClass();
        BaseEntity entityFromCache = cacheProcessor.getEntity(entityClass, id);
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
                entity = parserManager.parse(entityClass, resultSet);
            }
            Assert.notNull(entity, "Nothing was found");
            cacheProcessor.putEntity(entity);
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
            cacheProcessor.deleteEntity(entityClass, id);
        } catch (SQLException | CPException e) {
            throw new DaoException("Error while deleting by id.", e);
        }

    }

    public E save(E entity) throws DaoException {
        String query = QueryBuilder.insertQuery(entity);
        EntityMeta entityMeta = cacheProcessor.getMeta(entity.getClass());
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

//            cacheProcessor.putEntity(entity);
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
//            cacheProcessor.putEntity(entity);
            return entity;

        } catch (SQLException | CPException e) {

            throw new DaoException("Error while updating entity: " + entity, e);
        }


    }


    private Long getRowCounts(final Class<? extends BaseEntity> clazz, Connection connection) throws DaoException, SQLException {
        String countQuery = QueryBuilder.countQuery(clazz);
        Long rowCount = null;

        PreparedStatement preparedStatement = connection.prepareStatement(countQuery);
        ResultSet counts = preparedStatement.executeQuery();
        if (counts.next()) {
            rowCount = counts.getLong(1);
        }
        preparedStatement.close();
        return rowCount;


    }


    protected List<E> findByQuery(String query) {
        return null;
    }

    @SuppressWarnings("unchecked")
    private Class<? extends BaseEntity> getParametrizeClass() {
        return (Class<? extends BaseEntity>) ReflectionUtil.getGenericParameterClass(getClass(), 1);
    }

}
