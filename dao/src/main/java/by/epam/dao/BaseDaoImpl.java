package by.epam.dao;

import by.epam.entity.BaseEntity;

import java.util.List;

public abstract class BaseDaoImpl<T, E extends BaseEntity> implements BaseDao<T, E> {


    protected List<E> findAll() {
        return null;
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
