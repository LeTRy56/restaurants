package ru.letry.restaurants.dto;

public class UserDTO {
    private final int id;

    private final int restaurantId;

    private final String restaurantName;


    public UserDTO(int id, int restaurantId, String restaurantName) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }

    public int getId() {
        return id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}
