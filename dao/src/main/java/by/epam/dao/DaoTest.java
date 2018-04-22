package by.epam.dao;

import by.epam.DSProperties;
import by.epam.entity.User;
import com.ititon.jdbc_orm.meta.EntityMeta;
import com.ititon.jdbc_orm.processor.CacheProcessor;
import com.ititon.jdbc_orm.processor.database.DefaultConnectionPool;

import com.ititon.jdbc_orm.processor.exception.DefaultOrmException;

import java.sql.SQLOutput;


public class DaoTest {

    public static void main(String[] args) throws DefaultOrmException {


        CacheProcessor instance = CacheProcessor.getInstance();
        instance.initCache(10000, 5000, 10000);
        EntityMeta meta = instance.getMeta(User.class);
        System.out.println(meta);
        com.ititon.jdbc_orm.processor.database.DSProperties.init(DSProperties.URL, DSProperties.DRIVER, DSProperties.USERNAME, DSProperties.PASSWORD, DSProperties.MAX_POOL_SIZE);
        DefaultConnectionPool.getInstance().init();


        UserDao userDao = new UserDao();
        System.out.println("By id: " + userDao.findOne(1L));

        System.out.println("Find all:");
        userDao.findAll().forEach(System.out::println);

        System.out.println("BY limit");
        userDao.findByLimit(0, 5).forEach(System.out::println);
//        while(true) {
//
//        }


//        TourDao tourDao = new TourDao();
//        tourDao.findOne(1L);


        AirportDao airportDao = new AirportDao();
        System.out.println(airportDao.findOne(1L));
    }
}
