package by.epam.dao;

import by.epam.dao.impl.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DaoDirector {

    private static final DaoDirector INSTANCE = new DaoDirector();
    private Map<Class<? extends BaseDao>, BaseDao> daoMap;

    private DaoDirector() {
        daoMap = new HashMap<>();
        daoMap.put(AirportDao.class, new AirportDao());
        daoMap.put(CityDao.class, new CityDao());
        daoMap.put(CountryDao.class, new CountryDao());
        daoMap.put(HotelDao.class, new HotelDao());
        daoMap.put(OrderDao.class, new OrderDao());
        daoMap.put(RoleDao.class, new RoleDao());
        daoMap.put(TourDao.class, new TourDao());
        daoMap.put(TourTypeDao.class, new TourTypeDao());
        daoMap.put(UserDao.class, new UserDao());
        daoMap.put(UserInfoDao.class, new UserInfoDao());
        daoMap = Collections.unmodifiableMap(daoMap);
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseDao> T getDao(final Class<? extends BaseDao> daoClass) {
        return (T) daoMap.get(daoClass);
    }

    public static DaoDirector getInstance() {
        return INSTANCE;
    }
}
