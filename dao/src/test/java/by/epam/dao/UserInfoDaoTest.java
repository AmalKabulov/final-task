package by.epam.dao;

import by.epam.dao.impl.CountryDao;
import by.epam.dao.impl.UserDao;
import by.epam.dao.impl.UserInfoDao;
import by.epam.entity.Country;
import by.epam.entity.User;
import by.epam.entity.UserInfo;
import com.ititon.jdbc_orm.processor.exception.DefaultOrmException;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class UserInfoDaoTest extends BaseDaoTest{
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private UserDao userDao = daoFactory.getDao(UserDao.class);
    private UserInfoDao userInfoDao = daoFactory.getDao(UserInfoDao.class);
    private CountryDao countryDao = daoFactory.getDao(CountryDao.class);

    @Test
    public void saveUserInfo() throws DefaultOrmException {
        User user = userDao.findOne(1L);
        Country country = countryDao.findOne(1L);
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        userInfo.setCountry(country);
        userInfo.setCity(country.getCities().get(0));
        userInfo.setPhoneNumber("+1234");
        userInfo.setFirstName("alex");
        userInfo.setLastName("alexov");
        userInfo.setDateOfBirth(LocalDate.now());

        UserInfo saveduserInfo = userInfoDao.save(userInfo);
        assertThat(userInfo, equalTo(saveduserInfo));


    }


    @Test
    public void updateUserInfo() throws DefaultOrmException {
        User user = userDao.findOne(1L);
        Country country = countryDao.findOne(1L);
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        userInfo.setCountry(country);
        userInfo.setCity(country.getCities().get(0));
        userInfo.setPhoneNumber("+1234");
        userInfo.setFirstName("alex");
        userInfo.setLastName("alexov");
        userInfo.setDateOfBirth(LocalDate.now());

        UserInfo saveduserInfo = userInfoDao.update(userInfo);
        assertThat(userInfo, equalTo(saveduserInfo));
    }


    @Test
    public void findUserById() throws DefaultOrmException {
//        User user = userDao.findOne(1L);
//        System.out.println("USER IS " + user);
        User user = new User();
        user.setId(1L);
        UserInfo userInfo = userInfoDao.findOne(user);
        assertThat(user, equalTo(userInfo.getUser()));
    }
}
