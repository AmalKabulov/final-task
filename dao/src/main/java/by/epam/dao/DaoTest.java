package by.epam.dao;

import by.epam.processor.annotation.Query;
import by.epam.processor.annotation.Repository;
import by.epam.entity.User;
import by.epam.dao.exception.DaoException;
import by.epam.processor.database.DefaultConnectionPool;
import by.epam.processor.util.CacheProcessor;

import java.sql.SQLException;
import java.util.List;


@Repository
public class DaoTest extends BaseDaoImpl<Long, User> {

    @Query("select * from epam-dno")
    public void smth() throws DaoException {

        List<User> all = findAll();
        all.forEach(System.out::println);
//        findAll();

        User one = findOne(7L);
        System.out.println(one);

        User one1 = findOne(8L);
        System.out.println(one1);

        User one2 = findOne(7L);
        System.out.println(one2);

        User one3 = findOne(7L);
        System.out.println(one3);


        List<User> byLimit = findByLimit(0, 5);
        byLimit.forEach(System.out::println);
//
//        User user = new User();
//        user.setEmail("abc12312123123@gmail.com");
//        user.setPassword("123456");
//        User saved = save(user);
//        System.out.println(saved);
//
//        user.setEmail("asasadsadd67das5@gmail.com");
//        User update = update(user);
//
//        System.out.println(update);
//
//        delete(6L);


//        DSConnector dsConnector = new DSConnector();
//        DefaultConnection connection = dsConnector.getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement();
    }


    public static void main(String[] args) throws DaoException {
        CacheProcessor.getInstance().initCache(60000, 30000, 10000);
        DefaultConnectionPool.getInstance().init();


        DaoTest daoTest = new DaoTest();
        daoTest.smth();
    }
}
