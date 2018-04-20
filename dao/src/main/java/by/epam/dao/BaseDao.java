package by.epam.dao;

import by.epam.entity.BaseEntity;
import com.ititon.jdbc_orm.DefaultRepository;

import java.io.Serializable;

public abstract class BaseDao <E extends BaseEntity, ID extends Serializable> extends DefaultRepository<E, ID> {

}
