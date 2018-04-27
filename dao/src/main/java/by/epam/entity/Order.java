package by.epam.entity;

import com.ititon.jdbc_orm.annotation.*;

import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order implements BaseEntity{
    private static final long serialVersionUID = -3143004111991609692L;


    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;


    @ManyToOne
    @JoinColumn(name = "id_tour")
    private Tour tour;


    @Column(name = "discount")
    private Integer discount;


    @Column(name = "price_with_discount")
    private Integer priceWithDiscount;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(Integer priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order1 = (Order) o;
        return Objects.equals(user, order1.user) &&
                Objects.equals(tour, order1.tour) &&
                Objects.equals(discount, order1.discount) &&
                Objects.equals(priceWithDiscount, order1.priceWithDiscount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(user, tour, discount, priceWithDiscount);
    }

    @Override
    public String toString() {
        return "OrderDao{ id=" + id +
                "user=" + user +
                ", tour=" + tour +
                ", discount=" + discount +
                ", priceWithDiscount=" + priceWithDiscount +
                '}';
    }
}
