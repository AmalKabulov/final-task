package by.epam.dao;

import by.epam.processor.annotation.Query;
import by.epam.processor.annotation.Repository;
import by.epam.processor.database.DSConnection;
import by.epam.processor.database.DefaultConnection;
import by.epam.entity.User;
import by.epam.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


@Repository
 public class DaoTest extends BaseDaoImpl<Long, User>{

    @Query("select * from epam-dno")
    public void smth() throws SQLException,  DaoException {

        List<User> all = findAll();
        all.forEach(System.out::println);
//        findAll();

        User one = findOne(1L);

        User user = new User();
        user.setEmail("abcdefghij@gmail.com");
        user.setPassword("123456");
        User saved = save(user);

        System.out.println(saved);

        System.out.println(one);
        DSConnection dsConnection = new DSConnection();
        DefaultConnection connection = dsConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement();
    }



    public static void main(String[] args) throws SQLException, DaoException {
        DaoTest daoTest = new DaoTest();
        daoTest.smth();
    }
}
