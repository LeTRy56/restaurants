package ru.letry.restaurants.model;

import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = Restaurant.ALL_SORTED, query = "SELECT r FROM Restaurant r ORDER BY r.name ASC"),
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant r WHERE r.id=:id")
})
@Entity
//todo http 500 - throws exception when create not unique or update (entity with same name)
//@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {
    public static final String ALL_SORTED = "Restaurant.getAll";
    public static final String DELETE = "Restaurant.delete";

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Dish> dishes;

    protected Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(String name, Collection<Dish> dishes) {
        this(null, name, dishes);
    }

    public Restaurant(Integer id, String name, Collection<Dish> dishes) {
        super(id, name);
        setDishes(dishes);
    }

    public Restaurant() {
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Collection<Dish> dishes) {
        this.dishes = CollectionUtils.isEmpty(dishes) ? Collections.emptySet() : new HashSet<>(dishes);
    }
}
