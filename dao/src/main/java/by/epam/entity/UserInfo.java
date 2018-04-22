package by.epam.entity;

import com.ititon.jdbc_orm.annotation.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "users_info")
public class UserInfo implements BaseEntity{

    @Id
    @Column(name = "id_user")
    private Long idUser;

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
    private Integer discount;


    public UserInfo() {
    }


    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
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

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(idUser, userInfo.idUser) &&
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

        return Objects.hash(idUser, firstName, lastName, dateOfBirth, country, city, phoneNumber, discount);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "idUser=" + idUser +
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
