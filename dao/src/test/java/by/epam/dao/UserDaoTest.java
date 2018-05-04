package by.epam.dao;

import by.epam.dao.impl.RoleDao;
import by.epam.dao.impl.UserDao;
import by.epam.entity.Role;
import by.epam.entity.User;
import com.ititon.jdbc_orm.processor.exception.DefaultOrmException;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class UserDaoTest extends BaseDaoTest{
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private UserDao userDao = daoFactory.getDao(UserDao.class);
    private RoleDao roleDao = daoFactory.getDao(RoleDao.class);


    @Test
    public void saveUser() throws DefaultOrmException {
        Role role = roleDao.findOne(1L);

        System.out.println(role);
//        User user = userDao.findOne(3L);


        User user = new User();
        user.setEmail("kabulov.amal@outlook1.com");
        user.setPassword("123");
        user.setIsActive(1);
        user.getRoles().add(role);

        User savedUser = userDao.save(user);
        assertThat(user, equalTo(savedUser));


    }
}
