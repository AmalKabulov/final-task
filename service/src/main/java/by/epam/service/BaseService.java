package by.epam.service;

import by.epam.dao.BaseDao;
import by.epam.entity.BaseEntity;
import com.ititon.jdbc_orm.processor.exception.DefaultOrmException;

import java.io.Serializable;
import java.util.List;

public interface BaseService<E extends BaseEntity, ID extends Serializable> {

    E save(final E entity) throws DefaultOrmException;

    E getOne(ID id) throws DefaultOrmException;

    List<E> findAll() throws DefaultOrmException;

    E update(final E entity) throws DefaultOrmException;
}
