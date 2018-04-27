package by.epam.service;

import by.epam.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends BaseEntity, ID extends Serializable> {
    T save(T entity);
    T get(ID id);
    List<T> getAll();
    void delete(T entity);
    void update(T entity);
}
