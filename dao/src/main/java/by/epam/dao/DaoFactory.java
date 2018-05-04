package by.epam.dao;

public class DaoFactory {
    private static final DaoFactory INSTANCE = new DaoFactory();
    private final DaoDirector daoDirector = DaoDirector.getInstance();

    private DaoFactory() {
    }

    public <D extends BaseDao> D getDao(final Class<D> daoClass) {
        return daoDirector.getDao(daoClass);
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }
}
