package by.epam.dao;

import com.ititon.jdbc_orm.processor.CacheProcessor;
import com.ititon.jdbc_orm.processor.database.DSProperties;
import com.ititon.jdbc_orm.processor.database.DefaultConnectionPool;
import com.ititon.jdbc_orm.processor.exception.DefaultOrmException;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class BaseDaoTest {
    private static DefaultConnectionPool connectionPool;

    @BeforeClass
    public static void init() throws DefaultOrmException {
        CacheProcessor instance = CacheProcessor.getInstance();
        instance.initCache(10000, 5000, 10000);
        DSProperties.init("jdbc:mysql://localhost:3306/travel_agency_test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                "com.mysql.jdbc.Driver",
                "root",
                "abcd123",
                15);

        connectionPool = DefaultConnectionPool.getInstance();
        connectionPool.init();
    }


    @AfterClass
    public static void destroy() throws DefaultOrmException {

        connectionPool.destroy();

    }
}
