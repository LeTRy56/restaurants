package ru.letry.restaurants.dto;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id &&
                restaurantId == userDTO.restaurantId &&
                Objects.equals(restaurantName, userDTO.restaurantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, restaurantId, restaurantName);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", restaurantId=" + restaurantId +
                ", restaurantName='" + restaurantName + '\'' +
                '}';
    }
}
