package by.epam.entity;

import com.ititon.jdbc_orm.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cities")
public class City implements BaseEntity{
    private static final long serialVersionUID = -3276439926128412613L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    private Country country;


    @OneToMany(mappedBy = "city")
    private List<Airport> airports = new ArrayList<>();

    @OneToMany(mappedBy = "city")
    private List<UserInfo> usersInfo = new ArrayList<>();

    @OneToOne(mappedBy = "city")
    private List<Hotel> hotels = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tours_cities", joinColumns = {@JoinColumn(name = "id_city")},
            inverseJoinColumns = {@JoinColumn(name = "id_tour")})
    private List<Tour> tours = new ArrayList<>();


    public City() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

    public List<UserInfo> getUsersInfo() {
        return usersInfo;
    }

    public void setUsersInfo(List<UserInfo> usersInfo) {
        this.usersInfo = usersInfo;
    }

    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return Objects.equals(id, city.id) &&
                Objects.equals(name, city.name) &&
                Objects.equals(country, city.country) &&
                Objects.equals(airports, city.airports) &&
                Objects.equals(usersInfo, city.usersInfo) &&
                Objects.equals(hotels, city.hotels) &&
                Objects.equals(tours, city.tours);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, country, airports, usersInfo, hotels, tours);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country.getName() +
                ", airports=" + airports +
                ", usersInfo=" + usersInfo +
                ", hotels=" + hotels +
                ", tours=" + tours +
                '}';
    }
}
