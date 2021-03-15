package ru.letry.restaurants.model;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Restaurant extends AbstractNamedEntity {

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

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Collection<Dish> dishes) {
        this.dishes = CollectionUtils.isEmpty(dishes) ? Collections.emptySet() : new HashSet<>(dishes);
    }
}
