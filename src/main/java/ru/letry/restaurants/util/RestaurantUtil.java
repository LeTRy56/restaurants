package ru.letry.restaurants.util;

import ru.letry.restaurants.dto.RestaurantDTO;
import ru.letry.restaurants.model.Restaurant;

import java.util.*;

public class RestaurantUtil {
    public static List<RestaurantDTO> getDTOs(Collection<Restaurant> restaurants, Map<Integer, Integer> results) {
        List<RestaurantDTO> restaurantDTOs = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            restaurantDTOs.add(createDTO(restaurant, results.get(restaurant.id())));
        }
        return restaurantDTOs;
    }

    private static RestaurantDTO createDTO(Restaurant restaurant, Integer votes) {
        return new RestaurantDTO(restaurant.id(), restaurant.getName(), new HashSet<>(restaurant.getDishes()), votes);
    }
}
