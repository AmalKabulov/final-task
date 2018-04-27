package by.epam.entity;

import com.ititon.jdbc_orm.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "countries")
public class Country implements BaseEntity{
    private static final long serialVersionUID = -1656230159759973881L;

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "name")
    private String name;


    @Column(name = "phone_code")
    private String phoneCode;

    @OneToMany(mappedBy = "country")
    private List<City> cities = new ArrayList<>();

    @OneToMany(mappedBy = "country")
    private List<UserInfo> usersInfo = new ArrayList<>();

    public Country() {
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

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<UserInfo> getUsersInfo() {
        return usersInfo;
    }

    public void setUsersInfo(List<UserInfo> usersInfo) {
        this.usersInfo = usersInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) &&
                Objects.equals(name, country.name) &&
                Objects.equals(phoneCode, country.phoneCode) &&
                Objects.equals(cities, country.cities) &&
                Objects.equals(usersInfo, country.usersInfo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, phoneCode, cities, usersInfo);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneCode='" + phoneCode + '\'' +
                ", cities=" + cities +
                ", usersInfo=" + usersInfo +
                '}';
    }
}
