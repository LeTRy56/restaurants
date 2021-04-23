package ru.letry.restaurants.dto;

import ru.letry.restaurants.model.Dish;

import java.util.Objects;
import java.util.Set;

public class RestaurantDTO extends BaseDTO {

    private Set<Dish> dishes;

    private int votes;

    public RestaurantDTO(Integer id, String name, Set<Dish> dishes, int votes) {
        this.id = id;
        this.name = name;
        this.dishes = dishes;
        this.votes = votes;
    }

    public RestaurantDTO(String name, Set<Dish> dishes) {
        this(null, name, dishes, 0);
    }

    public RestaurantDTO() {
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public int getVotes() {
        return votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantDTO that = (RestaurantDTO) o;
        return votes == that.votes &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(dishes, that.dishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dishes, votes);
    }

    @Override
    public String toString() {
        return "RestaurantDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dishes=" + dishes +
                ", votes=" + votes +
                '}';
    }
}
