package by.epam.dao;

import by.epam.dao.impl.AirportDao;

public class DaoFactory {
    private static final DaoFactory INSTANCE = new DaoFactory();
    private final DaoDirector daoDirector = DaoDirector.getInstance();

    private DaoFactory() {
    }

    public <T extends BaseDao> T getDao(final Class<T> daoClass) {
        return daoDirector.getDao(daoClass);
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }
}
