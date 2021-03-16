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
        LOG.info("create restaurant {} by user {}", restaurant, userId);
        return service.create(restaurant);
    }

    public Restaurant get(int id) {
        int userId = SecurityUtil.authUserId();
        LOG.info("delete restaurant {} by user {}", id, userId);
        return service.get(id);
    }

    public List<Restaurant> getAll() {
        int userId = SecurityUtil.authUserId();
        LOG.info("getAll by user {}", userId);
        return service.getAll();
    }

    public void update(Restaurant restaurant) {
        int userId = SecurityUtil.authUserId();
        LOG.info("update restaurant {} by user {}", restaurant, userId);
        //todo validation util assureIdConsistent
        service.update(restaurant);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        LOG.info("delete restaurant {} by user {}", id, userId);
        service.delete(id);
    }
}
