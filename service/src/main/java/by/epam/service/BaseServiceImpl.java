package by.epam.service;

import by.epam.dao.BaseDao;
import by.epam.dao.DaoFactory;
import by.epam.entity.BaseEntity;
import com.ititon.jdbc_orm.processor.exception.DefaultOrmException;
import com.ititon.jdbc_orm.util.ReflectionUtil;

import java.io.Serializable;
import java.util.List;

public class BaseServiceImpl<E extends BaseEntity, ID extends Serializable, R extends BaseDao<E, ID>>
        implements BaseService<E, ID> {

    private R currentDao;
    private DaoFactory daoFactory = DaoFactory.getInstance();

    {
        currentDao = daoFactory.getDao(getParametrizeClass());
    }

    @Override
    public E save(E entity) throws DefaultOrmException {
        return currentDao.save(entity);
    }

    @Override
    public E getOne(ID id) throws DefaultOrmException {
        return currentDao.findOne(id);
    }

    @Override
    public List<E> findAll() throws DefaultOrmException {
        return currentDao.findAll();
    }

    @Override
    public E update(E entity) throws DefaultOrmException {
        return currentDao.update(entity);
    }

    @SuppressWarnings("unchecked")
    private Class<R> getParametrizeClass() {
        return (Class<R>) ReflectionUtil.getGenericParameterClass(getClass(), 1);
    }
}
