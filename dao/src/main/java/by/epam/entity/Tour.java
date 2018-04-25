package by.epam.entity;

import com.ititon.jdbc_orm.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tours")
public class Tour implements BaseEntity{

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

    @Column(name = "departure_date")
    private LocalDateTime departureDate;

    @ManyToOne
    @JoinColumn(name = "arrival_from")
    private Airport arrivalFrom;

    @ManyToOne
    @JoinColumn(name = "departure_to")
    private Airport departureTo;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "urgent")
    private Integer urgent;

    @ManyToMany(mappedBy = "tours", fetch = FetchType.EAGER)
    private List<TourType> tourTypes = new ArrayList<>();

    @ManyToMany(mappedBy = "tours", fetch = FetchType.EAGER)
    private List<City> cities = new ArrayList<>();

    @OneToMany(mappedBy = "tour")
    private List<Order> orders = new ArrayList<>();


    public Tour() {
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public Airport getArrivalFrom() {
        return arrivalFrom;
    }

    public void setArrivalFrom(Airport arrivalFrom) {
        this.arrivalFrom = arrivalFrom;
    }

    public Airport getDepartureTo() {
        return departureTo;
    }

    public void setDepartureTo(Airport departureTo) {
        this.departureTo = departureTo;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getUrgent() {
        return urgent;
    }

    public void setUrgent(Integer urgent) {
        this.urgent = urgent;
    }

    public List<TourType> getTourTypes() {
        return tourTypes;
    }

    public void setTourTypes(List<TourType> tourTypes) {
        this.tourTypes = tourTypes;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return Objects.equals(id, tour.id) &&
                Objects.equals(name, tour.name) &&
                Objects.equals(price, tour.price) &&
                Objects.equals(arrivalDate, tour.arrivalDate) &&
                Objects.equals(departureDate, tour.departureDate) &&
                Objects.equals(arrivalFrom, tour.arrivalFrom) &&
                Objects.equals(departureTo, tour.departureTo) &&
                Objects.equals(isActive, tour.isActive) &&
                Objects.equals(urgent, tour.urgent) &&
                Objects.equals(tourTypes, tour.tourTypes) &&
                Objects.equals(cities, tour.cities) &&
                Objects.equals(orders, tour.orders);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, price, arrivalDate, departureDate, arrivalFrom, departureTo, isActive, urgent, tourTypes, cities, orders);
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", arrivalDate=" + arrivalDate +
                ", departureDate=" + departureDate +
                ", arrivalFrom=" + arrivalFrom +
                ", departureTo=" + departureTo +
                ", isActive=" + isActive +
                ", urgent=" + urgent +
                ", tourTypes=" + tourTypes +
                ", cities=" + cities +
                ", orders=" + orders +
                '}';
    }
}
