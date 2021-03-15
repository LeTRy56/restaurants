package ru.letry.restaurants.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.service.RestaurantService;
import ru.letry.restaurants.web.SecurityUtil;

import java.util.List;

@Controller
public class RestaurantRestController {
    private static final Logger LOG = LoggerFactory.getLogger(RestaurantRestController.class);

    private final RestaurantService service;

    public RestaurantRestController(RestaurantService service) {
        this.service = service;
    }

    public Restaurant create(Restaurant restaurant) {
        int userId = SecurityUtil.authUserId();
        //todo validation util checkNew
        LOG.info("create restaurant {} for user {}", restaurant, userId);
        return service.create(restaurant, userId);
    }

    public Restaurant get(int id) {
        int userId = SecurityUtil.authUserId();
        LOG.info("delete restaurant {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public List<Restaurant> getAll() {
        int userId = SecurityUtil.authUserId();
        LOG.info("getAll for user {}", userId);
        return service.getAll(userId);
    }

    public void update(Restaurant restaurant) {
        int userId = SecurityUtil.authUserId();
        LOG.info("update restaurant {} for user {}", restaurant, userId);
        //todo validation util assureIdConsistent
        service.update(restaurant, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        LOG.info("delete restaurant {} for user {}", id, userId);
        service.delete(id, userId);
    }
}
