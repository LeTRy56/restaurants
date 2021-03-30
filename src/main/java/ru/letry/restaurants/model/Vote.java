package ru.letry.restaurants.model;

import java.time.LocalDateTime;

public class Vote extends AbstractBaseEntity {
    private LocalDateTime dateTime;
    private User user;
    private Restaurant restaurant;
}
