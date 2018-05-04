package by.epam.service;

import by.epam.entity.User;
import by.epam.service.exception.ServiceException;

public interface UserService {
    User register(final User user) throws ServiceException;

    User makeAdmin(final User user) throws ServiceException;

    void delete(final User user) throws ServiceException;

}
