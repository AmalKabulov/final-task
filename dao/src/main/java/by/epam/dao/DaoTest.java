package by.epam.dao;

import by.epam.annotation.Query;
import by.epam.annotation.Repository;
import by.epam.entity.User;
import by.epam.exception.NoSuchEntityException;
import by.epam.exception.QueryNotFoundException;

import java.sql.SQLException;
import java.util.List;


@Repository
 public class DaoTest extends BaseDaoImpl<Long, User>{

    @Query("select * from epam-dno")
    public void smth() throws SQLException, ClassNotFoundException, QueryNotFoundException, NoSuchEntityException {

        List<User> all = findAll();
        all.forEach(System.out::println);
//        findAll();
//        DSConnection dsConnection = new DSConnection();
//        DefaultConnection connection = dsConnection.getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement();
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException, QueryNotFoundException, NoSuchEntityException {
        DaoTest daoTest = new DaoTest();
        daoTest.smth();
    }
}
