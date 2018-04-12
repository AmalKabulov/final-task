package by.epam.dao;

import by.epam.processor.Cache;
import by.epam.processor.annotation.Query;
import by.epam.processor.annotation.Repository;
import by.epam.entity.User;
import by.epam.dao.exception.DaoException;
import by.epam.processor.database.DefaultConnectionPool;

import java.sql.SQLException;
import java.util.List;


@Repository
public class DaoTest extends BaseDaoImpl<Long, User> {

    @Query("select * from epam-dno")
    public void smth() throws SQLException, DaoException {

        List<User> all = findAll();
        all.forEach(System.out::println);
//        findAll();

        User one = findOne(7L);
        System.out.println(one);

//        User user = new User();
//        user.setEmail("abc@gmail.com");
//        user.setPassword("123456");
//        User saved = save(user);
//        System.out.println(saved);
//
//        user.setEmail("asasd67das5@gmail.com");
//        User update = update(user);
//
//        System.out.println(update);
//
//        delete(7L);


//        DSConnector dsConnector = new DSConnector();
//        DefaultConnection connection = dsConnector.getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement();
    }


    public static void main(String[] args) throws SQLException, DaoException {
        Cache.warm();
        DefaultConnectionPool.getInstance().init();


        DaoTest daoTest = new DaoTest();
        daoTest.smth();
    }
}
