package by.epam.dao;

import by.epam.dao.impl.AirportDao;
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


    @Test
    public void saveCascadeAirport() throws DefaultOrmException {
        Country country = new Country();
        country.setName("Turkey");
        country.setPhoneCode("+90");


        City city = new City();
        city.setCountry(country);
        city.setName("Istanbul");
//        country.getCities().add(city);

        Airport airport = new Airport();
        airport.setCity(city);
        airport.setName("Istanbul airport - Soltan-Ahmet");

        Airport save = airportDao.save(airport);
        System.out.println(save);

        assertThat(airport, equalTo(save));

    }

}
