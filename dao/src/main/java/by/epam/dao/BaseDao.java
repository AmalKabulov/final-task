package by.epam.dao;

import by.epam.dao.exception.DaoException;

import java.io.Serializable;
import java.util.List;

public interface BaseDao <T extends Serializable, E> {
    List<E> findAll() throws DaoException;

    E findOne(T id) throws DaoException;

    void delete(T id) throws DaoException;

    E save(E entity) throws DaoException;

    E update(E entity) throws DaoException;
}
