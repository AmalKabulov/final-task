package by.epam.service;

import by.epam.service.impl.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServiceDirector {
    private static final ServiceDirector INSTANCE = new ServiceDirector();
    private Map<Class<? extends BaseService>, BaseService> serviceMap;


    private ServiceDirector() {
        serviceMap = new HashMap<>();
        serviceMap.put(AirportService.class, new AirportService());
        serviceMap.put(CityService.class, new CityService());
        serviceMap.put(CountryService.class, new CountryService());
        serviceMap.put(HotelService.class, new HotelService());
        serviceMap.put(OrderService.class, new OrderService());
        serviceMap.put(RoleService.class, new RoleService());
        serviceMap.put(TourService.class, new TourService());
        serviceMap.put(TourTypeService.class, new TourTypeService());
        serviceMap.put(UserServiceImpl.class, new UserServiceImpl());
        serviceMap.put(UserInfoService.class, new UserInfoService());
        serviceMap = Collections.unmodifiableMap(serviceMap);
    }


    @SuppressWarnings("unchecked")
    public <S extends BaseService> S getService(final Class<S> serviceClass) {
        return (S) serviceMap.get(serviceClass);
    }

    public static ServiceDirector getInstance() {
        return INSTANCE;
    }
}
