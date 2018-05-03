package by.epam.entity;

import com.ititon.jdbc_orm.annotation.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "users_info")
public class UserInfo implements BaseEntity {
    private static final long serialVersionUID = 4944493895044853994L;

    @Id
    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "discount")
    private int discount;


    public UserInfo() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfo)) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(user, userInfo.user) &&
                Objects.equals(firstName, userInfo.firstName) &&
                Objects.equals(lastName, userInfo.lastName) &&
                Objects.equals(dateOfBirth, userInfo.dateOfBirth) &&
                Objects.equals(country, userInfo.country) &&
                Objects.equals(city, userInfo.city) &&
                Objects.equals(phoneNumber, userInfo.phoneNumber) &&
                Objects.equals(discount, userInfo.discount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(user, firstName, lastName, dateOfBirth, country, city, phoneNumber, discount);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + user.getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", country=" + country +
                ", city=" + city +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", discount=" + discount +
                '}';
    }
}
