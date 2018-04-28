package by.epam.dao;

import by.epam.dao.impl.AirportDao;
import by.epam.dao.impl.CityDao;
import by.epam.dao.impl.CountryDao;
import by.epam.entity.Airport;
import by.epam.entity.City;
import by.epam.entity.Country;
import com.ititon.jdbc_orm.processor.exception.DefaultOrmException;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class AirportDaoTestTest extends BaseDaoTest {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private AirportDao airportDao = daoFactory.getDao(AirportDao.class);
    private CountryDao countryDao = daoFactory.getDao(CountryDao.class);
    private CityDao cityDao = daoFactory.getDao(CityDao.class);


    @Test
    public void saveCascadeAirport() throws DefaultOrmException {
//        Country country = new Country();
//        country.setName("Russia");
//        country.setPhoneCode("+7");

        Country country = countryDao.findOne(1L);
        City city = country.getCities().get(0);
        city.setName("Moscow1");

        cityDao.update(city);
//        City city = new City();
//        city.setCountry(country);
//        city.setName("Moscow");
//        country.getCities().add(city);
//
//        Airport airport = new Airport();
//        airport.setCity(city);
//        airport.setName("Moscow airport - Domodedovo");
//        city.getAirports().add(airport);
//
//        Airport save = airportDao.update(airport);
//        System.out.println(save);

//        assertThat(airport, equalTo(save));

    }

}
