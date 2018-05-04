package by.epam.service;

public class ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactory();
    private final ServiceDirector serviceDirector = ServiceDirector.getInstance();


    private ServiceFactory() {
    }


    public <S extends BaseService> S getService(final Class<S> serviceClass) {
        return serviceDirector.getService(serviceClass);
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }
}
