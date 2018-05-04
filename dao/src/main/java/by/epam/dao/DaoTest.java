package by.epam.dao;

import com.ititon.jdbc_orm.processor.exception.DefaultOrmException;


public class DaoTest {

    public static void main(String[] args) throws DefaultOrmException {


//        CacheProcessor instance = CacheProcessor.getInstance();
//        instance.initCache(10000, 5000, 10000);
//        com.ititon.jdbc_orm.processor.database.DSProperties.init(DSProperties.URL, DSProperties.DRIVER, DSProperties.USERNAME, DSProperties.PASSWORD, DSProperties.MAX_POOL_SIZE);
//        DefaultConnectionPool connectionPool = DefaultConnectionPool.getInstance();
//        connectionPool.init();
//
//
//        UserDao userDao = new UserDao();
//
//
//        System.out.println("Find all:");
//        userDao.findAll().forEach(System.out::println);
////
////
//        User user1 = userDao.findOne(1L);
//        System.out.println("By id: " + user1);
////
////
////        User user1 = userDao.findOne(1L);
////        System.out.println("By id: " + user1);
//
////        System.out.println(user1 == user);
//
//        RoleDao roleDao = new RoleDao();
//        Role role = roleDao.findOne(1L);
//
//        User user = new User();
//        user.setEmail("kabulov.amal@outlook.com");
//        user.setPassword("abcd123");
////        Role role = new Role();
////        role.setRoleName("admin");
//        user.getRoles().add(role);
//        userDao.save(user);


        int[][] a = new int[][]{{1, 11, 323,},{123, 1231231, 12312}};

        int v = 0;
        for (int i = 0; i < a.length; i++) {
            if (a.length > ++v) {
                if (a[i].length > a[v].length) {
                    for (int j = 0; j < a[i].length; j++) {
                        System.out.println(a[i][j] + "\t");
                    }
                }
            }
        }
//        System.out.println("BY limit");
//        userDao.findByLimit(0, 5).forEach(System.out::println);
//        while(true) {
//
//        }


//        TourDao tourDao = new TourDao();
//        tourDao.findOne(1L);


//        AirportDao airportDao = new AirportDao();
//        System.out.println(airportDao.findOne(1L));

//        connectionPool.destroy();


    }


}
