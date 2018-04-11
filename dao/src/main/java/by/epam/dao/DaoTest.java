package by.epam.dao;

import by.epam.processor.annotation.Query;
import by.epam.processor.annotation.Repository;
import by.epam.processor.database.DSConnector;
import by.epam.processor.database.DefaultConnection;
import by.epam.entity.User;
import by.epam.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


@Repository
public class DaoTest extends BaseDaoImpl<Long, User> {

    @Query("select * from epam-dno")
    public void smth() throws SQLException, DaoException {

        List<User> all = findAll();
        all.forEach(System.out::println);
//        findAll();

        User one = findOne(4L);
        System.out.println(one);

//        User user = new User();
//        user.setEmail("asdasd@gmail.com");
//        user.setPassword("123456");
//        User saved = save(user);
//        System.out.println(saved);
//
//        user.setEmail("11111@gmail.com");
//        User update = update(user);

//        System.out.println(update);

        delete(3L);


//        DSConnector dsConnector = new DSConnector();
//        DefaultConnection connection = dsConnector.getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement();
    }


    public static void main(String[] args) throws SQLException, DaoException {

        DaoTest daoTest = new DaoTest();
        daoTest.smth();
    }
}
