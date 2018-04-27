package by.epam.entity;

import com.ititon.jdbc_orm.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "airports")
public class Airport implements BaseEntity{
    private static final long serialVersionUID = -2634523175765308159L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "arrivalFrom")
    private List<Tour> toursForArrival = new ArrayList<>();


    @OneToMany(mappedBy = "departureTo")
    private List<Tour> toursForDeparture = new ArrayList<>();

    public Airport() {
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Tour> getToursForArrival() {
        return toursForArrival;
    }

    public void setToursForArrival(List<Tour> toursForArrival) {
        this.toursForArrival = toursForArrival;
    }

    public List<Tour> getToursForDeparture() {
        return toursForDeparture;
    }

    public void setToursForDeparture(List<Tour> toursForDeparture) {
        this.toursForDeparture = toursForDeparture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Objects.equals(id, airport.id) &&
                Objects.equals(name, airport.name) &&
                Objects.equals(city, airport.city) &&
                Objects.equals(toursForArrival, airport.toursForArrival) &&
                Objects.equals(toursForDeparture, airport.toursForDeparture);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, city, toursForArrival, toursForDeparture);
    }

    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city=" + city +
                ", toursForArrival=" + toursForArrival +
                ", toursForDeparture=" + toursForDeparture +
                '}';
    }
}
