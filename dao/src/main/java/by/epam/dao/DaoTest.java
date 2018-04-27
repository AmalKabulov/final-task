package by.epam.dao;

import by.epam.DSProperties;
import by.epam.entity.Role;
import by.epam.entity.User;
import com.ititon.jdbc_orm.meta.EntityMeta;
import com.ititon.jdbc_orm.processor.CacheProcessor;
import com.ititon.jdbc_orm.processor.database.DefaultConnectionPool;

import com.ititon.jdbc_orm.processor.exception.DefaultOrmException;

import java.sql.SQLOutput;
import java.util.*;


public class DaoTest {

    public static void main(String[] args) throws DefaultOrmException {


        CacheProcessor instance = CacheProcessor.getInstance();
        instance.initCache(10000, 5000, 10000);
        EntityMeta meta = instance.getMeta(User.class);
        System.out.println(meta);
        com.ititon.jdbc_orm.processor.database.DSProperties.init(DSProperties.URL, DSProperties.DRIVER, DSProperties.USERNAME, DSProperties.PASSWORD, DSProperties.MAX_POOL_SIZE);
        DefaultConnectionPool.getInstance().init();


        UserDao userDao = new UserDao();


//        System.out.println("Find all:");
//        userDao.findAll().forEach(System.out::println);
//
//
//        User user = userDao.findOne(1L);
//        System.out.println("By id: " + user);
//
//
//        User user1 = userDao.findOne(1L);
//        System.out.println("By id: " + user1);

//        System.out.println(user1 == user);

        RoleDao roleDao = new RoleDao();
        Role role = roleDao.findOne(1L);

        User user = new User();
        user.setEmail("lalala1231231lal@gmail.com");
        user.setPassword("asdasdas");
//        Role role = new Role();
//        role.setRoleName("bla-vsemu");
        user.getRoles().add(role);
        userDao.save(user);


//        System.out.println("BY limit");
//        userDao.findByLimit(0, 5).forEach(System.out::println);
//        while(true) {
//
//        }


//        TourDao tourDao = new TourDao();
//        tourDao.findOne(1L);


        AirportDao airportDao = new AirportDao();
        System.out.println(airportDao.findOne(1L));


    }


}
