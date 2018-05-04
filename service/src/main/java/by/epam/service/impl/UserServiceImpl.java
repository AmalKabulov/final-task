package by.epam.service.impl;

import by.epam.dao.impl.UserDao;
import by.epam.entity.Role;
import by.epam.entity.User;
import by.epam.service.BaseServiceImpl;
import by.epam.service.ServiceFactory;
import by.epam.service.UserService;
import by.epam.service.exception.ServiceException;
import com.ititon.jdbc_orm.processor.exception.DefaultOrmException;

public class UserServiceImpl extends BaseServiceImpl<User, Long, UserDao> implements UserService {

    private static final long ROLE_USER = 2L;


    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private RoleService roleService = serviceFactory.getService(RoleService.class);

    @Override
    public User register(User user) throws ServiceException {
        try {
            Role role = roleService.getOne(ROLE_USER);
            user.getRoles().add(role);
            return save(user);
        } catch (DefaultOrmException e) {
            throw new ServiceException("Error while saving user : " + user, e);
        }

    }

    @Override
    public User makeAdmin(User user) throws ServiceException {
        try {
            Role role = roleService.getOne(1L);
            user.getRoles().add(role);
            return save(user);
        } catch (DefaultOrmException e) {
            throw new ServiceException("Error while making admin: " + user, e);
        }

    }

    @Override
    public void delete(User user) throws ServiceException {
        try {
            user.setIsActive(0);
            update(user);
        } catch (DefaultOrmException e) {
            throw new ServiceException("Error while deleting user: " + user, e);
        }
    }
}
