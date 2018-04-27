package by.epam.service;

import by.epam.dao.BaseDao;
import by.epam.entity.BaseEntity;

import java.io.Serializable;

public abstract class BaseServiceImpl<E extends BaseEntity, ID extends Serializable, K extends BaseDao<E, ID>>
        implements BaseService<E, ID> {


}
