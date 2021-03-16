package ru.letry.restaurants.repository.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.letry.restaurants.model.Dish;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.repository.RestaurantRepository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MemoryRestaurantRepository extends MemoryBaseRepository<Restaurant> implements RestaurantRepository {
    private static final Logger LOG = LoggerFactory.getLogger(MemoryRestaurantRepository.class);

    {
        put(new Restaurant(100, "First restaurant", Set.of(new Dish(10, "Dish 1", 45d), new Dish(11, "Dish 2", 55d))));
        put(new Restaurant(101, "Second restaurant", Set.of(new Dish(12, "Dish 3", 35d), new Dish(13, "Dish 4", 25d))));
    }

    @Override
    public List<Restaurant> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(Restaurant::getName))
                .collect(Collectors.toList());
    }
}
