package by.epam.dao;

import by.epam.DSProperties;
import by.epam.dao.exception.DaoException;
import by.epam.entity.User;
import com.ititon.jdbc_orm.meta.EntityMeta;
import com.ititon.jdbc_orm.processor.database.TransactionManager;
import com.ititon.jdbc_orm.processor.exception.DefaultOrmException;


public class DaoTest {

    public static void main(String[] args) throws DefaultOrmException {


        com.ititon.jdbc_orm.processor.CacheProcessor instance = com.ititon.jdbc_orm.processor.CacheProcessor.getInstance();
        instance.initCache(10000, 5000, 10000);
        EntityMeta meta = instance.getMeta(User.class);
        System.out.println(meta);
        com.ititon.jdbc_orm.processor.database.DSProperties.init(DSProperties.URL, DSProperties.DRIVER, DSProperties.USERNAME, DSProperties.PASSWORD, DSProperties.MAX_POOL_SIZE);
        TransactionManager.getInstance().init();
        DaoDef daoDef = new DaoDef();

        System.out.println("By id: " + daoDef.findOne(8L));
        daoDef.findAll().forEach(System.out::println);

        System.out.println("BY limit");
        daoDef.findByLimit(0, 5).forEach(System.out::println);
        while(true) {

        }




    }
}
