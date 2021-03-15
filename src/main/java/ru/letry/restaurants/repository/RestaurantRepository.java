package ru.letry.restaurants.repository;

import ru.letry.restaurants.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    //null if not found, when updated
    Restaurant save(Restaurant restaurant, int userId);

    // false if not found
    boolean delete(int id, int userId);

    // null if not found
    Restaurant get(int id, int userId);

    List<Restaurant> getAll(int userId);
}
