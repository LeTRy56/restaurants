package ru.letry.restaurants.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private Set<Dish> dishes;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
