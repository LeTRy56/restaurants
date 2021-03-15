package ru.letry.restaurants.model;

import org.springframework.stereotype.Component;

public class Dish extends AbstractNamedEntity {
    private Double price;

    protected Dish(Integer id, String name) {
        super(id, name);
    }

    public Dish(String name, Double price) {
        this(null, name, price);
    }

    public Dish(Integer id, String name, Double price) {
        super(id, name);
        this.price = price;
    }



    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
