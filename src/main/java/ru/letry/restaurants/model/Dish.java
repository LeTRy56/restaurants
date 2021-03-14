package ru.letry.restaurants.model;

public class Dish extends AbstractNamedEntity {
    private Double price;

    protected Dish(Integer id, String name) {
        super(id, name);
    }
}
