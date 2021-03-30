package ru.letry.restaurants.web.dish;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import ru.letry.restaurants.model.Dish;
import ru.letry.restaurants.service.DishService;
import ru.letry.restaurants.web.SecurityUtil;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.letry.restaurants.util.ValidationUtil.*;

@Controller
public class DishRestController {
    private static final Logger LOG = getLogger(DishRestController.class);

    private final DishService service;

    public DishRestController(DishService service) {
        this.service = service;
    }

    public Dish create(Dish dish, int restaurantId) {
        int userId = SecurityUtil.authUserId();
        checkNew(dish);
        LOG.info("create dish {} by user {}", dish, userId);
        return service.create(dish, restaurantId, userId);
    }

    public Dish get(int id, int restaurantId) {
        int userId = SecurityUtil.authUserId();
        LOG.info("get dish {} by user {}", id, userId);
        return service.get(id, restaurantId);
    }

    public List<Dish> getAll(int restaurantId) {
        int userId = SecurityUtil.authUserId();
        LOG.info("getAll by user {}", userId);
        return service.getAll(restaurantId);
    }

    public void update(Dish dish, int id, int restaurantId) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(dish, id);
        LOG.info("update dish {} by user {}", id, userId);
        service.update(dish, restaurantId, userId);
    }

    public void delete(int id, int restaurantId) {
        int userId = SecurityUtil.authUserId();
        LOG.info("delete dish {} by user {}", id, userId);
        service.delete(id, restaurantId, userId);
    }
}
