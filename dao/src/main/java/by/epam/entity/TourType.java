package by.epam.entity;

import com.ititon.jdbc_orm.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tour_types")
public class TourType implements BaseEntity{
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;


    @ManyToMany
    @JoinTable(name = "tours_tour_types", joinColumns = {@JoinColumn(name = "id_tour_type")},
            inverseJoinColumns = {@JoinColumn(name = "id_tour")})
    private List<Tour> tours = new ArrayList<>();


    public TourType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourType tourType = (TourType) o;
        return Objects.equals(id, tourType.id) &&
                Objects.equals(type, tourType.type) &&
                Objects.equals(tours, tourType.tours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, tours);
    }

    @Override
    public String toString() {
        return "TourType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", tours=" + tours +
                '}';
    }
}
