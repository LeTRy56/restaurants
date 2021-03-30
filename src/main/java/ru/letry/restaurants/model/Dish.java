package ru.letry.restaurants.model;


import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = Dish.DELETE, query = "DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId"),
        @NamedQuery(name = Dish.ALL_SORTED, query = "SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.name ASC")
})
@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "name"}, name = "dishes_unique_restaurant_name_idx")})
public class Dish extends AbstractNamedEntity {
    public static final String DELETE = "Dish.delete";
    public static final String ALL_SORTED = "Dish.getAll";

    @Column(name = "price", nullable = false)
    @NotNull
    @DecimalMin("5.0")
    @DecimalMax("5000.0")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

//    private LocalDate date;

    protected Dish(Integer id, String name) {
        super(id, name);
    }

    public Dish(String name, BigDecimal price) {
        this(null, name, price);
    }

    public Dish(Integer id, String name, BigDecimal price) {
        super(id, name);
        this.price = price;
    }

    public Dish() {
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
