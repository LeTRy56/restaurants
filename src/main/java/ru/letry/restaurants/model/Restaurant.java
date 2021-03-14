package ru.letry.restaurants.model;

import java.util.Set;

public class Restaurant extends AbstractNamedEntity {

    private Set<Dish> lunch;

    protected Restaurant(Integer id, String name) {
        super(id, name);
    }
}
